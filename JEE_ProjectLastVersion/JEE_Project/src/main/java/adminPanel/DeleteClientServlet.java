package adminPanel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import db_connection.DbConnection;

@WebServlet("/deleteClient")
public class DeleteClientServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the client ID from the request parameters
        String clientId = request.getParameter("clientId");

        // Delete the client from the database
        deleteClient(clientId);

        // Send a success response
        response.getWriter().write("Client deleted successfully");
    }

    // Method to delete client from the database
    private void deleteClient(String clientId) {
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "DELETE FROM clients WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clientId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, such as logging it or throwing a custom exception
        }
    }
}
