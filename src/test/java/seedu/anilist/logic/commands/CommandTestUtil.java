package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_EPISODE;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.anilist.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.anilist.commons.core.index.Index;
import seedu.anilist.logic.commands.exceptions.CommandException;
import seedu.anilist.model.AnimeList;
import seedu.anilist.model.Model;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.NameContainsKeywordsPredicate;
import seedu.anilist.testutil.EditAnimeDescriptorBuilder;
import seedu.anilist.testutil.EpisodeDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AKIRA = "Akira";
    public static final String VALID_NAME_BNHA = "Boku No Hero Academia";
    public static final String VALID_TAG_SHOUNEN = "shounen";
    public static final String VALID_TAG_SUPERHERO = "superhero";
    public static final String VALID_EPISODE_ONE = "1";
    public static final String VALID_EPISODE_TWO = "2";

    public static final String NAME_DESC_AKIRA = " " + PREFIX_NAME + VALID_NAME_AKIRA;
    public static final String NAME_DESC_BNHA = " " + PREFIX_NAME + VALID_NAME_BNHA;
    public static final String TAG_DESC_SHOUNEN = " " + PREFIX_TAG + VALID_TAG_SHOUNEN;
    public static final String TAG_DESC_SUPERHERO = " " + PREFIX_TAG + VALID_TAG_SUPERHERO;
    public static final String EPISODE_DESC_EPISODE_ONE = " " + PREFIX_EPISODE + VALID_EPISODE_ONE;
    public static final String EPISODE_DESC_EPISODE_TWO = " " + PREFIX_EPISODE + VALID_EPISODE_TWO;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Akira&"; // '&' not allowed in names
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "shounen*"; // '*' not allowed in tags
    public static final String INVALID_EPISODE_DESC_NEG = " " + PREFIX_EPISODE + "-1"; // '-' not allowed in episode
    public static final String INVALID_EPISODE_DESC_DECIMAL = " "
        + PREFIX_EPISODE + "0.1"; // '.' not allowed in episode

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditAnimeDescriptor DESC_AKIRA;
    public static final EditCommand.EditAnimeDescriptor DESC_BNHA;

    public static final UpdateEpisodeCommand.EpisodeDescriptor DESC_EPISODE_ZERO;
    public static final UpdateEpisodeCommand.EpisodeDescriptor DESC_EPISODE_ONE;

    static {
        DESC_AKIRA = new EditAnimeDescriptorBuilder().withName(VALID_NAME_AKIRA)
                .withTags(VALID_TAG_SHOUNEN).build();
        DESC_BNHA = new EditAnimeDescriptorBuilder().withName(VALID_NAME_BNHA)
                .withTags(VALID_TAG_SHOUNEN, VALID_TAG_SUPERHERO).build();
        DESC_EPISODE_ZERO = new EpisodeDescriptorBuilder().withEpisode("0").build();
        DESC_EPISODE_ONE = new EpisodeDescriptorBuilder().withEpisode("1").build();
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

        Anime anime = model.getFilteredAnimeList().get(targetIndex.getZeroBased());
        final String[] splitName = anime.getName().fullName.split("\\s+");
        model.updateFilteredAnimeList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredAnimeList().size());
    }

}
