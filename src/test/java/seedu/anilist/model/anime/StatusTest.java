package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STATUS_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STATUS_NUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_EMPTY;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_SPACE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_FINISHED_UPPER_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_TOWATCH;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_TOWATCH_SHORT_FORM;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING_MIXED_CASE;
import static seedu.anilist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        // Status that is not defined
        assertThrows(IllegalArgumentException.class, () -> new Status(INVALID_STRING_SPACE));
        assertThrows(IllegalArgumentException.class, () -> new Status(INVALID_STATUS_NUMERIC));
    }

    @Test
    public void isValidStatus() {
        // Null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // Status that is not defined
        assertFalse(Status.isValidStatus(INVALID_STRING_EMPTY));
        assertFalse(Status.isValidStatus(INVALID_STATUS_ALPHA));

        // Towatch
        assertTrue(Status.isValidStatus(VALID_STATUS_TOWATCH));

        // Towatch short form
        assertTrue(Status.isValidStatus(VALID_STATUS_TOWATCH_SHORT_FORM));

        // Watching mixed case
        assertTrue(Status.isValidStatus(VALID_STATUS_WATCHING_MIXED_CASE));

        // Finished upper case
        assertTrue(Status.isValidStatus(VALID_STATUS_FINISHED_UPPER_CASE));

    }
}
