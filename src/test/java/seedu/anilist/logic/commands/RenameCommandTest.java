package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_NAME_AKIRA;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_NAME_BNHA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_BNHA;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.anilist.logic.commands.CommandTestUtil.showAnimeAtIndex;
import static seedu.anilist.testutil.TypicalAnimes.getTypicalAnimeList;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_SECOND_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.Messages;
import seedu.anilist.commons.core.index.Index;
import seedu.anilist.model.AnimeList;
import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;
import seedu.anilist.model.UserPrefs;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.testutil.AnimeBuilder;
import seedu.anilist.testutil.NameDescriptorBuilder;

public class RenameCommandTest {
    private final Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Anime renamedAnime = new AnimeBuilder(
            model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased()))
            .withName(VALID_NAME_BNHA)
            .build();
        RenameCommand.NameDescriptor descriptor = new NameDescriptorBuilder()
            .withName(VALID_NAME_BNHA).build();
        RenameCommand renameCommand = new RenameCommand(INDEX_FIRST_ANIME, descriptor);

        String expectedMessage = String.format(
            RenameCommand.MESSAGE_RENAME_ANIME_SUCCESS,
            renamedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAnimeList()), new UserPrefs());
        expectedModel.setAnime(model.getFilteredAnimeList().get(0), renamedAnime);

        assertCommandSuccess(renameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showAnimeAtIndex(model, INDEX_FIRST_ANIME);

        Anime renamedAnime = new AnimeBuilder(
            model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased()))
            .withName(VALID_NAME_BNHA).build();
        RenameCommand.NameDescriptor descriptor = new NameDescriptorBuilder()
            .withName(VALID_NAME_BNHA).build();
        RenameCommand renameCommand = new RenameCommand(INDEX_FIRST_ANIME, descriptor);

        String expectedMessage = String.format(
            RenameCommand.MESSAGE_RENAME_ANIME_SUCCESS,
            renamedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAnimeList()), new UserPrefs());
        expectedModel.setAnime(model.getFilteredAnimeList().get(0), renamedAnime);

        assertCommandSuccess(renameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAnimeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAnimeList().size() + 1);
        RenameCommand.NameDescriptor descriptor = new NameDescriptorBuilder()
            .withName(VALID_NAME_BNHA).build();
        RenameCommand renameCommand = new RenameCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(renameCommand, model, Messages.MESSAGE_OUT_OF_RANGE_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of anime list
     */
    @Test
    public void execute_invalidAnimeIndexFilteredList_failure() {
        showAnimeAtIndex(model, INDEX_FIRST_ANIME);
        Index outOfBoundIndex = INDEX_SECOND_ANIME;
        // ensures that outOfBoundIndex is still in bounds of anime list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAnimeList().getAnimeList().size());
        RenameCommand.NameDescriptor descriptor = new NameDescriptorBuilder()
            .withName(VALID_NAME_BNHA).build();
        RenameCommand renameCommand = new RenameCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(renameCommand, model, Messages.MESSAGE_OUT_OF_RANGE_INDEX);
    }

    @Test
    public void equals() {
        final RenameCommand standardCommand = new RenameCommand(INDEX_FIRST_ANIME, DESC_NAME_AKIRA);

        // same values -> returns true
        RenameCommand.NameDescriptor copyDescriptor = new RenameCommand.NameDescriptor(
            DESC_NAME_AKIRA);
        RenameCommand commandWithSameValues = new RenameCommand(INDEX_FIRST_ANIME, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RenameCommand(INDEX_SECOND_ANIME, DESC_NAME_AKIRA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RenameCommand(INDEX_FIRST_ANIME, DESC_NAME_BNHA)));
    }

}
