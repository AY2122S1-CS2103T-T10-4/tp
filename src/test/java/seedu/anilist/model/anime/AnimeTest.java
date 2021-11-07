package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_EPISODE_ONE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SCIENCE_FICTION_UPPER_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_BNHA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_FINISHED_UPPER_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_TOWATCH;
import static seedu.anilist.testutil.Assert.assertThrows;
import static seedu.anilist.testutil.TypicalAnimes.AOT;
import static seedu.anilist.testutil.TypicalAnimes.BNHA;

import org.junit.jupiter.api.Test;

import seedu.anilist.testutil.AnimeBuilder;

public class AnimeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Anime anime = new AnimeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> anime.getGenres().remove(0));
    }

    @Test
    public void isSameAnime() {
        // same object -> returns true
        assertTrue(AOT.isSameAnime(AOT));

        // null -> returns false
        assertFalse(AOT.isSameAnime(null));

        // same name, all other attributes different -> returns true
        Anime editedAot = new AnimeBuilder(AOT)
                .withEpisode(VALID_EPISODE_ONE)
                .withStatus(VALID_STATUS_FINISHED_UPPER_CASE)
                .withGenres(VALID_GENRE_ACTION).build();
        assertTrue(AOT.isSameAnime(editedAot));

        // different name, all other attributes same -> returns false
        editedAot = new AnimeBuilder(AOT).withName(VALID_NAME_BNHA).build();
        assertFalse(AOT.isSameAnime(editedAot));

        // name differs in case, all other attributes same -> returns false
        Anime editedBnha = new AnimeBuilder(BNHA).withName(VALID_NAME_BNHA.toLowerCase()).build();
        assertFalse(BNHA.isSameAnime(editedBnha));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BNHA + " ";
        editedBnha = new AnimeBuilder(BNHA).withName(nameWithTrailingSpaces).build();
        assertFalse(BNHA.isSameAnime(editedBnha));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Anime aotCopy = new AnimeBuilder(AOT).build();
        assertEquals(AOT, aotCopy);

        // same object -> returns true
        assertEquals(AOT, AOT);

        // null -> returns false
        assertNotEquals(null, AOT);

        // different type -> returns false
        assertNotEquals(5, AOT);

        // different anime -> returns false
        assertNotEquals(AOT, BNHA);

        // different name -> returns false
        Anime editedAot = new AnimeBuilder(AOT).withName(VALID_NAME_BNHA).build();
        assertNotEquals(AOT, editedAot);

        // different episode -> returns false
        editedAot = new AnimeBuilder(AOT).withEpisode(VALID_EPISODE_ONE).build();
        assertNotEquals(AOT, editedAot);

        // different status -> returns false
        editedAot = new AnimeBuilder(AOT).withStatus(VALID_STATUS_TOWATCH).build();
        assertNotEquals(AOT, editedAot);

        // different genres -> returns false
        editedAot = new AnimeBuilder(AOT).withGenres(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE).build();
        assertNotEquals(AOT, editedAot);
    }
}
