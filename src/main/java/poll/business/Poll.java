package poll.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Poll implements Serializable {
    private int pollId;
    private String title;
    private List<PollOption> options;

    public Poll() {
        options = new ArrayList<>();
    }
    
    public int getPollId() {
        return pollId;
    }
    public void setPollId(int pollId) {
        this.pollId = pollId;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<PollOption> getOptions() {
        return options;
    }
    public void setOptions(List<PollOption> options) {
        this.options = options;
    }
    
    public void addOption(PollOption option) {
        this.options.add(option);
    }
}
