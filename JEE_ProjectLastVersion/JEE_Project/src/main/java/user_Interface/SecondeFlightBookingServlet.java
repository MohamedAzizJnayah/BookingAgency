package user_Interface;

import java.io.IOException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import db_connection.DbConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FlightBookingServlet
 */
@WebServlet("/Flight_to_car_option")
public class SecondeFlightBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SecondeFlightBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String fullName = request.getParameter("fullName");
        String emailAddress = request.getParameter("emailAddress");
        String phoneNumber = request.getParameter("phoneNumber");
        String flightDestination = request.getParameter("flightDestination");
        String flightPrice = request.getParameter("flightPrice");

        // Process the data as needed
        // For example, you can store it in a database or perform other operations
        
     // Database connection and SQL query
        try (Connection conn = DbConnection.getConnection()) {

            // Check if the client name exists
            if (isClientNameExists(conn, emailAddress)) {

        
     response.sendRedirect("Car_Hotel_option.html?fullName=" + fullName + "&emailAddress=" + emailAddress + "&phoneNumber=" + phoneNumber + "&flightDestination=" + flightDestination + "&flightPrice=" + flightPrice);

        
        }else {
            // Client name not found
            response.sendRedirect("Reservation_Form.html?error=3"); // Redirect back to car booking page with error
        } 

        // Redirect to a confirmation page
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}
    
	private boolean isClientNameExists(Connection conn, String emailAddress) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM clients WHERE email = ? and role = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, emailAddress);
            statement.setString(2, "user");

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0;
                }
            }
        }
        return false;
    }

}


