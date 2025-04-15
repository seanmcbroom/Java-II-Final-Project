package poll.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import poll.data.PollDAO;
import java.io.IOException;

@WebServlet("/votePoll")
public class VoteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pollIdParam = request.getParameter("pollId");
        String optionIdParam = request.getParameter("optionId");

        if (pollIdParam == null || optionIdParam == null) {
            request.setAttribute("errorMessage", "Invalid vote submission.");
            getServletContext().getRequestDispatcher("/listPolls").forward(request, response);
            return;
        }

        int optionId = Integer.parseInt(optionIdParam);
        boolean success = PollDAO.voteOnOption(optionId);
        if(success) {
            // Redirect to poll results page after successful vote.
            response.sendRedirect("results?pollId=" + pollIdParam);
        } else {
            request.setAttribute("errorMessage", "Unable to record vote. Please try again.");
            getServletContext().getRequestDispatcher("/listPolls").forward(request, response);
        }
    }
}
