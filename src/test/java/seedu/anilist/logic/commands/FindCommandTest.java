package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.commons.core.Messages.MESSAGE_ANIME_LISTED_OVERVIEW;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandSuccess;
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

import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;
import seedu.anilist.model.UserPrefs;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.GenresContainedPredicate;
import seedu.anilist.model.anime.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private final Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAnimeList(), new UserPrefs());

    @Test
    public void equals() throws ParseException {
        NameContainsKeywordsPredicate firstPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different keywords -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noAnimeFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 0);
        Predicate<Anime> predicate = preparePredicate(
            Collections.emptyList(), Collections.emptyList());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAnimeList());
    }

    @Test
    public void execute_multipleNames_multipleAnimesFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 3);
        Predicate<Anime> predicate = preparePredicate(
            Arrays.asList("Man", "Elfen", "night"), null);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CSM, ELF, FSN), model.getFilteredAnimeList());
    }

    @Test
    public void execute_multipleGenres_multipleAnimesFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 2);
        Predicate<Anime> predicate = preparePredicate(
            null, Arrays.asList("action", "horror"));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BRS, DBZ), model.getFilteredAnimeList());
    }

    @Test
    public void execute_multipleNameAndGenres_multipleAnimesFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 2);
        Predicate<Anime> predicate = preparePredicate(
            Arrays.asList("Black", "dragon"), Arrays.asList("action", "horror"));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BRS, DBZ), model.getFilteredAnimeList());
    }

    @Test
    public void execute_multipleNameAndGenres_noAnimeFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 0);
        Predicate<Anime> predicate = preparePredicate(
                List.of("chainsaw"), Arrays.asList("action", "horror"));
        FindCommand command = new FindCommand(predicate);
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
