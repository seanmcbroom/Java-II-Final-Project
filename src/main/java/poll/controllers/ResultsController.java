package poll.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import poll.business.Poll;
import poll.data.PollDAO;
import java.io.IOException;

@WebServlet("/results")
public class ResultsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pollIdParam = request.getParameter("pollId");
        
        if (pollIdParam == null) {
            request.setAttribute("errorMessage", "Poll ID not provided.");
            response.sendRedirect("listPolls");
            return;
        }
        
        int pollId = Integer.parseInt(pollIdParam);
        Poll poll = PollDAO.selectPoll(pollId);
        
        if(poll == null) {
            request.setAttribute("errorMessage", "Poll not found.");
            response.sendRedirect("listPolls");
            return;
        }
        
        request.setAttribute("poll", poll);
        String url = "/results.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}
