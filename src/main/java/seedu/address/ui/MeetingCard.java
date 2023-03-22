package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.meeting.Meeting;

/**
 * An UI component that displays information of a {@code Meeting}.
 */
public class MeetingCard extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "MeetingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    public final Meeting meeting;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label dateTime;
    @FXML
    private Label attendees;
    @FXML
    private Label meetingLocation;
    @FXML
    private Label description;

    /**
     * Creates a {@code MeetingCard} with the given {@code Meeting} and index to display.
     */
    public MeetingCard(Meeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        id.setText(displayedIndex + ". ");
        title.setText(meeting.getTitle().toString());
        dateTime.setText(meeting.getDateTime().toString());
        attendees.setText(
                "Attendees: "
                        + meeting.getAttendees()
                        .stream()
                        .map(person -> person.getName().toString())
                        .reduce((s, s2) -> s + "," + s2)
                        .get());
        meetingLocation.setText("Location: " + meeting.getLocation().toString());
        description.setText("Description: " + meeting.getDescription().toString());
        formatDateTimePast();
    }

    public void formatDateTimePast() {
        if (meeting.hasPassed()) {
            dateTime.getStyleClass().add(ERROR_STYLE_CLASS);
        } else {
            dateTime.getStyleClass().remove(ERROR_STYLE_CLASS);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetingCard)) {
            return false;
        }

        // state check
        MeetingCard card = (MeetingCard) other;
        return meeting.equals(card.meeting);
    }
}