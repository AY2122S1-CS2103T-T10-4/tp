package seedu.anilist.logic.parser;

import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.commons.core.Messages.MESSAGE_OUT_OF_RANGE_INDEX;
import static seedu.anilist.logic.commands.CommandTestUtil.ACTION_DESC_ADD;
import static seedu.anilist.logic.commands.CommandTestUtil.ACTION_DESC_DELETE_SHORT_FORM;
import static seedu.anilist.logic.commands.CommandTestUtil.GENRE_DESC_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.GENRE_DESC_SCIENCE_FICTION;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_ACTION_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_ACTION_DESC_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_DESC_NON_ALPHANUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_TOWATCH;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.anilist.testutil.AnimeUtil.getGenreDescriptorDetails;
import static seedu.anilist.testutil.TypicalAnimes.BNHA;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.index.Index;
import seedu.anilist.logic.commands.Action;
import seedu.anilist.logic.commands.GenreAddCommand;
import seedu.anilist.logic.commands.GenreCommand;
import seedu.anilist.model.genre.Genre;
import seedu.anilist.testutil.GenresDescriptorBuilder;

public class GenreCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenreCommand.MESSAGE_USAGE);

    private final GenreCommandParser parser = new GenreCommandParser();
    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ACTION_DESC_ADD, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no action specified
        assertParseFailure(parser, "1" + GENRE_DESC_SCIENCE_FICTION, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no genre specified
        assertParseFailure(parser, "1" + ACTION_DESC_ADD, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser,
                "1 some random string" + ACTION_DESC_ADD + GENRE_DESC_SCIENCE_FICTION,
                MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser,
                "1 i/ string" + ACTION_DESC_ADD + GENRE_DESC_SCIENCE_FICTION,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_indexNotInRangeOfPositiveInt_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ACTION_DESC_ADD + GENRE_DESC_SCIENCE_FICTION,
            MESSAGE_OUT_OF_RANGE_INDEX);

        // zero index
        assertParseFailure(parser, "0" + ACTION_DESC_ADD + GENRE_DESC_SCIENCE_FICTION,
            MESSAGE_OUT_OF_RANGE_INDEX);

        // larger than MAX_INT index
        assertParseFailure(parser, ((long) Integer.MAX_VALUE + 1) + ACTION_DESC_ADD
                + GENRE_DESC_SCIENCE_FICTION, MESSAGE_OUT_OF_RANGE_INDEX);

    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid action
        assertParseFailure(parser,
                "1" + INVALID_ACTION_DESC_ALPHA + GENRE_DESC_SCIENCE_FICTION,
                String.format(Action.MESSAGE_INVALID_ACTION_FORMAT, INVALID_ACTION_ALPHA));

        //invalid genre
        assertParseFailure(parser, "1" + ACTION_DESC_DELETE_SHORT_FORM + INVALID_GENRE_DESC_NON_ALPHANUMERIC,
                Genre.MESSAGE_CONSTRAINTS);

        // wrong params specified
        assertParseFailure(parser, "1" + ACTION_DESC_DELETE_SHORT_FORM + GENRE_DESC_ACTION
                + STATUS_DESC_TOWATCH, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validAllFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ANIME;

        GenreCommand.GenresDescriptor descriptor = new GenresDescriptorBuilder()
                .withGenre(BNHA.getGenres())
                .build();

        String userInputAdd = targetIndex.getOneBased() + ACTION_DESC_ADD
                + " " + getGenreDescriptorDetails(descriptor);
        GenreCommand expectedAddCommand = new GenreAddCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInputAdd, expectedAddCommand);
    }
}
