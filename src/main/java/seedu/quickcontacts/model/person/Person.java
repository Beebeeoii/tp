package seedu.quickcontacts.model.person;

import static seedu.quickcontacts.commons.util.CollectionUtil.requireAllNonNull;
import seedu.quickcontacts.logic.parser.EditMeetingParser;
import seedu.quickcontacts.model.Model;

import seedu.quickcontacts.logic.commands.Command;
import seedu.quickcontacts.logic.parser.exceptions.ParseException;
import seedu.quickcontacts.logic.commands.exceptions.CommandException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.quickcontacts.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        if (otherPerson.getPhone() == null) {
            if (getPhone() != null) {
                return false;
            }
        } else {
            if (!otherPerson.getPhone().equals(getPhone())) {
                return false;
            }
        }
        if (otherPerson.getEmail() == null) {
            if (getEmail() != null) {
                return false;
            }
        } else {
            if (!otherPerson.getEmail().equals(getEmail())) {
                return false;
            }
        }
        if (otherPerson.getAddress() == null) {
            if (getAddress() != null) {
                return false;
            }
        } else {
            if (!otherPerson.getAddress().equals(getAddress())) {
                return false;
            }
        }
        return otherPerson.getName().equals(getName())
                && otherPerson.getTags().equals(getTags());
    }

    public void tellMeetingNameChanged(Name newName, Model model) {

        String arguments = "1 m/test";
        try{ 
            Command command = new EditMeetingParser().parse(arguments);
            command.execute(model);
        } catch (ParseException e) {
            System.out.println("nope");
        } catch (CommandException e) {

        }

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
