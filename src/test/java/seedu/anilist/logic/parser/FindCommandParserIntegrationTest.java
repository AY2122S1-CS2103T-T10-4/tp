package seedu.anilist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.anilist.commons.core.Messages.MESSAGE_ANIME_LISTED_OVERVIEW;
import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_DESC_NON_ALPHANUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_NAME_DESC_BLANK;
import static seedu.anilist.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.anilist.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.testutil.TypicalAnimes.BRS;
import static seedu.anilist.testutil.TypicalAnimes.CSM;
import static seedu.anilist.testutil.TypicalAnimes.DBZ;
import static seedu.anilist.testutil.TypicalAnimes.ELF;
import static seedu.anilist.testutil.TypicalAnimes.FSN;
import static seedu.anilist.testutil.TypicalAnimes.getTypicalAnimeList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.commands.FindCommand;
import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;
import seedu.anilist.model.UserPrefs;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.GenresContainedPredicate;
import seedu.anilist.model.anime.Name;
import seedu.anilist.model.anime.NameContainsKeywordsPredicate;
import seedu.anilist.model.genre.Genre;

public class FindCommandParserIntegrationTest {
    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    private final Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAnimeList(), new UserPrefs());
    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void execute_zeroKeywords_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, PREAMBLE_WHITESPACE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, INVALID_NAME_DESC_BLANK, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_GENRE_DESC_NON_ALPHANUMERIC, Genre.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void execute_multipleNames_multipleAnimesFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 3);
        Predicate<Anime> predicate = preparePredicate(
            Arrays.asList("Man", "Elfen", "night"), null);
        FindCommand command = parser.parse(" n/Man n/Elfen n/night");
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CSM, ELF, FSN), model.getFilteredAnimeList());
    }

    @Test
    public void execute_multipleGenres_multipleAnimesFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 2);
        Predicate<Anime> predicate = preparePredicate(
            null, Arrays.asList("action", "horror"));
        FindCommand command = parser.parse(" g/action g/horror");
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BRS, DBZ), model.getFilteredAnimeList());
    }

    @Test
    public void execute_multipleNameAndGenres_multipleAnimesFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 2);
        Predicate<Anime> predicate = preparePredicate(
            Arrays.asList("Black", "dragon"), Arrays.asList("action", "horror"));
        FindCommand command = parser.parse(" n/Black n/dragon g/action g/horror");
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BRS, DBZ), model.getFilteredAnimeList());
    }

    @Test
    public void execute_multipleNameAndGenres_noAnimeFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 0);
        Predicate<Anime> predicate = preparePredicate(
                List.of("chainsaw"), Arrays.asList("action", "horror"));
        FindCommand command = parser.parse(" n/chainsaw g/action g/horror");
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAnimeList());
    }

    /**
     * Parses {@code nameKeywords} and {@code genreKeywords} into a {@code Predicate<Anime>}.
     */
    private Predicate<Anime> preparePredicate(List<String> nameKeywords, List<String> genreKeywords)
        throws ParseException {
        if (nameKeywords == null && genreKeywords == null) {
            throw new AssertionError("This should not be ran.");
        } else if (nameKeywords == null) {
            return new GenresContainedPredicate(genreKeywords);
        } else if (genreKeywords == null) {
            return new NameContainsKeywordsPredicate(nameKeywords);
        } else {
            return new NameContainsKeywordsPredicate(nameKeywords).and(
                new GenresContainedPredicate(genreKeywords));
        }
    }



}
