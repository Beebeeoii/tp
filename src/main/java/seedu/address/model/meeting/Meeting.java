package seedu.address.model.meeting;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;

/**
 * Represents a Meeting in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Meeting {
    // Identity fields
    private final Title title;
    private final DateTime dateTime;
    private final Set<Person> attendees = new HashSet<>();

    // Data fields
    private final Location location;
    private final Description description;

    private boolean hasPassed = false;

    /**
     * Every field must be present and not null.
     */
    public Meeting(Title title, DateTime dateTime, Set<Person> attendees, Location location, Description description) {
        requireAllNonNull(title, dateTime, attendees, location, description);
        this.title = title;
        this.dateTime = dateTime;
        this.attendees.addAll(attendees);
        this.location = location;
        this.description = description;
    }

    public Title getTitle() {
        return title;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns an immutable attendee set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getAttendees() {
        return Collections.unmodifiableSet(attendees);
    }

    public Location getLocation() {
        return location;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns true if both meetings have the same title, dateTime and attendees.
     * This defines a weaker notion of equality between two meetings.
     */
    public boolean isSameMeeting(Meeting otherMeeting) {
        if (otherMeeting == this) {
            return true;
        }

        return otherMeeting != null
                && otherMeeting.getTitle().equals(getTitle())
                && otherMeeting.getDateTime().equals(getDateTime())
                && otherMeeting.getAttendees().equals(getAttendees());
    }

    /**
     * Returns true if {@code Meeting} has passed based on the current DateTime.
     */
    public boolean hasPassed() {
        this.hasPassed = LocalDateTime.now().isAfter(dateTime.get());
        return this.hasPassed;
    }

    /**
     * Returns true if both meetings have the same identity and data fields.
     * This defines a stronger notion of equality between two meetings.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Meeting)) {
            return false;
        }

        Meeting otherMeeting = (Meeting) other;
        return otherMeeting.getTitle().equals(getTitle())
                && otherMeeting.getDateTime().equals(getDateTime())
                && otherMeeting.getAttendees().equals(getAttendees())
                && otherMeeting.getLocation().equals(getLocation())
                && otherMeeting.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, dateTime, attendees, location, description);
    }

    @Override
    public String toString() {
        return getTitle()
                + "; Date/Time: "
                + getDateTime()
                + "; Attendees: "
                + getAttendees()
                + "; Location: "
                + getLocation()
                + "; Description: "
                + getDescription();
    }
    
}
