package poll.data;

import poll.business.Poll;
import poll.business.PollOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PollDAO {

    // Helper method to safely close a ResultSet
    private static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Error closing ResultSet: " + e);
            }
        }
    }
    
    // Helper method to safely close a PreparedStatement
    private static void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                System.out.println("Error closing PreparedStatement: " + e);
            }
        }
    }
    
    /**
     * Inserts a poll and its options into the database.
     * Uses transactions to ensure both poll and options are saved together.
     */
    public static boolean insertPoll(Poll poll) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement pollStmt = null;
        PreparedStatement optionStmt = null;
        ResultSet rs = null;
        
        try {
            connection.setAutoCommit(false);
            
            // Insert poll title into the Polls table.
            String pollQuery = "INSERT INTO Polls (Title) VALUES (?)";
            pollStmt = connection.prepareStatement(pollQuery, Statement.RETURN_GENERATED_KEYS);
            pollStmt.setString(1, poll.getTitle());
            int pollInserted = pollStmt.executeUpdate();
            if (pollInserted == 0) {
                return false;
            }
            
            // Retrieve the generated poll ID.
            rs = pollStmt.getGeneratedKeys();
            int pollId = 0;
            if (rs.next()) {
                pollId = rs.getInt(1);
                poll.setPollId(pollId);
            } else {
                return false;
            }
            
            // Insert each poll option into the PollOptions table.
            String optionQuery = "INSERT INTO PollOptions (PollID, OptionText) VALUES (?, ?)";
            optionStmt = connection.prepareStatement(optionQuery);
            for (PollOption option : poll.getOptions()) {
                optionStmt.setInt(1, pollId);
                optionStmt.setString(2, option.getOptionText());
                optionStmt.addBatch();
            }
            int[] optionResults = optionStmt.executeBatch();
            // Verify that every insertion was successful.
            for (int result : optionResults) {
                if (result == Statement.EXECUTE_FAILED) {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error in insertPoll: " + e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback failed: " + ex);
            }
            return false;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pollStmt);
            closePreparedStatement(optionStmt);
            pool.freeConnection(connection);
        }
    }
    
    /**
     * Retrieves a list of all polls including their options.
     */
    public static List<Poll> selectAllPolls() {
        List<Poll> pollList = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT PollID, Title FROM Polls";
        try {
            stmt = connection.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Poll poll = new Poll();
                poll.setPollId(rs.getInt("PollID"));
                poll.setTitle(rs.getString("Title"));
                // Retrieve options for each poll.
                poll.setOptions(selectPollOptions(poll.getPollId()));
                pollList.add(poll);
            }
        } catch (SQLException e) {
            System.err.println("Error in selectAllPolls: " + e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(stmt);
            pool.freeConnection(connection);
        }
        return pollList;
    }
    
    /**
     * Retrieves the list of options for a given poll.
     */
    public static List<PollOption> selectPollOptions(int pollId) {
        List<PollOption> options = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT OptionID, OptionText, VoteCount FROM PollOptions WHERE PollID = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pollId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PollOption option = new PollOption();
                option.setOptionId(rs.getInt("OptionID"));
                option.setOptionText(rs.getString("OptionText"));
                option.setVoteCount(rs.getInt("VoteCount"));
                options.add(option);
            }
        } catch (SQLException e) {
            System.err.println("Error in selectPollOptions: " + e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(stmt);
            pool.freeConnection(connection);
        }
        return options;
    }
    
    /**
     * Retrieves a single poll by its ID, including its options.
     */
    public static Poll selectPoll(int pollId) {
        Poll poll = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT PollID, Title FROM Polls WHERE PollID = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, pollId);
            rs = stmt.executeQuery();
            if (rs.next()){
                poll = new Poll();
                poll.setPollId(rs.getInt("PollID"));
                poll.setTitle(rs.getString("Title"));
                poll.setOptions(selectPollOptions(pollId));
            }
        } catch (SQLException e) {
            System.err.println("Error in selectPoll: " + e);
        } finally {
            closeResultSet(rs);
            closePreparedStatement(stmt);
            pool.freeConnection(connection);
        }
        return poll;
    }
    
    /**
     * Increments the vote count for a specific poll option.
     */
    public static boolean voteOnOption(int optionId) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement stmt = null;
        String sql = "UPDATE PollOptions SET VoteCount = VoteCount + 1 WHERE OptionID = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, optionId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in voteOnOption: " + e);
            return false;
        } finally {
            closePreparedStatement(stmt);
            pool.freeConnection(connection);
        }
    }
}
