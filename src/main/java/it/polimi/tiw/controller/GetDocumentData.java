package it.polimi.tiw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.tiw.beans.DocumentData;
import it.polimi.tiw.dao.DocumentDAO;
import it.polimi.tiw.util.ConnectionHandler;
import it.polimi.tiw.util.Initialization;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/GetDocumentData")
public class GetDocumentData extends HttpServlet {
    public static Connection connection = null;

    @Override
    public void init() throws ServletException {
        connection = Initialization.connectionInit(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = -1;

        if(req.getParameter("documentid") != null)
            id = Integer.parseInt(req.getParameter("documentid"));
        else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Invalid document id or not present.");
            return;
        }

        DocumentDAO documentDAO = new DocumentDAO(connection, id);
        DocumentData documentData = null;
        try {
            if(documentDAO.findDocument() != null) {
                try {
                    documentData = documentDAO.getData();
                } catch (SQLException exception) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().println("Unable to retrieve document data.");
                    return;
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("Invalid document id.");
                return;
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Unable to retrieve document.");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        Gson gson = new GsonBuilder().create();
        String jsonOut = gson.toJson(documentData);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonOut);
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
