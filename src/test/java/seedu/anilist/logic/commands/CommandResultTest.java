package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class CommandResultTest {

    private final String feedback = "feedback";
    private final String differentFeedback = "different feedback";

    @Test
    public void isStats() {
        CommandResult commandResultA = new CommandResult(feedback);
        assertFalse(commandResultA.isStats());

        CommandResult commandResultB = new CommandResult(feedback, true, false);
        assertTrue(commandResultB.isStats());
    }

    @Test
    public void isExit() {
        CommandResult commandResultA = new CommandResult(feedback);
        assertFalse(commandResultA.isExit());

        CommandResult commandResultB = new CommandResult(feedback, false, true);
        assertTrue(commandResultB.isExit());
    }

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult(feedback);

        // same values -> returns true
        assertEquals(commandResult, new CommandResult(feedback));
        assertEquals(commandResult, new CommandResult(feedback, false, false));

        // same object -> returns true
        assertEquals(commandResult, commandResult);

        // null -> returns false
        assertNotEquals(commandResult, null);

        // different types -> returns false
        assertNotEquals(commandResult, 0.5f);

        // different feedbackToUser value -> returns false
        assertNotEquals(commandResult, new CommandResult(differentFeedback));

        // different exit value -> returns false
        assertNotEquals(commandResult, new CommandResult(feedback, false, true));

        // different stats value -> returns false
        assertNotEquals(commandResult, new CommandResult(feedback, true, false));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult(feedback);

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult(feedback).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult(differentFeedback).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult(feedback, false, true).hashCode());

        // different stats value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                new CommandResult(feedback, true, false).hashCode());
    }
}
