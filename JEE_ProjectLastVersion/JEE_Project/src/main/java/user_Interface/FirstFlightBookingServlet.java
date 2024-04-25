package user_Interface;

//AddFlightServlet.java
import java.io.IOException;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookingflight")
public class FirstFlightBookingServlet extends HttpServlet {
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String destination = request.getParameter("destination");
        String price = request.getParameter("price");

        // Redirect user to registration.html with booking details as query parameters
        response.sendRedirect("Reservation_Form.html?destination=" + destination + "&price=" + price);
    }
}
