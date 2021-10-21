package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.commons.core.Messages.MESSAGE_ANIME_LISTED_OVERVIEW;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.anilist.testutil.TypicalAnimes.CSM;
import static seedu.anilist.testutil.TypicalAnimes.ELF;
import static seedu.anilist.testutil.TypicalAnimes.FSN;
import static seedu.anilist.testutil.TypicalAnimes.getTypicalAnimeList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;
import seedu.anilist.model.UserPrefs;
import seedu.anilist.model.anime.NameMatchesKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAnimeList(), new UserPrefs());

    @Test
    public void equals() {
        NameMatchesKeywordsPredicate firstPredicate =
                new NameMatchesKeywordsPredicate(Collections.singletonList("first"));
        NameMatchesKeywordsPredicate secondPredicate =
                new NameMatchesKeywordsPredicate(Collections.singletonList("second"));

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

        // different anime -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noAnimeFound() {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 0);
        NameMatchesKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAnimeList());
    }

    @Test
    public void execute_multipleKeywords_multipleAnimesFound() {
        String expectedMessage = String.format(MESSAGE_ANIME_LISTED_OVERVIEW, 3);
        NameMatchesKeywordsPredicate predicate = preparePredicate("Man Elfen night");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredAnimeList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CSM, ELF, FSN), model.getFilteredAnimeList());
    }

    /**
     * Parses {@code userInput} into a {@code NameMatchesKeywordsPredicate}.
     */
    private NameMatchesKeywordsPredicate preparePredicate(String userInput) {
        return new NameMatchesKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
