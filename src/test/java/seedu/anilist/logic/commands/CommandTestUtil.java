package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_EPISODE;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.anilist.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.anilist.commons.core.index.Index;
import seedu.anilist.logic.commands.exceptions.CommandException;
import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.AnimeList;
import seedu.anilist.model.Model;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.NameContainsKeywordsPredicate;
import seedu.anilist.testutil.EpisodeDescriptorBuilder;
import seedu.anilist.testutil.GenresDescriptorBuilder;
import seedu.anilist.testutil.NameDescriptorBuilder;
import seedu.anilist.testutil.StatusDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AKIRA = "Akira";
    public static final String VALID_NAME_BNHA = "Boku No Hero Academia";
    public static final String VALID_NAME_MAX_LENGTH = "A".repeat(140);
    public static final String VALID_GENRE_ACTION = "action";
    public static final String VALID_GENRE_SCIENCE_FICTION = "sci fi";
    public static final String VALID_EPISODE_ONE = "1";
    public static final String VALID_EPISODE_TWO = "2";
    public static final String VALID_ACTION_ADD = "add";
    public static final String VALID_ACTION_DELETE_SHORT_FORM = "d";
    public static final String VALID_STATUS_TOWATCH = "towatch";
    public static final String VALID_STATUS_WATCHING = "watching";

    public static final String INVALID_ACTION_EMPTY_STRING = "";
    public static final String INVALID_EPISODE_LARGER_THAN_MAX_INT =
        Long.toString((long) Integer.MAX_VALUE + 1);
    public static final String INVALID_ACTION_NO_SUCH_ACTION = "dancing";
    public static final String INVALID_GENRE_NOT_IN_LIST = "tentacles";

    public static final String NAME_DESC_AKIRA = " " + PREFIX_NAME + VALID_NAME_AKIRA;
    public static final String NAME_DESC_BNHA = " " + PREFIX_NAME + VALID_NAME_BNHA;
    public static final String GENRE_DESC_ACTION = " " + PREFIX_GENRE + VALID_GENRE_ACTION;
    public static final String GENRE_DESC_SCIENCE_FICTION = " " + PREFIX_GENRE + VALID_GENRE_SCIENCE_FICTION;
    public static final String EPISODE_DESC_EPISODE_ONE = " " + PREFIX_EPISODE + VALID_EPISODE_ONE;
    public static final String EPISODE_DESC_EPISODE_TWO = " " + PREFIX_EPISODE + VALID_EPISODE_TWO;
    public static final String STATUS_DESC_TOWATCH = " " + PREFIX_STATUS + VALID_STATUS_TOWATCH;
    public static final String STATUS_DESC_WATCHING = " " + PREFIX_STATUS + VALID_STATUS_WATCHING;
    public static final String ACTION_DESC_ADD = " " + PREFIX_ACTION + VALID_ACTION_ADD;
    public static final String ACTION_DESC_DELETE = " " + PREFIX_ACTION + VALID_ACTION_DELETE_SHORT_FORM;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " "; // cannot be blank
    public static final String INVALID_NAME_DESC_NONASCII = " " + PREFIX_NAME + "中文";
    public static final String INVALID_GENRE_DESC = " " + PREFIX_GENRE + "shounen*"; // '*' not allowed in genres
    public static final String INVALID_EPISODE_DESC_NEG = " " + PREFIX_EPISODE + "-1"; // '-' not allowed in episode
    public static final String INVALID_EPISODE_DESC_DECIMAL = " "
        + PREFIX_EPISODE + "0.1"; // '.' not allowed in episode
    public static final String INVALID_EPISODE_DESC_LARGER_THAN_MAX_INT = " "
        + PREFIX_EPISODE + INVALID_EPISODE_LARGER_THAN_MAX_INT;
    public static final String INVALID_ACTION_DESC = " " + PREFIX_ACTION + INVALID_ACTION_NO_SUCH_ACTION;
    public static final String INVALID_STATUS_DESC_ALPHA = " " + PREFIX_STATUS + "TOWATCHINGG";
    public static final String INVALID_STATUS_DESC_NUMERIC = " " + PREFIX_STATUS + "261";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final RenameCommand.NameDescriptor DESC_NAME_AKIRA;
    public static final RenameCommand.NameDescriptor DESC_NAME_BNHA;
    public static final UpdateEpisodeCommand.EpisodeDescriptor DESC_EPISODE_ZERO;
    public static final UpdateEpisodeCommand.EpisodeDescriptor DESC_EPISODE_ONE;
    public static final UpdateStatusCommand.StatusDescriptor DESC_TOWATCH;
    public static final UpdateStatusCommand.StatusDescriptor DESC_WATCHING;
    public static final UpdateStatusCommand.StatusDescriptor DESC_WATCHING_SHORTFORM;
    public static final GenreCommand.GenresDescriptor DESC_GENRE_ACTION;
    public static final GenreCommand.GenresDescriptor DESC_GENRE_SCIENCE_FICTION;

    static {
        DESC_NAME_AKIRA = new NameDescriptorBuilder().withName(VALID_NAME_AKIRA).build();
        DESC_NAME_BNHA = new NameDescriptorBuilder().withName(VALID_NAME_BNHA).build();
        DESC_EPISODE_ZERO = new EpisodeDescriptorBuilder().withEpisode("0").build();
        DESC_EPISODE_ONE = new EpisodeDescriptorBuilder().withEpisode("1").build();
        DESC_TOWATCH = new StatusDescriptorBuilder().withStatus("towatch").build();
        DESC_WATCHING = new StatusDescriptorBuilder().withStatus("watching").build();
        DESC_WATCHING_SHORTFORM = new StatusDescriptorBuilder().withStatus("w").build();
        DESC_GENRE_ACTION = new GenresDescriptorBuilder().withGenre(VALID_GENRE_ACTION).build();
        DESC_GENRE_SCIENCE_FICTION = new GenresDescriptorBuilder().withGenre(VALID_GENRE_SCIENCE_FICTION).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the anime list, filtered anime list and selected anime in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AnimeList expectedAnimeList = new AnimeList(actualModel.getAnimeList());
        List<Anime> expectedFilteredList = new ArrayList<>(actualModel.getFilteredAnimeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAnimeList, actualModel.getAnimeList());
        assertEquals(expectedFilteredList, actualModel.getFilteredAnimeList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the anime at the given {@code targetIndex} in the
     * {@code model}'s anime list.
     */
    public static void showAnimeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAnimeList().size());
        try {
            Anime anime = model.getFilteredAnimeList().get(targetIndex.getZeroBased());
            final String[] splitName = anime.getName().fullName.split("\\s+");
            assertTrue(splitName.length > 0);
            assertNotNull(splitName[0]);
            model.updateFilteredAnimeList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));
        } catch (ParseException pe) {
            throw new AssertionError("Error should not happen.", pe);
        }

        assertEquals(1, model.getFilteredAnimeList().size());
    }

}
