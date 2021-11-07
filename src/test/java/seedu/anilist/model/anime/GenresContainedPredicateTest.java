package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.testutil.AnimeBuilder;

public class GenresContainedPredicateTest {

    @Test
    public void equals() throws ParseException {
        List<String> firstGenreKeywordList = Collections.singletonList("action");
        List<String> secondGenreKeywordList = Arrays.asList("supernatural", "horror");
        GenresContainedPredicate firstPredicate = new GenresContainedPredicate(firstGenreKeywordList);
        GenresContainedPredicate secondPredicate = new GenresContainedPredicate(secondGenreKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GenresContainedPredicate secondPredicateCopy = new GenresContainedPredicate(secondGenreKeywordList);
        assertTrue(secondPredicateCopy.equals(secondPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keyword -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void constructor_invalidGenreKeyword_throwsParseException() {
        // Empty String
        assertThrows(ParseException.class, () ->
            new GenresContainedPredicate(Collections.singletonList("")));

        // Non-ASCII
        assertThrows(ParseException.class, () ->
            new GenresContainedPredicate(Collections.singletonList("非ascii字符")));
    }

    @Test
    public void test_genresSetContainsGenre_returnsTrue() throws ParseException {
        // One keyword
        GenresContainedPredicate predicate =
            new GenresContainedPredicate(Collections.singletonList("comedy"));
        assertTrue(predicate.test(new AnimeBuilder().withGenres("horror", "comedy").build()));


        // Multiple keywords
        predicate = new GenresContainedPredicate(Arrays.asList("comedy", "horror"));
        assertTrue(predicate.test(new AnimeBuilder().withGenres("horror", "comedy").build()));

        // Only one matching keyword
        predicate = new GenresContainedPredicate(Arrays.asList("comedy", "fantasy"));
        assertTrue(predicate.test(new AnimeBuilder().withGenres("horror", "comedy").build()));
    }

    @Test
    public void test_genresSetContainsMixedCaseGenre_returnsTrue() throws ParseException {
        // Genre will always be lower-cased
        // One keyword
        GenresContainedPredicate predicate =
            new GenresContainedPredicate(Collections.singletonList("coMedy"));
        assertTrue(predicate.test(new AnimeBuilder().withGenres("horror", "comedy").build()));


        // Multiple keywords
        predicate = new GenresContainedPredicate(Arrays.asList("coMeDy", "hORror"));
        assertTrue(predicate.test(new AnimeBuilder().withGenres("horror", "comedy").build()));

        // Only one matching keyword
        predicate = new GenresContainedPredicate(Arrays.asList("coMeDy", "SupERnaTUrAl"));
        assertTrue(predicate.test(new AnimeBuilder().withGenres("horror", "comedy").build()));
    }

    @Test
    public void test_genresSetDoesNotContainGenre_returnsFalse() throws ParseException {
        // Zero keywords
        // Note: This is how it should work if you pass an empty list into genresContainedPredicates's
        // constructor. However, a predicate is not created if it does not have a g/ arg.
        GenresContainedPredicate predicate = new GenresContainedPredicate(Collections.emptyList());
        assertFalse(predicate.test(new AnimeBuilder().withGenres("horror", "comedy").build()));

        // Non-matching keyword
        predicate = new GenresContainedPredicate(Arrays.asList("supernatural"));
        assertFalse(predicate.test(new AnimeBuilder().withGenres("horror", "comedy").build()));

        // Keywords match names, but does not match genre
        predicate = new GenresContainedPredicate(Arrays.asList("sci fi", "fantasy"));
        assertFalse(predicate.test(new AnimeBuilder().withName("attack on titan")
            .withGenres("horror", "comedy").build()));
    }

}
