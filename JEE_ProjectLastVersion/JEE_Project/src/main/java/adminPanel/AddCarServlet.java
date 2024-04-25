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

@WebServlet("/addCar")
public class AddCarServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the car model and price from the request parameters
        String carModel = request.getParameter("carmodel");
        String carPrice = request.getParameter("carprice");

        // Add the car to the database
        addCar(carModel, carPrice);

        // Redirect to the addCar.html page with a confirmation parameter
        response.sendRedirect("AdminPanel/addCar.html?confirmation=3");
    }

    // Method to add a car to the database
    private void addCar(String carModel, String carPrice) {
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "INSERT INTO cars (model, price) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, carModel);
            preparedStatement.setString(2, carPrice);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, such as logging it or throwing a custom exception
        }
    }
}
