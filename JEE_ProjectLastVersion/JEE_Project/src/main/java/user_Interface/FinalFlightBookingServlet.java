package user_Interface;

//AddFlightServlet.java
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

@WebServlet("/FlightFinalBooking")
public class FinalFlightBookingServlet extends HttpServlet {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String fullName = request.getParameter("clientName");
        String emailAddress = request.getParameter("emailAddress");
        String phoneNumber = request.getParameter("phoneNumber");
        String flightDestination = request.getParameter("flightDestination");
        String flightPrice = request.getParameter("flightPrice");
        String carModel = request.getParameter("carModel");
        String hotelName = request.getParameter("hotelName");
        String reservationDate = request.getParameter("reservationDate");
        
     

     // Store the data into the database
        try {
            Connection conn = DbConnection.getConnection();
            
            if (carModel.equalsIgnoreCase("select car model") | hotelName.equalsIgnoreCase("select hotel name")){
                response.sendRedirect("Car_Hotel_option.html?fullName=" + fullName + "&error=3&emailAddress=" + emailAddress + "&phoneNumber=" + phoneNumber + "&flightDestination=" + flightDestination + "&flightPrice=" + flightPrice);

            }else {
            
            
         // Get client ID based on client name
            int clientId = getClientIdByAdress(conn, emailAddress);

            // Get car ID based on car model
            int carId = getCarIdByModel(conn, carModel);
            
         // Get flight ID based on flight Destination
            int flightId = getFlightIdByDestination(conn, flightDestination);
            
         // Get hotel ID based on hotel Name
            int hotelId = getHotelIdByName(conn, hotelName);
            
            
            String sql = "INSERT INTO reservations (client_id,client_name,client_email,"
            		+ "client_phone_number,car_id,car_model,flight_id,"
            		+ "flight_destination,flight_price,hotel_id,hotel_name, reservation_date) "
            		+ "VALUES (?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, clientId);
            pstmt.setString(2, fullName);
            pstmt.setString(3, emailAddress);
            pstmt.setString(4, phoneNumber);
            pstmt.setLong(5, carId);
            pstmt.setString(6, carModel);
            pstmt.setLong(7, flightId);
            pstmt.setString(8, flightDestination);
            pstmt.setString(9, flightPrice);
            pstmt.setLong(10, hotelId);
            pstmt.setString(11, hotelName);
            pstmt.setString(12, reservationDate);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
         // Redirect to a confirmation page
            response.sendRedirect("Confirmation.html?fullName=" + fullName + "&flightDestination=" + flightDestination + "&flightId=" + flightId);
            
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
            response.sendRedirect("error.html"); // Redirect to an error page
            return;
        }

        
    }
	
	private int getClientIdByAdress(Connection conn, String emailAddress) throws SQLException {
        String sql = "SELECT id FROM clients WHERE email = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, emailAddress);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("Client not found");
                }
            }
        }
    }

    private int getCarIdByModel(Connection conn, String carModel) throws SQLException {
        String sql = "SELECT id FROM cars WHERE model = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, carModel);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("Car not found");
                }
            }
        }
    }
    
    private int getFlightIdByDestination(Connection conn, String destination) throws SQLException {
        String sql = "SELECT id FROM flights WHERE destination = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, destination);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("Flight not found");
                }
            }
        }
    }
    
    private int getHotelIdByName(Connection conn, String hotelName) throws SQLException {
        String sql = "SELECT id FROM hotels WHERE name = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, hotelName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("Hotel not found");
                }
            }
        }
    }
    



}
