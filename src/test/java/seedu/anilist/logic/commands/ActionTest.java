package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_ACTION_EMPTY_STRING;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_ACTION_NO_SUCH_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_ACTION_ADD;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_ACTION_DELETE_SHORT_FORM;
import static seedu.anilist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ActionTest {

    @Test
    public void isValidActionTest() {
        // null action string
        assertThrows(NullPointerException.class, () -> Action.isValidAction(null));

        assertFalse(Action.isValidAction(INVALID_ACTION_NO_SUCH_ACTION));
        assertFalse(Action.isValidAction(INVALID_ACTION_EMPTY_STRING));

        assertTrue(Action.isValidAction(VALID_ACTION_ADD));
    }

    @Test void actionFromString() {
        assertThrows(AssertionError.class, () -> Action.actionFromString((String) null));

        assertEquals(Action.DEFAULT, Action.actionFromString(INVALID_ACTION_EMPTY_STRING));
        assertEquals(Action.ADD, Action.actionFromString(VALID_ACTION_ADD));
        assertEquals(Action.DELETE, Action.actionFromString(VALID_ACTION_DELETE_SHORT_FORM));


    }
}
