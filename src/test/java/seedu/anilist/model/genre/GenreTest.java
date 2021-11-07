package seedu.anilist.model.genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenreTest {

    private static final String INVALID_GENRE_NON_ALPHANUMERICAL = "@#$%^&";
    private static final String INVALID_GENRE_START_WITH_SPACE = " bad";
    private static final String INVALID_GENRE_WITH_DOUBLE_SPACE = "three  word  genres";

    private static final String VALID_GENRE_LOWER_CASE = "fantasy";
    private static final String VALID_GENRE_MIXED_CASE = "supERNatuRAl";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Genre(null));
    }

    @Test
    public void constructor_invalidGenreName_throwsIllegalArgumentException() {
        String invalidGenreName = "";
        assertThrows(IllegalArgumentException.class, () -> new Genre(invalidGenreName));
    }

    @Test
    public void isValidGenreName() {
        // null genre name
        assertThrows(NullPointerException.class, () -> Genre.isValidGenreName(null));

        // invalid genres
        // genre containing non-alphanumerical characters
        assertFalse(Genre.isValidGenreName(INVALID_GENRE_NON_ALPHANUMERICAL));
        // genre starting with space
        assertFalse(Genre.isValidGenreName(INVALID_GENRE_START_WITH_SPACE));
        // genre with double spaces
        assertFalse(Genre.isValidGenreName(INVALID_GENRE_WITH_DOUBLE_SPACE));

        // valid genres
        // genre with lower case
        assertTrue(Genre.isValidGenreName(VALID_GENRE_LOWER_CASE));
        // genre with mixed case
        assertTrue(Genre.isValidGenreName(VALID_GENRE_MIXED_CASE));
    }

    @Test
    public void compareTo() {
        Genre genreOne = new Genre(VALID_GENRE_LOWER_CASE);
        Genre genreOneCopy = new Genre(VALID_GENRE_LOWER_CASE);
        Genre genreTwo = new Genre(VALID_GENRE_MIXED_CASE);

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
