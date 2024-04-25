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

import db_connection.DbConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Retrieve username and password from the request parameters
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        
	        
	        try {
		        Connection connection = DbConnection.getConnection();
		        String sql = "SELECT * FROM clients WHERE email = ? AND password = ? ";
		        PreparedStatement preparedStatement = connection.prepareStatement(sql);
		        preparedStatement.setString(1, email);
		        preparedStatement.setString(2, password);

		        ResultSet resultSet = preparedStatement.executeQuery();
		        
		        if (resultSet.next()) {
		        	 String role = resultSet.getString("role");
                     if ("admin".equals(role)) {
                         // Redirect to the admin page for admin users
                         response.sendRedirect("/JEE_Project/AdminPanel/dashboard.html");
                     } else {
                         // Redirect to the home page for regular users
                         response.sendRedirect("index.html");
                     }
		        } else {
		            // If credentials don't match, redirect back to the login page with an error message
		            response.sendRedirect("Login_Register.html?error=1");
		        }
		        
		        
		        resultSet.close();
		        preparedStatement.close();
		        connection.close();
		    } catch (Exception e) {
		        // Log the exception
		        e.printStackTrace();
		        // Provide a user-friendly error message
		        response.getWriter().println("An error occurred while login.");
		    }
	        
}
	    
	}


