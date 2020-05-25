package it.polimi.tiw.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.tiw.beans.DeleteDataBean;
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

@WebServlet("/deleteEntity")
public class DeleteEntity extends HttpServlet {
    private static Connection connection = null;

    @Override
    public void init() throws ServletException {
        connection = Initialization.connectionInit(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DeleteDataBean bean = null;
        try{
            bean = new Gson().fromJson(req.getReader().readLine(), DeleteDataBean.class);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Malformed request.");
            return;
        }
        System.out.println(bean.getEntity_id() + "::" + bean.getEntity_type());
        // is the object not null and a number?
        if(bean.getEntity_id() > 0 && (bean.getEntity_type().equals("subfolder") || bean.getEntity_type().equals("document"))) {
            if(bean.getEntity_type().equals("subfolder")) {
                SubfolderDAO subfolderDAO = new SubfolderDAO(connection, bean.getEntity_id());
                try {
                    if(subfolderDAO.findSubfolder() != null) {
                        try{
                            subfolderDAO.deleteSubfolder();
                        } catch (SQLException e) {
                            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            resp.getWriter().println("Unable to delete subfolder");
                            return;
                        }
                    } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().println("Invalid entity id.");
                        return;
                    }
                } catch (SQLException e) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().println("Unable to find subfolder.");
                    return;
                }
            } else if(bean.getEntity_type().equals("document")) {
                DocumentDAO documentDAO = new DocumentDAO(connection, bean.getEntity_id());
                try {
                    if(documentDAO.findDocument() != null) {
                        try{
                            documentDAO.deleteDocument();
                        } catch (SQLException e) {
                            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            resp.getWriter().println("Unable to delete document.");
                            return;
                        }
                    } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().println("Invalid entity id.");
                        return;
                    }
                } catch (SQLException e) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().println("Unable to find document.");
                    return;
                }
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Entity type is invalid.");
            return;
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("Delete successful");
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
