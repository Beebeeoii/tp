package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;

/**
 * Creates an SortMeetingCommand to get {@code ModelManage} class to sort with a specified attributes {@code Title},
 * {@code DateTime}, {@code Location}, {@code Description}
 */
public class SortMeetingCommand extends Command {

    public static final String COMMAND_WORD = "sortm";
    public static final String MESSAGE_USAGE = "Sort meetings by their attributes: "
            + "title (m/), dateTime (dt/), location (l/), description (des/). \n"
            + "Example: `sortm m/`.i \n"
            + "For reverse sorting, append an `r` after the prefix.\n"
            + "Example: `sortm m/r`";
    private static Prefix sortByPrefix;
    private static String prefix;
    private static boolean isReverse;
    private static final Comparator<Meeting> titleComparator = Comparator.comparing(m -> m.getTitle().toString());
    private static final Comparator<Meeting> descriptorComparator = Comparator.comparing(m -> {
        if (m.getDescription() == null) {
            return "";
        }
        return m.getDescription().toString();
    });
    private static final Comparator<Meeting> locationComparator = Comparator.comparing(m -> {
        if (m.getLocation() == null) {
            return "";
        }
        return m.getLocation().toString();
    });
    private static final Comparator<Meeting> dateTimeComparator = Comparator.comparing((Meeting m) -> m.getDateTime()
                                                                .get())
                                                                .reversed();
    private static final String MESSAGE_SUCCESS = "Sorted by %1$s";
    /**
     * Creates an SortMeetingCommand to get {@code ModelManage} class to sort with a specified attributes {@code Title},
     * {@code DateTime}, {@code Location}, {@code Description}
     */
    public SortMeetingCommand(Prefix sortByPrefix, boolean isReverse) {
        SortMeetingCommand.sortByPrefix = sortByPrefix;
        SortMeetingCommand.isReverse = isReverse;
    }
    /**
     * executes the sort command, by the prefix given pass the correct comparator
     * {@code DateTime}, {@code Location}, {@code Description}, {@code Title}
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        prefix = sortByPrefix.toString();
        String prefixName;
        switch (prefix) {
        case "m/":
            reverseSort(model, titleComparator, isReverse);
            prefixName = "Meeting Title";
            break;
        case "des/":
            reverseSort(model, descriptorComparator, isReverse);
            prefixName = "Meeting Description";
            break;
        case "l/":
            reverseSort(model, locationComparator, isReverse);
            prefixName = "Meeting Location";
            break;
        case "dt/":
            reverseSort(model, dateTimeComparator, isReverse);
            prefixName = "Meeting DateTime";
            break;
        default:
            throw new CommandException("Something went wrong");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, prefixName));
    }
    private void reverseSort(Model model, Comparator<Meeting> comparator, boolean isReverse) {
        if (isReverse) {
            model.sortFilteredMeetingList(comparator.reversed());
        } else {
            model.sortFilteredMeetingList(comparator);
        }
    }
}
