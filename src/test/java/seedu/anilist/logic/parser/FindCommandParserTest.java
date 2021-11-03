package seedu.anilist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.logic.commands.CommandTestUtil.GENRE_DESC_SCIENCE_FICTION;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_WATCHING;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.commands.FindCommand;
import seedu.anilist.logic.parser.exceptions.ParseException;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    private static void assertIsFindCommand(Object o) {
        assertNotNull(o);
        assertTrue(o instanceof FindCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        // no params specified
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // empty param
        assertParseFailure(parser, "n/",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, "asdf" + GENRE_DESC_SCIENCE_FICTION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // wrong params
        assertParseFailure(parser, STATUS_DESC_WATCHING + GENRE_DESC_SCIENCE_FICTION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {
        // no leading and trailing whitespaces
        FindCommand resultingCommand = parser.parse(" n/Attack n/Black");
        assertIsFindCommand(resultingCommand);

        resultingCommand = parser.parse(" g/supernatural g/horror");
        assertIsFindCommand(resultingCommand);

        resultingCommand = parser.parse(" n/A n/B g/sci fi g/fantasy");
        assertIsFindCommand(resultingCommand);
    }

}
