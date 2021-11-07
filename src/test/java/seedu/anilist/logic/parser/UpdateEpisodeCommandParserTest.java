package seedu.anilist.logic.parser;

import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.commons.core.Messages.MESSAGE_OUT_OF_RANGE_INDEX;
import static seedu.anilist.logic.commands.CommandTestUtil.EPISODE_DESC_EPISODE_ONE;
import static seedu.anilist.logic.commands.CommandTestUtil.EPISODE_DESC_EPISODE_TWO;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_EPISODE_DESC_DECIMAL;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_EPISODE_DESC_LARGER_THAN_MAX_INT;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_EPISODE_DESC_NEG;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_TOWATCH;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_EPISODE_TWO_WITH_ZEROS_PADDED;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.index.Index;
import seedu.anilist.logic.commands.UpdateEpisodeCommand;
import seedu.anilist.model.anime.Episode;
import seedu.anilist.testutil.EpisodeDescriptorBuilder;

public class UpdateEpisodeCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateEpisodeCommand.MESSAGE_USAGE);

    private final UpdateEpisodeCommandParser parser = new UpdateEpisodeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, EPISODE_DESC_EPISODE_ONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // not a valid number
        assertParseFailure(parser, "3.141582653" + EPISODE_DESC_EPISODE_ONE,
            MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "3-141582653" + EPISODE_DESC_EPISODE_ONE,
            MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_indexNotInRangeOfPositiveInt_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EPISODE_DESC_EPISODE_ONE,
            MESSAGE_OUT_OF_RANGE_INDEX);

        // zero index
        assertParseFailure(parser, "0" + EPISODE_DESC_EPISODE_ONE, MESSAGE_OUT_OF_RANGE_INDEX);

        // larger than MAX_INT index
        assertParseFailure(parser, ((long) Integer.MAX_VALUE + 1) + EPISODE_DESC_EPISODE_ONE,
            MESSAGE_OUT_OF_RANGE_INDEX);

    }

    @Test
    public void parse_invalidValue_failure() {
        // wrong episode desc
        assertParseFailure(parser, "1" + INVALID_EPISODE_DESC_NEG, Episode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EPISODE_DESC_DECIMAL, Episode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EPISODE_DESC_LARGER_THAN_MAX_INT,
            Episode.MESSAGE_CONSTRAINTS);

        // wrong param specified
        assertParseFailure(parser, "1" + EPISODE_DESC_EPISODE_ONE + STATUS_DESC_TOWATCH,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validEpisodeNumberSpecified_success() {
        Index targetIndex = INDEX_FIRST_ANIME;
        String userInput = targetIndex.getOneBased() + EPISODE_DESC_EPISODE_TWO;

        UpdateEpisodeCommand.EpisodeDescriptor descriptor = new EpisodeDescriptorBuilder()
            .withEpisode(VALID_EPISODE_TWO_WITH_ZEROS_PADDED)
            .build();
        UpdateEpisodeCommand expectedCommand = new UpdateEpisodeCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
