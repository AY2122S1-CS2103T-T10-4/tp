package seedu.anilist.model.genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.anilist.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class GenreListTest {

    @Test
    public void getListOfGenresAsString() {
        String[] listOfGenresCopy = GenreList.getListOfGenres();
        String correctString = listOfGenresToString(listOfGenresCopy);

        String actualString = GenreList.getListOfGenresAsString();
        assertEquals(correctString, actualString);
    }

    /**
     * getListOfGenres should return a copy of the original array
     * modifying it's contents should not affect original array.
     */
    @Test
    public void getListOfGenres() {
        String[] listOfGenresCopy = GenreList.getListOfGenres();
        if (listOfGenresCopy.length > 0) {
            Arrays.fill(listOfGenresCopy, "foobar foobar");

            String wrongString = listOfGenresToString(listOfGenresCopy);
            String actualString = GenreList.getListOfGenresAsString();
            assertNotEquals(wrongString, actualString);
        }
    }

    /**
     * contains should return true if given
     * string is inside GenreList, false otherwise
     */
    @Test
    public void contains() {
        // null input
        assertThrows(NullPointerException.class, () -> GenreList.contains(null));
        // Valid Genre
        assertTrue(GenreList.contains(VALID_GENRE_ACTION));
        // Invalid Genre
        assertFalse(GenreList.contains(INVALID_GENRE_ALPHA));
    }

    /**
     * Converts a list of Genres which is a String array into a String.
     * The genres will be sorted in an ascending order.
     * Each entry is separated by a comma and space.
     */
    private String listOfGenresToString(String[] listOfGenres) {
        String[] listOfGenresCopy = Arrays.stream(listOfGenres).toArray(String[]::new);
        Arrays.sort(listOfGenresCopy);
        String result = "";
        for (String genre : listOfGenresCopy) {
            result += "    - ";
            result += genre;
            result += "\n";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }
}
