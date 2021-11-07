package seedu.anilist.logic.parser;

import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.commons.core.Messages.MESSAGE_OUT_OF_RANGE_INDEX;
import static seedu.anilist.logic.commands.CommandTestUtil.EPISODE_DESC_EPISODE_ONE;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STATUS_DESC_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STATUS_DESC_NUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_TOWATCH;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_WATCHING;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_TOWATCH;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.index.Index;
import seedu.anilist.logic.commands.UpdateStatusCommand;
import seedu.anilist.model.anime.Status;
import seedu.anilist.testutil.StatusDescriptorBuilder;

public class UpdateStatusCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateStatusCommand.MESSAGE_USAGE);

    private final UpdateStatusCommandParser parser = new UpdateStatusCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, STATUS_DESC_WATCHING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // not a valid number
        assertParseFailure(parser, "3.141582653" + STATUS_DESC_WATCHING,
            MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "3-141582653" + STATUS_DESC_WATCHING,
            MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_indexNotInRangeOfPositiveInt_failure() {
        // negative index
        assertParseFailure(parser, "-5" + STATUS_DESC_WATCHING, MESSAGE_OUT_OF_RANGE_INDEX);

        // zero index
        assertParseFailure(parser, "0" + STATUS_DESC_WATCHING, MESSAGE_OUT_OF_RANGE_INDEX);

        // larger than MAX_INT index
        assertParseFailure(parser, ((long) Integer.MAX_VALUE + 1) + STATUS_DESC_WATCHING,
            MESSAGE_OUT_OF_RANGE_INDEX);

    }

    @Test
    public void parse_invalidValue_failure() {
        // wrong status desc
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC_ALPHA, Status.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC_NUMERIC, Status.MESSAGE_CONSTRAINTS);

        // wrong param specified
        assertParseFailure(parser, "1" + STATUS_DESC_WATCHING + EPISODE_DESC_EPISODE_ONE,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validStatusSpecified_success() {
        Index targetIndex = INDEX_FIRST_ANIME;
        String userInput = targetIndex.getOneBased() + STATUS_DESC_TOWATCH;

        UpdateStatusCommand.StatusDescriptor descriptor = new StatusDescriptorBuilder()
                .withStatus(VALID_STATUS_TOWATCH)
                .build();
        UpdateStatusCommand expectedCommand = new UpdateStatusCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
