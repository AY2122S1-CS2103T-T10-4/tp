package seedu.anilist.logic.commands;

import static seedu.anilist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.anilist.testutil.TypicalAnimes.getTypicalAnime;
import static seedu.anilist.testutil.TypicalAnimes.getTypicalAnimeList;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.Model;
import seedu.anilist.model.ModelManager;
import seedu.anilist.model.UserPrefs;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.NameContainsKeywordsPredicate;
import seedu.anilist.model.anime.Status;
import seedu.anilist.model.anime.StatusEqualsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ConfirmClearCommand}.
 */
public class ConfirmClearCommandTest {

    /** {@code Predicate} that always evaluate to false */
    private Predicate<Anime> predicateShowNoAnime = unused -> false;

    @Test
    public void execute_emptyAnimeList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ConfirmClearCommand(), model, ConfirmClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyFilteredList_success() {
        //does not clear anime since there are no animes in the filtered list
        Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());
        model.updateFilteredAnimeList(predicateShowNoAnime);
        Model expectedModel = new ModelManager(getTypicalAnimeList(), new UserPrefs());
        expectedModel.updateFilteredAnimeList(predicateShowNoAnime);

        assertCommandSuccess(new ConfirmClearCommand(), model, ConfirmClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyUnfilteredAnimeList_success() {
        Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ConfirmClearCommand(), model, ConfirmClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyFilteredByStatusAnimeList_success() {
        //Constructs a model containing TypicalAnimes and a filtered list containing
        //the animes of status 'watching'
        Status watchingStatus = new Status("watching");
        StatusEqualsPredicate predicate =
                new StatusEqualsPredicate(watchingStatus);
        Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());
        model.updateFilteredAnimeList(predicate);

        //Constructs the expected model containing TypicalAnimes\animes matching 'watching'
        //status and a filtered list containing no anime
        Model expectedModel = new ModelManager();
        for (Anime anime: getTypicalAnime()) {
            if (!predicate.test(anime)) {
                expectedModel.addAnime(anime);
            }
        }
        expectedModel.updateFilteredAnimeList(predicateShowNoAnime);

        assertCommandSuccess(new ConfirmClearCommand(), model, ConfirmClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyFilteredByKeywordAnimeList_success() throws ParseException {
        //Constructs a model containing TypicalAnimes and a filtered list containing
        //the animes that match keywords
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(List.of("titan", "shooter", "man"));
        Model model = new ModelManager(getTypicalAnimeList(), new UserPrefs());
        model.updateFilteredAnimeList(predicate);

        //Constructs the expected model containing TypicalAnimes\animes matching keywords
        //and a filtered list containing no anime
        Model expectedModel = new ModelManager();
        for (Anime anime: getTypicalAnime()) {
            if (!predicate.test(anime)) {
                expectedModel.addAnime(anime);
            }
        }
        expectedModel.updateFilteredAnimeList(predicateShowNoAnime);

        assertCommandSuccess(new ConfirmClearCommand(), model, ConfirmClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
