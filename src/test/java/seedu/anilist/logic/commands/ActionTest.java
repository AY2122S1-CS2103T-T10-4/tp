package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_ACTION_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_EMPTY;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_ACTION_ADD;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_ACTION_ADD_SHORT_FORM;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_ACTION_DELETE_MIXED_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_ACTION_DELETE_SHORT_FORM_UPPER_CASE;
import static seedu.anilist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ActionTest {

    @Test
    public void isValidActionTest() {
        // Null action string
        assertThrows(NullPointerException.class, () -> Action.isValidAction(null));

        // Empty String
        assertFalse(Action.isValidAction(INVALID_STRING_EMPTY));

        // Action that is not add / delete
        assertFalse(Action.isValidAction(INVALID_ACTION_ALPHA));

        // Add all lower case
        assertTrue(Action.isValidAction(VALID_ACTION_ADD));

        // Add shortform lower case
        assertTrue(Action.isValidAction(VALID_ACTION_ADD_SHORT_FORM));

        // Delete mixed case
        assertTrue(Action.isValidAction(VALID_ACTION_DELETE_MIXED_CASE));

        // Delete shortform upper case
        assertTrue(Action.isValidAction(VALID_ACTION_DELETE_SHORT_FORM_UPPER_CASE));
    }

    @Test void actionFromString() {
        // Null action string
        assertThrows(AssertionError.class, () -> Action.actionFromString(null));

        // Invalid action string
        assertEquals(Action.DEFAULT, Action.actionFromString(INVALID_STRING_EMPTY));

        // Add
        assertEquals(Action.ADD, Action.actionFromString(VALID_ACTION_ADD));

        // Delete shortform
        assertEquals(Action.DELETE, Action.actionFromString(VALID_ACTION_DELETE_SHORT_FORM_UPPER_CASE));


    }
}
