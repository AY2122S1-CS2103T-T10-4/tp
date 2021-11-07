package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_NAME_LONGER_THAN_MAX_LENGTH;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_EMPTY;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_NON_ASCII;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_SPACE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_AKIRA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_BNHA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_EIGHTY_SIX;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_FATE_ZERO;
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
        // Empty string
        assertThrows(IllegalArgumentException.class, () -> new Name(INVALID_STRING_EMPTY));

        // Only contains space
        assertThrows(IllegalArgumentException.class, () -> new Name(INVALID_STRING_SPACE));

        // Non ascii
        assertThrows(IllegalArgumentException.class, () -> new Name(INVALID_STRING_NON_ASCII));

        // Length > Name.MAX_LENGTH
        assertThrows(IllegalArgumentException.class, () -> new Name(INVALID_NAME_LONGER_THAN_MAX_LENGTH));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // Empty string
        assertFalse(Name.isValidName(INVALID_STRING_EMPTY));

        // Only contains space
        assertFalse(Name.isValidName(INVALID_STRING_SPACE));

        // Non ascii
        assertFalse(Name.isValidName(INVALID_STRING_NON_ASCII));

        // length > Name.MAX_LENGTH
        assertFalse(Name.isValidName(INVALID_NAME_LONGER_THAN_MAX_LENGTH));

        // Only alphabets
        assertTrue(Name.isValidName(VALID_NAME_BNHA));

        // Alphabets with space
        assertTrue(Name.isValidName(VALID_NAME_AKIRA));

        // Only numeric
        assertTrue(Name.isValidName(VALID_NAME_EIGHTY_SIX));

        // Includes non alphanumeric ascii characters
        assertTrue(Name.isValidName(VALID_NAME_FATE_ZERO));

        // Length = Name.MAX_LENGTH
        assertTrue(Name.isValidName(VALID_NAME_MAX_LENGTH));
    }
}
