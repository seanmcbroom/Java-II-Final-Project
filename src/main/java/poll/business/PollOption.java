package poll.business;

import java.io.Serializable;

public class PollOption implements Serializable {
    private int optionId;
    private int pollId;
    private String optionText;
    private int voteCount; // Initialized to zero

    public int getOptionId() {
        return optionId;
    }
    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
    
    public int getPollId() {
        return pollId;
    }
    public void setPollId(int pollId) {
        this.pollId = pollId;
    }
    
    public String getOptionText() {
        return optionText;
    }
    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
    
    public int getVoteCount() {
        return voteCount;
    }
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
