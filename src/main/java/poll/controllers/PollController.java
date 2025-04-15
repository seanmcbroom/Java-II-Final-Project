package poll.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import poll.business.Poll;
import poll.business.PollOption;
import poll.data.PollDAO;

@WebServlet("/submitPoll")
public class PollController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the poll creation page.
        String url = "/create.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String url = "/create.jsp";
        
        // Dispatch based on the request URI.
        if (requestURI.endsWith("/submitPoll")) {
            url = createPoll(request, response);
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    /**
     * Processes the poll creation form submission.
     */
    private String createPoll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String pollTitle = request.getParameter("pollTitle");
        String[] options = request.getParameterValues("options");

        // Validate: require a title and at least two non-empty options.
        if (pollTitle == null || pollTitle.trim().isEmpty() || options == null || options.length < 2) {
            request.setAttribute("errorMessage", "Poll title and at least two options are required.");
            return "/create.jsp";
        }

        // Create the Poll business object.
        Poll poll = new Poll();
        poll.setTitle(pollTitle);

        // Add each non-empty option to the Poll.
        for (String optionText : options) {
            if (optionText != null && !optionText.trim().isEmpty()) {
                PollOption option = new PollOption();
                option.setOptionText(optionText);
                poll.addOption(option);
            }
        }
        
        // Save the poll using the DAO.
        boolean success = PollDAO.insertPoll(poll);
        if (success) {
            // Forward to a list or confirmation page after successful creation.
            return "/view.jsp";
        } else {
            request.setAttribute("errorMessage", "Failed to create poll. Please try again.");
            return "/create.jsp";
        }
    }
}
