package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindMeetingCommand;
import seedu.address.model.meeting.MeetingContainsNamesPredicate;

public class FindMeetingCommandParserTest {

    private final FindMeetingCommandParser parser = new FindMeetingCommandParser();

    @Test
    public void parse_noArg_returnsFindMeetingCommand() {
        FindMeetingCommand expectedFindMeetingCommand = new FindMeetingCommand(
            new MeetingContainsNamesPredicate());
        assertParseSuccess(parser, "", expectedFindMeetingCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMeetingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindMeetingCommand() {
        // no leading and trailing whitespaces
        FindMeetingCommand expectedFindMeetingCommand = new FindMeetingCommand(
                new MeetingContainsNamesPredicate(List.of("Alice", "Bob")));

        assertParseSuccess(parser, "Alice Bob", expectedFindMeetingCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindMeetingCommand);
    }

}