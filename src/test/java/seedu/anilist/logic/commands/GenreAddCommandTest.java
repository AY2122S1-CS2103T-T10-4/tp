package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_GENRE_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_GENRE_SCIENCE_FICTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.anilist.logic.commands.CommandTestUtil.showAnimeAtIndex;
import static seedu.anilist.testutil.TypicalAnimes.FIRST_ANIME_GENRE;
import static seedu.anilist.testutil.TypicalAnimes.getTypicalAnimeList;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_SECOND_ANIME;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.core.Messages;
import seedu.anilist.commons.core.index.Index;
import seedu.anilist.model.AnimeList;
import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;
import seedu.anilist.model.UserPrefs;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.genre.Genre;
import seedu.anilist.testutil.AnimeBuilder;
import seedu.anilist.testutil.GenresDescriptorBuilder;




public class GenreAddCommandTest {
    private static final String ANIME_ONE_GENRE = FIRST_ANIME_GENRE;

    private final Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredListNewGenre_success() {
        Anime updatedAnime = new AnimeBuilder(model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased()))
                .withGenres(ANIME_ONE_GENRE, VALID_GENRE_ACTION)
                .build();
        GenreAddCommand.GenresDescriptor descriptor = DESC_GENRE_ACTION;

        GenreAddCommand genreAddCommand = new GenreAddCommand(INDEX_FIRST_ANIME, descriptor);

        Set<Genre> usedGenres = new HashSet<>(descriptor.getGenres().get());

        String expectedMessage = String.format(GenreAddCommand.MESSAGE_SUCCESS,
                genresSetToString(usedGenres),
                updatedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAnimeList()), new UserPrefs());
        expectedModel.setAnime(model.getFilteredAnimeList().get(0), updatedAnime);

        assertCommandSuccess(genreAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListGenreAlreadyPresent_genreNotAdded() {
        Anime updatedAnime = new AnimeBuilder(model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased()))
                .withGenres(ANIME_ONE_GENRE)
                .build();
        GenreAddCommand.GenresDescriptor descriptor = new GenresDescriptorBuilder()
                .withGenre(ANIME_ONE_GENRE)
                .build();

        GenreAddCommand genreAddCommand = new GenreAddCommand(INDEX_FIRST_ANIME, descriptor);

        Set<Genre> unusedGenres = new HashSet<>(descriptor.getGenres().get());

        String expectedMessage = String.format(GenreAddCommand.MESSAGE_GENRE_PRESENT,
                genresSetToString(unusedGenres),
                updatedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAnimeList()), new UserPrefs());
        expectedModel.setAnime(model.getFilteredAnimeList().get(0), updatedAnime);

        assertCommandSuccess(genreAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListWithAlreadyPresentAndNewGenres_partialSuccess() {
        Anime updatedAnime = new AnimeBuilder(model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased()))
                .withGenres(ANIME_ONE_GENRE, VALID_GENRE_ACTION)
                .build();
        GenreAddCommand.GenresDescriptor descriptor = new GenresDescriptorBuilder()
                .withGenre(ANIME_ONE_GENRE, VALID_GENRE_ACTION)
                .build();

        GenreAddCommand genreAddCommand = new GenreAddCommand(INDEX_FIRST_ANIME, descriptor);

        Set<Genre> usedGenres = new HashSet<>();
        Set<Genre> unusedGenres = new HashSet<>();
        usedGenres.add(new Genre(VALID_GENRE_ACTION));
        unusedGenres.add(new Genre(ANIME_ONE_GENRE));

        String expectedMessage = String.format(GenreAddCommand.MESSAGE_PARTIAL_SUCCESS,
                genresSetToString(usedGenres),
                genresSetToString(unusedGenres),
                updatedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAnimeList()), new UserPrefs());
        expectedModel.setAnime(model.getFilteredAnimeList().get(0), updatedAnime);

        assertCommandSuccess(genreAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showAnimeAtIndex(model, INDEX_FIRST_ANIME);

        Anime updatedAnime = new AnimeBuilder(model.getFilteredAnimeList().get(INDEX_FIRST_ANIME.getZeroBased()))
                .withGenres(ANIME_ONE_GENRE, VALID_GENRE_ACTION)
                .build();
        GenreAddCommand.GenresDescriptor descriptor = DESC_GENRE_ACTION;


        GenreAddCommand genreAddCommand = new GenreAddCommand(INDEX_FIRST_ANIME, descriptor);

        Set<Genre> usedGenres = new HashSet<>(descriptor.getGenres().get());

        String expectedMessage = String.format(GenreAddCommand.MESSAGE_SUCCESS,
                genresSetToString(usedGenres),
                updatedAnime);

        Model expectedModel = new ModelManager(new AnimeList(model.getAnimeList()), new UserPrefs());
        expectedModel.setAnime(model.getFilteredAnimeList().get(0), updatedAnime);

        assertCommandSuccess(genreAddCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAnimeIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAnimeList().size() + 1);
        GenreAddCommand genreAddCommand = new GenreAddCommand(outOfBoundIndex, DESC_GENRE_ACTION);

        assertCommandFailure(genreAddCommand, model, Messages.MESSAGE_INVALID_ANIME_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of anime list
     */
    @Test
    public void execute_invalidAnimeIndexFilteredList_failure() {
        showAnimeAtIndex(model, INDEX_FIRST_ANIME);
        Index outOfBoundIndex = INDEX_SECOND_ANIME;
        // ensures that outOfBoundIndex is still in bounds of anime list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAnimeList().getAnimeList().size());
        GenreAddCommand genreAddCommand = new GenreAddCommand(outOfBoundIndex, DESC_GENRE_ACTION);

        assertCommandFailure(genreAddCommand, model, Messages.MESSAGE_INVALID_ANIME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        GenreAddCommand.GenresDescriptor descriptor = DESC_GENRE_ACTION;
        final GenreAddCommand standardCommand = new GenreAddCommand(INDEX_FIRST_ANIME, descriptor);

        // same values -> returns true
        GenreAddCommand.GenresDescriptor copyDescriptor = new GenreCommand.GenresDescriptor(descriptor);
        GenreAddCommand commandWithSameValues = new GenreAddCommand(INDEX_FIRST_ANIME, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new GenreAddCommand(INDEX_SECOND_ANIME, descriptor)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new GenreAddCommand(INDEX_FIRST_ANIME, DESC_GENRE_SCIENCE_FICTION)));
    }

    /**
     * Converts a {@code Genre} {@code Set} into a {@code String}
     * @param genresSet the set to be converted
     * @return a String listing the items inside the set
     */
    private static String genresSetToString(Set<Genre> genresSet) {
        assert genresSet != null;

        String genresString = Arrays.toString(genresSet.toArray());
        return genresString.substring(1, genresString.length() - 1);
    }
}
