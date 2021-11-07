package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.anilist.logic.commands.CommandTestUtil.showAnimeAtIndex;
import static seedu.anilist.testutil.TypicalAnimes.getTypicalAnimeList;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_SECOND_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.Messages;
import seedu.anilist.commons.core.index.Index;
import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;
import seedu.anilist.model.UserPrefs;
import seedu.anilist.model.anime.Anime;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private final Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Anime animeToDelete = model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ANIME);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ANIME_SUCCESS, animeToDelete);

        ModelManager expectedModel = new ModelManager(model.getAnimeList(), new UserPrefs());
        expectedModel.deleteAnime(animeToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAnimeList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_OUT_OF_RANGE_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showAnimeAtIndex(model, INDEX_FIRST_ANIME);

        Anime animeToDelete = model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ANIME);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ANIME_SUCCESS, animeToDelete);

        Model expectedModel = new ModelManager(model.getAnimeList(), new UserPrefs());
        expectedModel.deleteAnime(animeToDelete);
        showNoAnime(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAnimeAtIndex(model, INDEX_FIRST_ANIME);

        Index outOfBoundIndex = INDEX_SECOND_ANIME;
        // ensures that outOfBoundIndex is still in bounds of anime list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAnimeList().getAnimeList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_OUT_OF_RANGE_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ANIME);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ANIME);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ANIME);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different anime -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no anime.
     */
    private void showNoAnime(Model model) {
        model.updateFilteredAnimeList(p -> false);

        assertTrue(model.getFilteredAnimeList().isEmpty());
    }
}
