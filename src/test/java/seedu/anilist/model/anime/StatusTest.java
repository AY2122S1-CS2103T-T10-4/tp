package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("^")); // only non-alphanumeric characters
        assertFalse(Status.isValidStatus("peter*")); // contains non-alphanumeric characters
        assertFalse(Status.isValidStatus("listening")); // only alphanumeric but not recognised

        // valid status
        assertTrue(Status.isValidStatus("watching")); // recognised lowercase
        assertTrue(Status.isValidStatus("FINISHED")); // recognised uppercase
        assertTrue(Status.isValidStatus("toWATcH")); // recognised mixed case
        assertTrue(Status.isValidStatus("t")); // recognised shortform lowercase
        assertTrue(Status.isValidStatus("W")); // recognised shortform uppercase
    }
}
