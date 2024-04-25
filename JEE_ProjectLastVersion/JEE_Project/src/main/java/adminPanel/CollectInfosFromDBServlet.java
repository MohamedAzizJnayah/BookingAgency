package adminPanel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import db_connection.DbConnection;

@WebServlet("/collectInfosFromDB")
public class CollectInfosFromDBServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch data from the database
        List<String[]> reservations = fetchReservations();
        List<String[]> clients = fetchUserClients();
        List<String[]> hotels = fetchHotels();
        List<String[]> cars = fetchCars();

        // Create a data object to hold all the fetched data
        DashboardData dashboardData = new DashboardData();
        dashboardData.setReservations(reservations);
        dashboardData.setClients(clients);
        dashboardData.setHotels(hotels);
        dashboardData.setCars(cars);

        // Convert the data object to JSON
        Gson gson = new Gson();
        String jsonData = gson.toJson(dashboardData);

        // Set the response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write the JSON data to the response
        response.getWriter().write(jsonData);
    }

    // Method to fetch reservation data from the database
    private List<String[]> fetchReservations() {
        List<String[]> reservations = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            String sql = "SELECT * FROM reservations";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String[] reservation = new String[6];
                reservation[0] = resultSet.getString("reservation_id");
                reservation[1] = resultSet.getString("client_name");
                reservation[2] = resultSet.getString("client_phone_number");
                reservation[3] = resultSet.getString("flight_destination");
                reservation[4] = resultSet.getString("flight_price");
                reservation[5] = resultSet.getString("reservation_date");
                reservations.add(reservation);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reservations;
    }
    
		 // Method to fetch client data with user role from the database
		    private List<String[]> fetchUserClients() {
		        List<String[]> clients = new ArrayList<>();
		        try {
		            Connection connection = DbConnection.getConnection();
		            String sql = "SELECT * FROM clients WHERE role = ?";
		            PreparedStatement preparedStatement = connection.prepareStatement(sql);
		            preparedStatement.setString(1, "user"); // Filter clients with the role "user"
		            ResultSet resultSet = preparedStatement.executeQuery();
		            while (resultSet.next()) {
		                String[] client = new String[3];
		                client[0] = resultSet.getString("id");
		                client[1] = resultSet.getString("username");
		                client[2] = resultSet.getString("email");
		                clients.add(client);
		            }
		            resultSet.close();
		            preparedStatement.close();
		            connection.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return clients;
		    }

		    
		 // Method to fetch hotel data from the database
		    private List<String[]> fetchHotels() {
		        List<String[]> hotels = new ArrayList<>();
		        try {
		            Connection connection = DbConnection.getConnection();
		            String sql = "SELECT * FROM hotels";
		            PreparedStatement preparedStatement = connection.prepareStatement(sql);
		            ResultSet resultSet = preparedStatement.executeQuery();
		            while (resultSet.next()) {
		                String[] hotel = new String[4];
		                hotel[0] = resultSet.getString("id");
		                hotel[1] = resultSet.getString("name");
		                hotel[2] = resultSet.getString("location");
		                hotel[3] = resultSet.getString("price");
		                hotels.add(hotel);
		            }
		            resultSet.close();
		            preparedStatement.close();
		            connection.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return hotels;
		    }
		    
		    
		 // Method to fetch car data from the database
		    private List<String[]> fetchCars() {
		        List<String[]> cars = new ArrayList<>();
		        try {
		            Connection connection = DbConnection.getConnection();
		            String sql = "SELECT * FROM cars";
		            PreparedStatement preparedStatement = connection.prepareStatement(sql);
		            ResultSet resultSet = preparedStatement.executeQuery();
		            while (resultSet.next()) {
		                String[] car = new String[3];
		                car[0] = resultSet.getString("id");
		                car[1] = resultSet.getString("model");
		                car[2] = resultSet.getString("price");
		                cars.add(car);
		            }
		            resultSet.close();
		            preparedStatement.close();
		            connection.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return cars;
		    }


    // Class to hold dashboard data
    class DashboardData {
        private List<String[]> reservations;
        private List<String[]> clients;
        private List<String[]> hotels;
        private List<String[]> cars;

        // Getters and setters
        public List<String[]> getReservations() {
            return reservations;
        }

        public void setReservations(List<String[]> reservations) {
            this.reservations = reservations;
        }

        public List<String[]> getClients() {
            return clients;
        }

        public void setClients(List<String[]> clients) {
            this.clients = clients;
        }

        public List<String[]> getHotels() {
            return hotels;
        }

        public void setHotels(List<String[]> hotels) {
            this.hotels = hotels;
        }

        public List<String[]> getCars() {
            return cars;
        }

        public void setCars(List<String[]> cars) {
            this.cars = cars;
        }
    }
}