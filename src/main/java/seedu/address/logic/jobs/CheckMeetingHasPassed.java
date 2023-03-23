package seedu.address.logic.jobs;

import java.util.TimerTask;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;

/**
 * Represents a job that checks whether a meeting has passed based on the current
 * system date/time.
 */
public class CheckMeetingHasPassed extends TimerTask {
    private final Model model;
    private final ObservableList<Meeting> meetings;

    /**
     * Generates the job to be run on CRON.
     * @param model QuickContacts model.
     */
    public CheckMeetingHasPassed(Model model) {
        this.model = model;
        meetings = model.getMeetingsList();
    }

    @Override
    public void run() {
        for (Meeting m : meetings) {
            if (!m.getHasPassed() && m.hasPassed()) {
                // UI update
                Platform.runLater(() -> model.setMeeting(m, m));
            }
        }
    }
}
