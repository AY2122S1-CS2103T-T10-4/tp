package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_MAX_LENGTH;
import static seedu.anilist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName(" Danshi Koukousei no Nichijou")); // starting with space
        assertFalse(Name.isValidName(VALID_NAME_MAX_LENGTH + "A")); // exceed max length

        // valid name
        assertTrue(Name.isValidName("Danshi Koukousei no Nichijou")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName(":;.'!")); // punctuation only
        assertTrue(Name.isValidName("~!@#$%^&()+-=:;")); // non-alphanumeric ascii characters
        assertTrue(Name.isValidName("3-gatsu no Lion")); // starting with numeric character containing punctuation
        assertTrue(Name.isValidName("No. 6")); // starting with alphabet containing punctuation
        assertTrue(Name.isValidName("BLEACH")); // with all capital letters
        // long names
        assertTrue(Name.isValidName("Higehiro: After Being Rejected, I Shaved and Took in a High School Runaway"));
        assertTrue(Name.isValidName(VALID_NAME_MAX_LENGTH)); // max length
        assertTrue(Name.isValidName("Gintama'")); // name containing apostrophe
    }
}
