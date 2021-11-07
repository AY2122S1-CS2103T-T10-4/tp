package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_TOWATCH;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_WATCHING;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_WATCHING_SHORTFORM;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING;

import org.junit.jupiter.api.Test;

import seedu.anilist.testutil.StatusDescriptorBuilder;

public class StatusDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        UpdateStatusCommand.StatusDescriptor descriptorWithSameValues =
                new UpdateStatusCommand.StatusDescriptor(DESC_TOWATCH);
        assertEquals(descriptorWithSameValues, DESC_TOWATCH);

        // orignal and shortform -> returns true
        UpdateStatusCommand.StatusDescriptor descriptorWithShortForm =
                new UpdateStatusCommand.StatusDescriptor(DESC_WATCHING_SHORTFORM);
        assertEquals(descriptorWithShortForm, DESC_WATCHING);

        // same object -> returns true
        assertEquals(DESC_TOWATCH, DESC_TOWATCH);

        // null -> returns false
        assertNotEquals(DESC_TOWATCH, null);

        // different types -> returns false
        assertNotEquals(DESC_TOWATCH, 5);

        // different values -> returns false
        assertNotEquals(DESC_TOWATCH, DESC_WATCHING_SHORTFORM);

        // different status -> returns false
        UpdateStatusCommand.StatusDescriptor editedStatusDesc =
                new StatusDescriptorBuilder(DESC_TOWATCH).withStatus(VALID_STATUS_WATCHING).build();
        assertNotEquals(editedStatusDesc, DESC_TOWATCH);

    }
}
