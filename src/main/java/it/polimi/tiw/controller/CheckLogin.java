package it.polimi.tiw.controller;

import it.polimi.tiw.beans.User;
import it.polimi.tiw.dao.UserDAO;
import it.polimi.tiw.util.ConnectionHandler;
import it.polimi.tiw.util.Initialization;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/CheckLogin")
@MultipartConfig
public class CheckLogin extends HttpServlet {
    private static Connection connection = null;

    @Override
    public void init() {
        connection = Initialization.connectionInit(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username;
        String password;
        username = req.getParameter("username");
        password = req.getParameter("password");
        if(username == null || password == null || username.isEmpty() || password.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Credentials must not be null");
            return;
        }
        UserDAO userDAO = new UserDAO(connection);
        User user;
        try {
            user = userDAO.checkCredentials(username, password);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Unable to retrieve user data.");
            return;
        }

        if(user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println("username/password incorrect");
        } else {
            req.getSession().setAttribute("user", user);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(username);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionHandler.closeConnection(connection);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
