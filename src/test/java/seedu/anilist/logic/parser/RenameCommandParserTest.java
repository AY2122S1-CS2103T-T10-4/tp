package seedu.anilist.logic.parser;

import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.commons.core.Messages.MESSAGE_OUT_OF_RANGE_INDEX;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_NAME_DESC_BLANK;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_NAME_DESC_NON_ASCII;
import static seedu.anilist.logic.commands.CommandTestUtil.NAME_DESC_AKIRA;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_TOWATCH;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_AKIRA;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_SECOND_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.index.Index;
import seedu.anilist.logic.commands.RenameCommand;
import seedu.anilist.model.anime.Name;
import seedu.anilist.testutil.NameDescriptorBuilder;

public class RenameCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameCommand.MESSAGE_USAGE);

    private final RenameCommandParser parser = new RenameCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AKIRA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // not a valid number
        assertParseFailure(parser, "3.141592654" + NAME_DESC_AKIRA, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "3-141582654" + NAME_DESC_AKIRA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_indexNotInRangeOfPositiveInt_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AKIRA, MESSAGE_OUT_OF_RANGE_INDEX);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AKIRA, MESSAGE_OUT_OF_RANGE_INDEX);

        // larger than MAX_INT index
        assertParseFailure(parser, ((long) Integer.MAX_VALUE + 1) + NAME_DESC_AKIRA,
            MESSAGE_OUT_OF_RANGE_INDEX);

    }

    @Test
    public void parse_invalidValue_failure() {
        // wrong name parameter
        assertParseFailure(parser, "1" + INVALID_NAME_DESC_BLANK, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_NAME_DESC_NON_ASCII, Name.MESSAGE_CONSTRAINTS);

        // extra wrong parameter specified
        assertParseFailure(parser, "1" + NAME_DESC_AKIRA + STATUS_DESC_TOWATCH, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ANIME;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AKIRA;

        RenameCommand.NameDescriptor descriptor = new NameDescriptorBuilder()
            .withName(VALID_NAME_AKIRA).build();
        RenameCommand expectedCommand = new RenameCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
