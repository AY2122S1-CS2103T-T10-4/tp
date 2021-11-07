package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_TOWATCH;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_WATCHING;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_WATCHING_SHORTFORM;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING_MIXED_CASE;

import org.junit.jupiter.api.Test;

import seedu.anilist.testutil.StatusDescriptorBuilder;

public class StatusDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        UpdateStatusCommand.StatusDescriptor descriptorWithSameValues =
                new UpdateStatusCommand.StatusDescriptor(DESC_TOWATCH);
        assertTrue(DESC_TOWATCH.equals(descriptorWithSameValues));

        // orignal and shortform -> returns true
        UpdateStatusCommand.StatusDescriptor descriptorWithShortForm =
                new UpdateStatusCommand.StatusDescriptor(DESC_WATCHING_SHORTFORM);
        assertTrue(DESC_WATCHING.equals(descriptorWithShortForm));

        // same object -> returns true
        assertTrue(DESC_TOWATCH.equals(DESC_TOWATCH));

        // null -> returns false
        assertFalse(DESC_TOWATCH.equals(null));

        // different types -> returns false
        assertFalse(DESC_TOWATCH.equals(5));

        // different values -> returns false
        assertFalse(DESC_TOWATCH.equals(DESC_WATCHING_SHORTFORM));

        // different status -> returns false
        UpdateStatusCommand.StatusDescriptor editedStatusDesc =
                new StatusDescriptorBuilder(DESC_TOWATCH).withStatus(VALID_STATUS_WATCHING_MIXED_CASE).build();
        assertFalse(DESC_TOWATCH.equals(editedStatusDesc));

    }
}
