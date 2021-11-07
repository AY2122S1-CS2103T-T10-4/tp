package seedu.anilist.model.genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_NON_ALPHANUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_NUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_EMPTY;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SCIENCE_FICTION_UPPER_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SUPERNATURAL_MIXED_CASE;
import static seedu.anilist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenreTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Genre(null));
    }

    @Test
    public void constructor_invalidGenreName_throwsIllegalArgumentException() {
        // Genre that is not in genrelist
        assertThrows(IllegalArgumentException.class, () -> new Genre(INVALID_STRING_EMPTY));
        assertThrows(IllegalArgumentException.class, () -> new Genre(INVALID_GENRE_NUMERIC));

    }

    @Test
    public void isValidGenreName() {
        // null genre name
        assertThrows(NullPointerException.class, () -> Genre.isValidGenreName(null));

        // Genre that is not in genrelist
        assertFalse(Genre.isValidGenreName(INVALID_GENRE_ALPHA));
        assertFalse(Genre.isValidGenreName(INVALID_GENRE_NON_ALPHANUMERIC));

        // valid genre lower case
        assertTrue(Genre.isValidGenreName(VALID_GENRE_ACTION));

        // valid genre upper case
        assertTrue(Genre.isValidGenreName(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE));

        // valid genre mixed case
        assertTrue(Genre.isValidGenreName(VALID_GENRE_SUPERNATURAL_MIXED_CASE));
    }

    @Test
    public void compareTo() {
        Genre genreOne = new Genre(VALID_GENRE_ACTION);
        Genre genreOneCopy = new Genre(VALID_GENRE_ACTION);
        Genre genreTwo = new Genre(VALID_GENRE_SUPERNATURAL_MIXED_CASE);

        // null input
        assertEquals(0, genreOne.compareTo(null));
        // same object
        assertEquals(0, genreOne.compareTo(genreOne));
        // same genreName
        assertEquals(0, genreOne.compareTo(genreOneCopy));
        // other genre is 'larger'
        assertTrue(genreOne.compareTo(genreTwo) < 0);
        // other genre is 'smaller'
        assertTrue(genreTwo.compareTo(genreOne) > 0);
    }
}
