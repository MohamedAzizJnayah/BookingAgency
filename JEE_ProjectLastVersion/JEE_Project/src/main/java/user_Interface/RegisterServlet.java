package user_Interface;

import jakarta.servlet.ServletException;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.util.UUID;

import db_connection.DbConnection;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve registration data from the request parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        // Add more fields as necessary for your registration form

        // Generate a random ID using UUID
        //String userId = UUID.randomUUID().toString();
        
        try {
	        Connection connection = DbConnection.getConnection();
            String sql = "INSERT INTO clients (username, email, password) VALUES ( ?, ?, ?)";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        //preparedStatement.setString(1, userId);
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, email );
	        preparedStatement.setString(3, password);
	        
	     // Execute the INSERT query
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                // Registration successful
                response.sendRedirect("index.html?msg=4"); // Redirect to login page
            } else {
                // Registration failed
                response.sendRedirect("Login_Register.html?error=1"); // Redirect back to register page with error
            }
	        
	       
	    } catch (Exception e) {
	        // Log the exception
	        e.printStackTrace();
	        // Provide a user-friendly error message
	        response.getWriter().println("An error occurred while register.");
	        
	    }
	    
        
        
	}

	}
