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

@WebServlet("/addHotel")
public class AddHotelServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get hotel details from the request parameters
        String hotelName = request.getParameter("hotelname");
        String hotelLocation = request.getParameter("hotellocation");
        String hotelPrice = request.getParameter("hotelprice");

        // Add the hotel to the database
        addHotel(hotelName, hotelLocation, hotelPrice);

        response.sendRedirect("AdminPanel/addHotel.html?confirmation=3"); // Redirect back to car booking page with error

    }

    // Method to add hotel to the database
    private void addHotel(String hotelName, String hotelLocation, String hotelPrice) {
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "INSERT INTO hotels (name, location, price) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, hotelName);
            preparedStatement.setString(2, hotelLocation);
            preparedStatement.setString(3, hotelPrice);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, such as logging it or throwing a custom exception
        }
    }
}
