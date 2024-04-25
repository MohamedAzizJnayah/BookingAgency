package user_Interface;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import db_connection.DbConnection;

/**
 * Servlet implementation class HotelBookingServlet
 */
@WebServlet("/hotelBooking")
public class SearchHotelBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchHotelBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
            // Fetch hotle name from the database
            ArrayList<String> hotels = getHotelName();
            
            Gson gson = new Gson();
            String jsonhotels = gson.toJson(hotels);
            
            // Set content type and write JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonhotels);
        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    
    
 // Method to retrieve hotel name from the database
    private ArrayList<String> getHotelName() throws SQLException {
    	 ArrayList<String> hotles = new ArrayList<>();
    	    String sql = "SELECT DISTINCT name FROM hotels";
    	    try (Connection conn = DbConnection.getConnection();
    	         PreparedStatement statement = conn.prepareStatement(sql);
    	         ResultSet resultSet = statement.executeQuery()) {
    	        while (resultSet.next()) {
    	        	hotles.add(resultSet.getString("name"));
    	        }
    	    }
    	    return hotles;
    }

}
