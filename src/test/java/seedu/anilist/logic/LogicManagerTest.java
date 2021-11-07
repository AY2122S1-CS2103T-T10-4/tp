package seedu.anilist.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.anilist.commons.core.Messages.MESSAGE_OUT_OF_RANGE_INDEX;
import static seedu.anilist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.anilist.logic.commands.CommandTestUtil.NAME_DESC_AKIRA;
import static seedu.anilist.testutil.Assert.assertThrows;
import static seedu.anilist.testutil.TypicalAnimes.AKIRA;
import static seedu.anilist.testutil.TypicalAnimes.getTypicalAnimeList;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.anilist.commons.core.GuiSettings;
import seedu.anilist.commons.core.Messages;
import seedu.anilist.logic.commands.AddCommand;
import seedu.anilist.logic.commands.CommandResult;
import seedu.anilist.logic.commands.ConfirmClearCommand;
import seedu.anilist.logic.commands.ListCommand;
import seedu.anilist.logic.commands.exceptions.CommandException;
import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;
import seedu.anilist.model.ReadOnlyAnimeList;
import seedu.anilist.model.UserPrefs;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.storage.JsonAnimeListStorage;
import seedu.anilist.storage.JsonUserPrefsStorage;
import seedu.anilist.storage.StorageManager;
import seedu.anilist.testutil.AnimeBuilder;
import seedu.anilist.ui.TabOption;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAnimeListStorage animeListStorage =
                new JsonAnimeListStorage(temporaryFolder.resolve("animeList.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(animeListStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_OUT_OF_RANGE_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand,
                String.format(Messages.MESSAGE_ANIME_LISTED_OVERVIEW, model.getFilteredAnimeList().size()),
                model);
    }

    @Test
    public void execute_validConfirmationCommand_success() throws Exception {
        // Setup LogicManager with modelWithAnime
        JsonAnimeListStorage animeListStorage =
                new JsonAnimeListStorage(temporaryFolder.resolve("animeList.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(animeListStorage, userPrefsStorage);
        Model modelWithAnime = new ModelManager(getTypicalAnimeList(), new UserPrefs());
        logic = new LogicManager(modelWithAnime, storage);

        //command 'clear' entered twice
        String cmd = "clear";
        logic.execute(cmd);
        CommandResult finalResult = logic.execute(cmd);

        assertEquals(ConfirmClearCommand.MESSAGE_SUCCESS, finalResult.getFeedbackToUser());
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAnimeListIoExceptionThrowingStub
        JsonAnimeListStorage animeListStorage =
                new JsonAnimeListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAnimeList.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(animeListStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AKIRA;
        Anime expectedAnime = new AnimeBuilder(AKIRA).withGenres().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addAnime(expectedAnime);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getAnimeList_nonNull_getsNonNullAnimeList() {
        assertNotNull(logic.getAnimeList());
    }

    @Test
    public void getFilteredAnimeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredAnimeList().remove(0));
    }

    @Test
    public void getAnimeListFilePath_nonNull_getsNonNullFilePath() {
        assertNotNull(logic.getAnimeListFilePath());
    }

    @Test
    public void getGuiSettings_nonNull_getsGuiSettings() {
        assertNotNull(logic.getGuiSettings());
    }

    @Test
    public void setGuiSettings_newSettings_setsGuiSettings() {
        int windowWidth = 400;
        int windowHeight = 500;
        GuiSettings guiSettings = new GuiSettings(windowWidth, windowHeight, 0, 0);
        logic.setGuiSettings(guiSettings);
        assertEquals(guiSettings, logic.getGuiSettings());
    }

    @Test
    public void setCurrentTab_allTab_setsCurrentTab() {
        logic.setCurrentTab(TabOption.TabOptions.ALL);
        assertEquals(TabOption.TabOptions.ALL, logic.getCurrentTab().getCurrentTab());
    }

    @Test
    public void getCurrentTab_nonNull_getsNonNullTab() {
        assertNotNull(logic.getCurrentTab());
    }

    @Test
    public void getStats_nonNull_getsNonNullStats() {
        assertNotNull(logic.getStats());
    }

    @Test
    public void setThemeCss_darkTheme_setsThemeCss() {
        String themeCss = "DarkTheme.css";
        logic.setThemeCss(themeCss);
        assertEquals(themeCss, logic.getThemeCss());
    }

    @Test
    public void getThemeCss_nonNull_getNonNullCss() {
        assertNotNull(logic.getThemeCss());
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAnimeList(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonAnimeListIoExceptionThrowingStub extends JsonAnimeListStorage {
        private JsonAnimeListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAnimeList(ReadOnlyAnimeList animeList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
