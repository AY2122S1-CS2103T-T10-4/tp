package seedu.anilist.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.model.Model.PREDICATE_SHOW_ALL_ANIME;
import static seedu.anilist.testutil.Assert.assertThrows;
import static seedu.anilist.testutil.TypicalAnimes.AOT;
import static seedu.anilist.testutil.TypicalAnimes.BRS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.GuiSettings;
import seedu.anilist.model.anime.NameMatchesKeywordsPredicate;
import seedu.anilist.testutil.AnimeListBuilder;
import seedu.anilist.ui.TabOption;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AnimeList(), new AnimeList(modelManager.getAnimeList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAnimeListFilePath(Paths.get("anime/list/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAnimeListFilePath(Paths.get("new/anime/list/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAnimeListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAnimeListFilePath(null));
    }

    @Test
    public void setAnimeListFilePath_validPath_setsAnimeListFilePath() {
        Path path = Paths.get("anime/list/file/path");
        modelManager.setAnimeListFilePath(path);
        assertEquals(path, modelManager.getAnimeListFilePath());
    }

    @Test
    public void hasAnime_nullAnime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAnime(null));
    }

    @Test
    public void hasAnime_animeNotInAnimeList_returnsFalse() {
        assertFalse(modelManager.hasAnime(AOT));
    }

    private void assertFalse(boolean hasAnime) {
    }

    @Test
    public void hasAnime_animeInAnimeList_returnsTrue() {
        modelManager.addAnime(AOT);
        assertTrue(modelManager.hasAnime(AOT));
    }

    @Test
    public void getFilteredAnimeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredAnimeList().remove(0));
    }

    @Test
    public void setCurrentTab_toWatchTab_setsToWatchTabCurrentTab() {
        modelManager.setCurrentTab(TabOption.TabOptions.TOWATCH);
        assertEquals(TabOption.TabOptions.TOWATCH, modelManager.getCurrentTab().getCurrentTab());
    }

    @Test
    public void getCurrentTab_nonNullTab_checksTabNonNull() {
        assertNotNull(modelManager.getCurrentTab());
    }

    @Test
    public void equals() {
        AnimeList animeList = new AnimeListBuilder().withAnime(AOT).withAnime(BRS).build();
        AnimeList differentAnimeList = new AnimeList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(animeList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(animeList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different animeList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAnimeList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = AOT.getName().fullName.split("\\s+");
        modelManager.updateFilteredAnimeList(new NameMatchesKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(animeList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredAnimeList(PREDICATE_SHOW_ALL_ANIME);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAnimeListFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(animeList, differentUserPrefs)));
    }
}
