package it.polimi.tiw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.tiw.beans.Folder;
import it.polimi.tiw.beans.User;
import it.polimi.tiw.dao.HomeDAO;
import it.polimi.tiw.util.ConnectionHandler;
import it.polimi.tiw.util.Initialization;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GetFolders")
public class GetFolders extends HttpServlet {
    private static Connection connection = null;

    @Override
    public void init() throws ServletException {
        connection = Initialization.connectionInit(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        HomeDAO homeDAO = new HomeDAO(connection);
        List<Folder> folderList = new ArrayList<Folder>();

        try {
            folderList = homeDAO.findFolders();
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Unable to recover folders.");
            return;
        }

        Gson gson = new GsonBuilder().setDateFormat("dd MMM yyyy").create();
        String jsonOut = gson.toJson(folderList);
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
