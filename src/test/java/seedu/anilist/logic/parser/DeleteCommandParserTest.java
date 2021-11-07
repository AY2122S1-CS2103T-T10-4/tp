package seedu.anilist.logic.parser;

import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.commons.core.Messages.MESSAGE_OUT_OF_RANGE_INDEX;
import static seedu.anilist.logic.commands.CommandTestUtil.EPISODE_DESC_EPISODE_TWO;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_ANIME));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // wrong preamble
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // empty preamble
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // wrong field specified
        assertParseFailure(parser, EPISODE_DESC_EPISODE_TWO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // wrong field specified with correct preamble
        assertParseFailure(parser, "1" + EPISODE_DESC_EPISODE_TWO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexNotInRangeOfPositiveInt_failure() {
        assertParseFailure(parser, "-5", MESSAGE_OUT_OF_RANGE_INDEX);
        assertParseFailure(parser, "0", MESSAGE_OUT_OF_RANGE_INDEX);
        assertParseFailure(parser, Long.toString((long) Integer.MAX_VALUE + 1),
            MESSAGE_OUT_OF_RANGE_INDEX);
    }
}
