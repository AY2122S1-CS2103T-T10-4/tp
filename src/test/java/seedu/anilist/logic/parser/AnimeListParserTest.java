package seedu.anilist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.anilist.logic.commands.CommandTestUtil.ACTION_DESC_ADD;
import static seedu.anilist.logic.commands.CommandTestUtil.ACTION_DESC_DELETE_SHORT_FORM;
import static seedu.anilist.logic.commands.CommandTestUtil.GENRE_DESC_SCIENCE_FICTION;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_WATCHING;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_EPISODE_ONE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SCIENCE_FICTION_UPPER_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_AKIRA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING_MIXED_CASE;
import static seedu.anilist.testutil.Assert.assertThrows;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.commands.AbortClearCommand;
import seedu.anilist.logic.commands.AddCommand;
import seedu.anilist.logic.commands.ClearCommand;
import seedu.anilist.logic.commands.ConfirmClearCommand;
import seedu.anilist.logic.commands.DeleteCommand;
import seedu.anilist.logic.commands.ExitCommand;
import seedu.anilist.logic.commands.FindCommand;
import seedu.anilist.logic.commands.GenreAddCommand;
import seedu.anilist.logic.commands.GenreCommand;
import seedu.anilist.logic.commands.GenreDeleteCommand;
import seedu.anilist.logic.commands.GenreListCommand;
import seedu.anilist.logic.commands.HelpCommand;
import seedu.anilist.logic.commands.ListCommand;
import seedu.anilist.logic.commands.RenameCommand;
import seedu.anilist.logic.commands.StatsCommand;
import seedu.anilist.logic.commands.UpdateEpisodeCommand;
import seedu.anilist.logic.commands.UpdateStatusCommand;
import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.testutil.AnimeBuilder;
import seedu.anilist.testutil.AnimeUtil;
import seedu.anilist.testutil.EpisodeDescriptorBuilder;
import seedu.anilist.testutil.GenresDescriptorBuilder;
import seedu.anilist.testutil.NameDescriptorBuilder;
import seedu.anilist.testutil.StatusDescriptorBuilder;

public class AnimeListParserTest {

    private final AnimeListParser parser = new AnimeListParser();

    @Test
    public void parseCommand_add() throws Exception {
        Anime anime = new AnimeBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AnimeUtil.getAddCommand(anime));
        assertEquals(new AddCommand(anime), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ANIME.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_ANIME), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        String input = FindCommand.COMMAND_WORD + " n/foo n/bar g/sci fi";
        assertTrue(parser.parseCommand(input) instanceof FindCommand);
    }

    @Test
    public void parseCommand_genreAdd() throws Exception {
        GenreCommand.GenresDescriptor descriptor = new GenresDescriptorBuilder()
                .withGenre(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE)
                .build();
        GenreCommand command = (GenreCommand) parser.parseCommand(GenreCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ANIME.getOneBased() + ACTION_DESC_ADD
                + GENRE_DESC_SCIENCE_FICTION);

        GenreCommand expectedCommand = new GenreAddCommand(INDEX_FIRST_ANIME, descriptor);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_genreDelete() throws Exception {
        GenreCommand.GenresDescriptor descriptor = new GenresDescriptorBuilder()
                .withGenre(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE)
                .build();
        GenreCommand command = (GenreCommand) parser.parseCommand(GenreCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ANIME.getOneBased() + ACTION_DESC_DELETE_SHORT_FORM
                + GENRE_DESC_SCIENCE_FICTION);
        assertEquals(new GenreDeleteCommand(INDEX_FIRST_ANIME, descriptor), command);
    }

    @Test
    public void parseCommand_genrelist() throws Exception {
        assertTrue(parser.parseCommand(GenreListCommand.COMMAND_WORD) instanceof GenreListCommand);
        assertTrue(parser.parseCommand(GenreListCommand.COMMAND_WORD + " 3") instanceof GenreListCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + STATUS_DESC_WATCHING) instanceof ListCommand);
    }

    @Test
    public void parseCommand_rename() throws Exception {
        RenameCommand.NameDescriptor descriptor =
                new NameDescriptorBuilder().withName(VALID_NAME_AKIRA).build();
        String input = RenameCommand.COMMAND_WORD + " " + INDEX_FIRST_ANIME.getOneBased() + " n/ " + VALID_NAME_AKIRA;
        assertEquals(new RenameCommand(INDEX_FIRST_ANIME, descriptor), parser.parseCommand(input));
    }

    @Test
    public void parseCommand_updateEpisode() throws Exception {
        UpdateEpisodeCommand.EpisodeDescriptor descriptor =
                new EpisodeDescriptorBuilder().withEpisode(VALID_EPISODE_ONE).build();
        UpdateEpisodeCommand command = (UpdateEpisodeCommand) parser.parseCommand(
                UpdateEpisodeCommand.COMMAND_WORD + " " + INDEX_FIRST_ANIME.getOneBased() + " e/ "
                        + VALID_EPISODE_ONE);
        assertEquals(new UpdateEpisodeCommand(INDEX_FIRST_ANIME, descriptor), command);
    }

    @Test
    public void parseCommand_updateStatus() throws Exception {
        UpdateStatusCommand.StatusDescriptor descriptor =
                new StatusDescriptorBuilder().withStatus(VALID_STATUS_WATCHING_MIXED_CASE).build();
        UpdateStatusCommand command = (UpdateStatusCommand) parser.parseCommand(
                UpdateStatusCommand.COMMAND_WORD + " " + INDEX_FIRST_ANIME.getOneBased() + " s/ "
                        + VALID_STATUS_WATCHING_MIXED_CASE);
        assertEquals(new UpdateStatusCommand(INDEX_FIRST_ANIME, descriptor), command);
    }

    @Test
    public void parseCommand_stats() throws Exception {
        assertTrue(parser.parseCommand(StatsCommand.COMMAND_WORD) instanceof StatsCommand);
        assertTrue(parser.parseCommand(StatsCommand.COMMAND_WORD + " 4") instanceof StatsCommand);
    }

    @Test
    public void parseConfirmationCommand_abortClear() {
        assertTrue(parser.parseConfirmationCommand("n") instanceof AbortClearCommand);
        assertTrue(parser.parseConfirmationCommand("X") instanceof AbortClearCommand);
        assertTrue(parser.parseConfirmationCommand("  ") instanceof AbortClearCommand);

        //If the previous command requires confirmation (i.e. ClearCommand), these become invalid inputs which
        //would abort the clear operation
        assertTrue(parser.parseConfirmationCommand("list") instanceof AbortClearCommand);
        assertTrue(parser.parseConfirmationCommand("help") instanceof AbortClearCommand);
    }

    @Test
    public void parseConfirmationCommand_confirmClear() {
        assertTrue(parser.parseConfirmationCommand("clear") instanceof ConfirmClearCommand);
        //trailing whitespace
        assertTrue(parser.parseConfirmationCommand("clear  ") instanceof ConfirmClearCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand"));
    }
}
