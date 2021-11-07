package seedu.anilist.logic.parser;

import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.logic.commands.CommandTestUtil.ACTION_DESC_ADD;
import static seedu.anilist.logic.commands.CommandTestUtil.EPISODE_DESC_EPISODE_TWO;
import static seedu.anilist.logic.commands.CommandTestUtil.GENRE_DESC_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.GENRE_DESC_SCIENCE_FICTION;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_EPISODE_DESC_DECIMAL;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_EPISODE_DESC_LARGER_THAN_MAX_INT;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_DESC_NON_ALPHANUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_NAME_DESC_BLANK;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STATUS_DESC_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.NAME_DESC_AKIRA;
import static seedu.anilist.logic.commands.CommandTestUtil.NAME_DESC_BNHA;
import static seedu.anilist.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.anilist.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_TOWATCH;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_EPISODE_TWO_WITH_ZEROS_PADDED;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SCIENCE_FICTION_UPPER_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_BNHA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_TOWATCH;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.anilist.testutil.TypicalAnimes.AKIRA;
import static seedu.anilist.testutil.TypicalAnimes.BNHA;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.commands.AddCommand;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.Episode;
import seedu.anilist.model.anime.Name;
import seedu.anilist.model.anime.Status;
import seedu.anilist.model.genre.Genre;
import seedu.anilist.testutil.AnimeBuilder;

public class AddCommandParserTest {
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Anime expectedAnime = new AnimeBuilder(BNHA)
                .withEpisode(VALID_EPISODE_TWO_WITH_ZEROS_PADDED)
                .withStatus(VALID_STATUS_TOWATCH)
                .withGenres(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BNHA + EPISODE_DESC_EPISODE_TWO
                + STATUS_DESC_TOWATCH + GENRE_DESC_SCIENCE_FICTION, new AddCommand(expectedAnime));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AKIRA + NAME_DESC_BNHA + EPISODE_DESC_EPISODE_TWO
                + STATUS_DESC_TOWATCH + GENRE_DESC_SCIENCE_FICTION, new AddCommand(expectedAnime));


        // multiple genres - all accepted
        Anime expectedAnimeMultipleGenres = new AnimeBuilder(BNHA)
                .withEpisode(VALID_EPISODE_TWO_WITH_ZEROS_PADDED)
                .withStatus(VALID_STATUS_TOWATCH)
                .withGenres(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE, VALID_GENRE_ACTION)
                .build();
        assertParseSuccess(parser, NAME_DESC_BNHA + EPISODE_DESC_EPISODE_TWO + STATUS_DESC_TOWATCH
                + GENRE_DESC_ACTION + GENRE_DESC_SCIENCE_FICTION, new AddCommand(expectedAnimeMultipleGenres));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero episode
        Anime expectedAnimeZeroEpisode = new AnimeBuilder(AKIRA)
                .withStatus(VALID_STATUS_TOWATCH)
                .withGenres(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE, VALID_GENRE_ACTION)
                .build();
        assertParseSuccess(parser, NAME_DESC_AKIRA + STATUS_DESC_TOWATCH
                + GENRE_DESC_ACTION + GENRE_DESC_SCIENCE_FICTION,
                new AddCommand(expectedAnimeZeroEpisode));

        // zero status
        Anime expectedAnimeZeroStatus = new AnimeBuilder(AKIRA)
                .withEpisode(VALID_EPISODE_TWO_WITH_ZEROS_PADDED)
                .withGenres(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE, VALID_GENRE_ACTION)
                .build();
        assertParseSuccess(parser, NAME_DESC_AKIRA + EPISODE_DESC_EPISODE_TWO
                + GENRE_DESC_ACTION + GENRE_DESC_SCIENCE_FICTION,
                new AddCommand(expectedAnimeZeroStatus));

        // zero genre
        Anime expectedAnimeZeroGenre = new AnimeBuilder(AKIRA)
                .withEpisode(VALID_EPISODE_TWO_WITH_ZEROS_PADDED)
                .withStatus(VALID_STATUS_TOWATCH)
                .withGenres()
                .build();
        assertParseSuccess(parser, NAME_DESC_AKIRA + EPISODE_DESC_EPISODE_TWO + STATUS_DESC_TOWATCH,
                new AddCommand(expectedAnimeZeroGenre));

        // no optionals
        Anime expectedAnimeNoOptionals = new AnimeBuilder(AKIRA)
                .withGenres()
                .build();
        assertParseSuccess(parser, NAME_DESC_AKIRA,
                new AddCommand(expectedAnimeNoOptionals));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BNHA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC_BLANK + EPISODE_DESC_EPISODE_TWO + STATUS_DESC_TOWATCH
                + GENRE_DESC_ACTION + GENRE_DESC_SCIENCE_FICTION, Name.MESSAGE_CONSTRAINTS);

        // invalid episode
        assertParseFailure(parser, NAME_DESC_BNHA + INVALID_EPISODE_DESC_DECIMAL + STATUS_DESC_TOWATCH
                + GENRE_DESC_ACTION + GENRE_DESC_SCIENCE_FICTION, Episode.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, NAME_DESC_BNHA + INVALID_EPISODE_DESC_LARGER_THAN_MAX_INT
            + STATUS_DESC_TOWATCH + GENRE_DESC_ACTION + GENRE_DESC_SCIENCE_FICTION, Episode.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, NAME_DESC_BNHA + EPISODE_DESC_EPISODE_TWO + INVALID_STATUS_DESC_ALPHA
                + GENRE_DESC_ACTION + GENRE_DESC_SCIENCE_FICTION, Status.MESSAGE_CONSTRAINTS);

        // invalid genres
        assertParseFailure(parser, NAME_DESC_BNHA + EPISODE_DESC_EPISODE_TWO + STATUS_DESC_TOWATCH
                + INVALID_GENRE_DESC_NON_ALPHANUMERIC + VALID_GENRE_SCIENCE_FICTION_UPPER_CASE,
                Genre.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BNHA
                + EPISODE_DESC_EPISODE_TWO + STATUS_DESC_TOWATCH
                + GENRE_DESC_ACTION + GENRE_DESC_SCIENCE_FICTION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // wrong field specified
        assertParseFailure(parser, NAME_DESC_BNHA
                        + EPISODE_DESC_EPISODE_TWO + STATUS_DESC_TOWATCH
                        + GENRE_DESC_ACTION + GENRE_DESC_SCIENCE_FICTION + ACTION_DESC_ADD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
