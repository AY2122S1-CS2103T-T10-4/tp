package seedu.anilist.logic.commands;

import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.anilist.logic.commands.GenreListCommand.SHOWING_GENRE_LIST_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;

public class GenreListCommandTest {
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_genreList_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_GENRE_LIST_MESSAGE);
        assertCommandSuccess(new GenreListCommand(), model, expectedCommandResult, expectedModel);
    }
}
