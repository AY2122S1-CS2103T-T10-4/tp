package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_NAME_AKIRA;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_NAME_BNHA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_BNHA;

import org.junit.jupiter.api.Test;

import seedu.anilist.testutil.NameDescriptorBuilder;

public class NameDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        RenameCommand.NameDescriptor descriptorWithSameValues =
            new RenameCommand.NameDescriptor(DESC_NAME_AKIRA);
        assertTrue(DESC_NAME_AKIRA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_NAME_AKIRA.equals(DESC_NAME_AKIRA));

        // null -> returns false
        assertFalse(DESC_NAME_AKIRA.equals(null));

        // different types -> returns false
        assertFalse(DESC_NAME_AKIRA.equals(5));

        // different values -> returns false
        assertFalse(DESC_NAME_AKIRA.equals(DESC_NAME_BNHA));

        // different names -> returns false
        RenameCommand.NameDescriptor renamedDesc =
            new NameDescriptorBuilder(DESC_NAME_AKIRA).withName(VALID_NAME_BNHA).build();
        assertFalse(DESC_NAME_AKIRA.equals(renamedDesc));
    }
}
