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

@WebServlet("/deleteReservation")
public class DeleteReservationServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the reservation ID from the request parameters
        String reservationId = request.getParameter("reservationId");

        // Delete the reservation from the database
        deleteReservation(reservationId);

        // Send a success response
        response.getWriter().write("Reservation deleted successfully");
    }

    // Method to delete reservation from the database
    private void deleteReservation(String reservationId) {
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "DELETE FROM reservations WHERE reservation_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reservationId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, such as logging it or throwing a custom exception
        }
    }
}