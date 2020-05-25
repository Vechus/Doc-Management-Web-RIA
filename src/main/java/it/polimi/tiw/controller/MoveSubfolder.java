package it.polimi.tiw.controller;

import com.google.gson.Gson;
import it.polimi.tiw.beans.MoveDataBean;
import it.polimi.tiw.dao.DocumentDAO;
import it.polimi.tiw.dao.SubfolderDAO;
import it.polimi.tiw.util.ConnectionHandler;
import it.polimi.tiw.util.Initialization;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/MoveSubfolder")
public class MoveSubfolder extends HttpServlet {
    private static Connection connection = null;

    @Override
    public void init() throws ServletException {
        connection = Initialization.connectionInit(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MoveDataBean moveDataBean = null;

        try {
            moveDataBean = new Gson().fromJson(req.getReader().readLine(), MoveDataBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Malformed request.");
            return;
        }

        if(moveDataBean == null || moveDataBean.getEntity_id() <= 0 || moveDataBean.getTo() <= 0) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Parameters missing or bad values.");
            return;
        }
        SubfolderDAO subfolderDAO = new SubfolderDAO(connection, moveDataBean.getEntity_id());
        try {
            if(subfolderDAO.findSubfolder() != null) {
                try {
                    subfolderDAO.moveSubfolder(moveDataBean.getTo());
                } catch (SQLException e) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().println("Unable to move subfolder.");
                    return;
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Subfolder does not exist.");
                return;
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Unable to get subfolder.");
            return;
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("Subfolder move successful.");
    }

    @Override
    public void destroy() {
        try {
            ConnectionHandler.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
