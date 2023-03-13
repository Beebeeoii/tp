package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Description;
import seedu.address.model.meeting.Location;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.Title;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Meeting}.
 */
public class JsonAdaptedMeeting {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meeting's %s field is missing!";

    private final String title;
    private final String dateTime;
    private final String location;
    private final String description;
    private final List<JsonAdaptedPerson> attendees = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given meeting details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("title") String title, @JsonProperty("dateTime") String dateTime,
                              @JsonProperty("attendees") List<JsonAdaptedPerson> attendees,
                              @JsonProperty("location") String location,
                              @JsonProperty("description") String description) {
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
        if (attendees != null) {
            this.attendees.addAll(attendees);
        }
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        title = source.getTitle().toString();
        dateTime = source.getDateTime().toString();
        location = source.getLocation().toString();
        description = source.getDescription().toString();
        attendees.addAll(source.getAttendees().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted meeting object into the model's {@code Meeting} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting.
     */
    public Meeting toModelType() throws IllegalValueException {
        final List<Person> meetingAttendees = new ArrayList<>();
        for (JsonAdaptedPerson attendee : attendees) {
            meetingAttendees.add(attendee.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }
        if (!DateTime.isValidDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = new DateTime(dateTime);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        final Set<Person> modelAttendees = new HashSet<>(meetingAttendees);
        return new Meeting(modelTitle, modelDateTime, modelAttendees, modelLocation, modelDescription);
    }
}