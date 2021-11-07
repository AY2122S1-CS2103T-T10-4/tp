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
import java.util.List;

import seedu.anilist.commons.core.index.Index;
import seedu.anilist.logic.commands.exceptions.CommandException;
import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.AnimeList;
import seedu.anilist.model.Model;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.Episode;
import seedu.anilist.model.anime.Name;
import seedu.anilist.model.anime.NameContainsKeywordsPredicate;
import seedu.anilist.testutil.EpisodeDescriptorBuilder;
import seedu.anilist.testutil.GenresDescriptorBuilder;
import seedu.anilist.testutil.NameDescriptorBuilder;
import seedu.anilist.testutil.StatusDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    // Invalid Strings
    public static final String INVALID_STRING_SPACE = " ";
    public static final String INVALID_STRING_EMPTY = "";
    public static final String INVALID_STRING_NON_ASCII = "中文";


    //-----------------------------------------------------NAME---------------------------------------------------------
    // Valid Names
    public static final String VALID_NAME_AKIRA = "Akira";
    public static final String VALID_NAME_BNHA = "Boku No Hero Academia";
    public static final String VALID_NAME_EIGHTY_SIX = "86";
    public static final String VALID_NAME_FATE_ZERO = "Fate/Zero";
    public static final String VALID_NAME_MAX_LENGTH = "A".repeat(Name.MAX_LEN);

    // Invalid Names
    public static final String INVALID_NAME_LONGER_THAN_MAX_LENGTH = "F".repeat(Name.MAX_LEN + 1);

    // Valid Name DESC
    public static final String NAME_DESC_AKIRA = " " + PREFIX_NAME + VALID_NAME_AKIRA;
    public static final String NAME_DESC_BNHA = " " + PREFIX_NAME + VALID_NAME_BNHA;

    // Invalid Name DESC
    public static final String INVALID_NAME_DESC_BLANK = " " + PREFIX_NAME + INVALID_STRING_SPACE;
    public static final String INVALID_NAME_DESC_NON_ASCII = " " + PREFIX_NAME + INVALID_STRING_NON_ASCII;


    //-----------------------------------------------------EPISODE------------------------------------------------------
    // Valid Episodes
    public static final String VALID_EPISODE_ZERO = "0";
    public static final String VALID_EPISODE_ONE = "1";
    public static final String VALID_EPISODE_TWO_WITH_ZEROS_PADDED = "0000002";
    public static final String VALID_EPISODE_MAX = String.valueOf(Episode.MAX_EPISODE);

    // Invalid Episodes

    public static final String INVALID_EPISODE_ALPHA = "two";
    public static final String INVALID_EPISODE_NEG = "-1";
    public static final String INVALID_EPISODE_DECIMAL = "0.1";
    public static final String INVALID_EPISODE_LARGER_THAN_MAX_EPISODE = String.valueOf(Episode.MAX_EPISODE + 1);
    public static final String INVALID_EPISODE_LARGER_THAN_MAX_INT =
            Long.toString((long) Integer.MAX_VALUE + 1);

    // Valid Episode DESC
    public static final String EPISODE_DESC_EPISODE_ONE = " " + PREFIX_EPISODE + VALID_EPISODE_ONE;
    public static final String EPISODE_DESC_EPISODE_TWO = " " + PREFIX_EPISODE + VALID_EPISODE_TWO_WITH_ZEROS_PADDED;

    // Invalid Episode DESC
    public static final String INVALID_EPISODE_DESC_NEG = " " + PREFIX_EPISODE + INVALID_EPISODE_NEG;
    public static final String INVALID_EPISODE_DESC_DECIMAL = " "
            + PREFIX_EPISODE + INVALID_EPISODE_DECIMAL;
    public static final String INVALID_EPISODE_DESC_LARGER_THAN_MAX_INT = " "
            + PREFIX_EPISODE + INVALID_EPISODE_LARGER_THAN_MAX_INT;


    //-----------------------------------------------------STATUS-------------------------------------------------------
    // Valid Statuses
    public static final String VALID_STATUS_TOWATCH = "towatch";
    public static final String VALID_STATUS_TOWATCH_SHORT_FORM = "t";
    public static final String VALID_STATUS_WATCHING_MIXED_CASE = "WAtcHinG";
    public static final String VALID_STATUS_WATCHING_SHORT_FORM_UPPER_CASE = "W";
    public static final String VALID_STATUS_FINISHED_UPPER_CASE = "FINISHED";

    // Invalid Statuses
    public static final String INVALID_STATUS_ALPHA = "TOWATCHINGG";
    public static final String INVALID_STATUS_NUMERIC = "261";

    // Valid Status DESC
    public static final String STATUS_DESC_TOWATCH = " " + PREFIX_STATUS + VALID_STATUS_TOWATCH;
    public static final String STATUS_DESC_WATCHING = " " + PREFIX_STATUS + VALID_STATUS_WATCHING_MIXED_CASE;

    // Invalid Status DESC
    public static final String INVALID_STATUS_DESC_ALPHA = " " + PREFIX_STATUS + INVALID_STATUS_ALPHA;
    public static final String INVALID_STATUS_DESC_NUMERIC = " " + PREFIX_STATUS + INVALID_STATUS_NUMERIC;


    //-----------------------------------------------------GENRE--------------------------------------------------------
    // Valid Genres
    public static final String VALID_GENRE_ACTION = "action";
    public static final String VALID_GENRE_SCIENCE_FICTION_UPPER_CASE = "SCI FI";
    public static final String VALID_GENRE_SUPERNATURAL_MIXED_CASE = "supERNatuRAl";

    // Invalid Genres
    public static final String INVALID_GENRE_NON_ALPHANUMERIC = "comedy*";
    public static final String INVALID_GENRE_ALPHA = "tentacles";
    public static final String INVALID_GENRE_NUMERIC = "01101000 01100101 01101110 01110100 01100001 01101001";

    // Valid Genre DESC
    public static final String GENRE_DESC_ACTION = " " + PREFIX_GENRE + VALID_GENRE_ACTION;
    public static final String GENRE_DESC_SCIENCE_FICTION = " " + PREFIX_GENRE + VALID_GENRE_SCIENCE_FICTION_UPPER_CASE;

    // Invalid Genre DESC
    public static final String INVALID_GENRE_DESC_NON_ALPHANUMERIC = " "
            + PREFIX_GENRE + INVALID_GENRE_NON_ALPHANUMERIC;


    //-----------------------------------------------------ACTION-------------------------------------------------------
    // Valid Actions
    public static final String VALID_ACTION_ADD = "add";
    public static final String VALID_ACTION_ADD_SHORT_FORM = "a";
    public static final String VALID_ACTION_DELETE_MIXED_CASE = "DeleTE";
    public static final String VALID_ACTION_DELETE_SHORT_FORM_UPPER_CASE = "D";

    // Invalid Actions
    public static final String INVALID_ACTION_ALPHA = "dancing";
    public static final String INVALID_ACTION_NUMERIC = "007";

    // Valid Action DESC
    public static final String ACTION_DESC_ADD = " " + PREFIX_ACTION + VALID_ACTION_ADD;
    public static final String ACTION_DESC_DELETE_SHORT_FORM = " " + PREFIX_ACTION
            + VALID_ACTION_DELETE_SHORT_FORM_UPPER_CASE;

    // Invalid Action DESC
    public static final String INVALID_ACTION_DESC_ALPHA = " " + PREFIX_ACTION + INVALID_ACTION_ALPHA;


    //-----------------------------------------------------PREAMBLES----------------------------------------------------
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";


    //-----------------------------------------------------COMMANDS-----------------------------------------------------
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
        DESC_EPISODE_ZERO = new EpisodeDescriptorBuilder().withEpisode(VALID_EPISODE_ZERO).build();
        DESC_EPISODE_ONE = new EpisodeDescriptorBuilder().withEpisode(VALID_EPISODE_ONE).build();
        DESC_TOWATCH = new StatusDescriptorBuilder().withStatus(VALID_STATUS_TOWATCH).build();
        DESC_WATCHING = new StatusDescriptorBuilder().withStatus(VALID_STATUS_WATCHING_MIXED_CASE).build();
        DESC_WATCHING_SHORTFORM = new StatusDescriptorBuilder().withStatus(VALID_STATUS_WATCHING_SHORT_FORM_UPPER_CASE)
                .build();
        DESC_GENRE_ACTION = new GenresDescriptorBuilder().withGenre(VALID_GENRE_ACTION).build();
        DESC_GENRE_SCIENCE_FICTION = new GenresDescriptorBuilder().withGenre(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE)
                .build();
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
            model.updateFilteredAnimeList(new NameContainsKeywordsPredicate(List.of(splitName[0])));
        } catch (ParseException pe) {
            throw new AssertionError("Error should not happen.", pe);
        }

        assertEquals(1, model.getFilteredAnimeList().size());
    }

}
