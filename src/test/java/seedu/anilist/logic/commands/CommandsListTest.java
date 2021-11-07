package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class CommandsListTest {

    @Test
    public void getListOfCommandsAsString() {
        String[] listOfCommandsCopy = CommandsList.getListOfCommands();
        String correctString = listOfCommandsToString(listOfCommandsCopy);

        String actualString = CommandsList.getListOfCommandsAsString();
        assertEquals(correctString, actualString);
    }

    /**
     * getListOfCommands should return a copy of the original array
     * modifying it's contents should not affect original array.
     */
    @Test
    public void getListOfCommands() {
        String[] listOfCommandsCopy = CommandsList.getListOfCommands();
        if (listOfCommandsCopy.length > 0) {
            Arrays.fill(listOfCommandsCopy, "foobar foobar");

            String wrongString = listOfCommandsToString(listOfCommandsCopy);
            String actualString = CommandsList.getListOfCommandsAsString();
            assertNotEquals(wrongString, actualString);
        }
    }

    /**
     * Converts a list of Commands which is a String array into a String.
     * Each entry is separated by a comma and space.
     */
    private String listOfCommandsToString(String[] listOfCommands) {
        String result = "";
        for (String command : listOfCommands) {
            result += "    - ";
            result += command;
            result += "\n";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }
}
