package poll.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import poll.business.Poll;
import poll.data.PollDAO;
import java.io.IOException;
import java.util.List;

@WebServlet("/view")
public class PollViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the list of polls from the DAO.
        List<Poll> pollList = PollDAO.selectAllPolls();
        request.setAttribute("pollList", pollList);
        // Forward to the JSP view.
        String url = "/view.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}
