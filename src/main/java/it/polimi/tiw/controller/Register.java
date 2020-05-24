package it.polimi.tiw.controller;

import com.google.gson.Gson;
import it.polimi.tiw.beans.User;
import it.polimi.tiw.beans.UserRegisterData;
import it.polimi.tiw.dao.UserDAO;
import it.polimi.tiw.util.ConnectionHandler;
import it.polimi.tiw.util.Hashing;
import it.polimi.tiw.util.Initialization;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

@WebServlet("/Register")
@MultipartConfig
public class Register extends HttpServlet {
    private Connection connection = null;

    @Override
    public void init() throws ServletException {
        connection = Initialization.connectionInit(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRegisterData userRegisterData = new Gson().fromJson(req.getReader().readLine(), UserRegisterData.class);
        System.out.println("email " + userRegisterData.getEmail() + " username " + userRegisterData.getUsername() + " password " + userRegisterData.getPasswordHash() + " passwordConfirm " + userRegisterData.getPasswordRepeatHash());

        if(userRegisterData.getUsername() == null || userRegisterData.getEmail() == null || userRegisterData.getPasswordHash() == null || userRegisterData.getPasswordRepeatHash() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Malformed registration request.");
            return;
        }
        if(userRegisterData.getUsername().isEmpty() || userRegisterData.getEmail().isEmpty() || userRegisterData.getPasswordHash().isEmpty() || userRegisterData.getPasswordRepeatHash().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Credentials must not be empty.");
            return;
        }
        if(!Hashing.isValidMD5(userRegisterData.getPasswordHash()) || !Hashing.isValidMD5(userRegisterData.getPasswordRepeatHash())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Client JavaScript security error.");
            return;
        }
        if(!userRegisterData.getPasswordHash().equals(userRegisterData.getPasswordRepeatHash())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Password and Confirm password do not match.");
            return;
        }
        // RFC822 compliant
        String regex = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
        if(!Pattern.matches(regex, userRegisterData.getEmail())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Invalid email format.");
            return;
        }
        UserDAO userDAO = new UserDAO(connection);
        try {
            if(!userDAO.checkEmail(userRegisterData.getEmail())) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("Email already exists.");
                return;
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Unable to check Email.");
            return;
        }
        try {
            if(!userDAO.checkUsername(userRegisterData.getUsername())) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("Username already exists.");
                return;
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Unable to check Username.");
            return;
        }

        User user;
        try {
            userDAO.registerUser(userRegisterData.getEmail(), userRegisterData.getUsername(), userRegisterData.getPasswordHash());
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Unable to register.");
            return;
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("Successfully registered!");
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
