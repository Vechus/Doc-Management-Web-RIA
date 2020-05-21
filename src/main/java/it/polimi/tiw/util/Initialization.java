package it.polimi.tiw.util;

import javax.servlet.ServletContext;
import java.sql.Connection;

public class Initialization {

    public static Connection connectionInit(ServletContext context) {
        Connection connection = null;
        try {
            connection = ConnectionHandler.getConnection(context);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }
}
