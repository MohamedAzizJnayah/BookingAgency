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
 * Servlet implementation class CarBookingServlet
 */
@WebServlet("/carBooking")
public class SearchCarBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchCarBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	try {
            // Fetch flight destinations from the database
            ArrayList<String> cars = getCarModel();
            
            Gson gson = new Gson();
            String jsoncars = gson.toJson(cars);
            
            // Set content type and write JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsoncars);
        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    	
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */




    
 // Method to retrieve flight destinations from the database
    private ArrayList<String> getCarModel() throws SQLException {
    	 ArrayList<String> models = new ArrayList<>();
    	    String sql = "SELECT DISTINCT model FROM cars";
    	    try (Connection conn = DbConnection.getConnection();
    	         PreparedStatement statement = conn.prepareStatement(sql);
    	         ResultSet resultSet = statement.executeQuery()) {
    	        while (resultSet.next()) {
    	            models.add(resultSet.getString("model"));
    	        }
    	    }
    	    return models;
    }

}
