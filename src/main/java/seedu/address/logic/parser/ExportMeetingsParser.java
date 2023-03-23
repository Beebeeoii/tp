package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AutocompleteResult;
import seedu.address.logic.commands.ExportMeetingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.DateTime;

/**
 * Parser for parsing export meeting commands
 */
public class ExportMeetingsParser implements Parser<ExportMeetingsCommand> {
    public static final Prefix[] PREFIXES = {PREFIX_MEETING_TITLE, PREFIX_START, PREFIX_END};

    @Override
    public ExportMeetingsCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIXES);
        List<String> indexStrings = argMultimap.getAllValues(PREFIX_MEETING_TITLE);
        List<Index> indexes = new ArrayList<>();
        for (String s : indexStrings) {
            try {
                indexes.add(ParserUtil.parseIndex(s));
            } catch (ParseException parseException) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ExportMeetingsCommand.MESSAGE_USAGE),
                        parseException);
            }
        }
        DateTime start = null;
        DateTime end = null;
        try {
            if (argMultimap.getValue(PREFIX_START).isPresent()) {
                start = new DateTime(argMultimap.getValue(PREFIX_START).get());
            }
            if (argMultimap.getValue(PREFIX_END).isPresent()) {
                end = new DateTime(argMultimap.getValue(PREFIX_END).get());
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExportMeetingsCommand.MESSAGE_USAGE));
        }
        return new ExportMeetingsCommand(indexes, start, end);
    }

    @Override
    public AutocompleteResult getAutocompleteSuggestion(String input) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input, PREFIXES);

        for (Prefix p : PREFIXES) {
            if (argMultimap.getValue(p).isEmpty()) {
                return new AutocompleteResult(p, false);
            }
        }

        return new AutocompleteResult(null, false);

    }
}
