package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.anilist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.anilist.testutil.TypicalAnime.getTypicalAnimeList;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_SECOND_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.Messages;
import seedu.anilist.commons.core.index.Index;
import seedu.anilist.logic.commands.EditCommand.EditAnimeDescriptor;
import seedu.anilist.model.AnimeList;
import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;
import seedu.anilist.model.UserPrefs;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.testutil.AnimeBuilder;
import seedu.anilist.testutil.EditPersonDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Anime editedAnime = new AnimeBuilder().build();
        EditAnimeDescriptor descriptor = new EditPersonDescriptorBuilder(editedAnime).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANIME, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAniList()), new UserPrefs());
        expectedModel.setAnime(model.getFilteredAnimeList().get(0), editedAnime);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredAnimeList().size());
        Anime lastAnime = model.getFilteredAnimeList().get(indexLastPerson.getZeroBased());

        AnimeBuilder personInList = new AnimeBuilder(lastAnime);
        Anime editedAnime = personInList.withName(VALID_NAME_BOB).withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditAnimeDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAniList()), new UserPrefs());
        expectedModel.setAnime(lastAnime, editedAnime);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANIME, new EditAnimeDescriptor());
        Anime editedAnime = model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAniList()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_ANIME);

        Anime animeInFilteredList = model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased());
        Anime editedAnime = new AnimeBuilder(animeInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANIME,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAniList()), new UserPrefs());
        expectedModel.setAnime(model.getFilteredAnimeList().get(0), editedAnime);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Anime firstAnime = model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased());
        EditCommand.EditAnimeDescriptor descriptor = new EditPersonDescriptorBuilder(firstAnime).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ANIME, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_ANIME);

        // edit person in filtered list into a duplicate in anime list
        Anime animeInList = model.getAniList().getAnimeList().get(INDEX_SECOND_ANIME.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANIME,
                new EditPersonDescriptorBuilder(animeInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAnimeList().size() + 1);
        EditCommand.EditAnimeDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ANIME_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of anime list
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_ANIME);
        Index outOfBoundIndex = INDEX_SECOND_ANIME;
        // ensures that outOfBoundIndex is still in bounds of anime list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAniList().getAnimeList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ANIME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ANIME, DESC_AMY);

        // same values -> returns true
        EditCommand.EditAnimeDescriptor copyDescriptor = new EditCommand.EditAnimeDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ANIME, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ANIME, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ANIME, DESC_BOB)));
    }

}
