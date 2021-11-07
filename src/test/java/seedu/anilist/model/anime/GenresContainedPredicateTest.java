package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_NON_ALPHANUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_NUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_EMPTY;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_NON_ASCII;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_SPACE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SCIENCE_FICTION_UPPER_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SUPERNATURAL_MIXED_CASE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.testutil.AnimeBuilder;


public class GenresContainedPredicateTest {

    private static final String VALID_NAME_AOT = "attack on titan";
    private static final String VALID_GENRE_HORROR = "horror";
    private static final String VALID_GENRE_COMEDY = "comedy";
    private static final String VALID_GENRE_HORROR_MIXED_CASE = "hORror";
    private static final String VALID_GENRE_COMEDY_MIXED_CASE = "coMedy";

    @Test
    public void equals() throws ParseException {
        List<String> firstGenreKeywordList = Collections.singletonList(VALID_GENRE_ACTION);
        List<String> secondGenreKeywordList = Arrays.asList(VALID_GENRE_SUPERNATURAL_MIXED_CASE, VALID_GENRE_HORROR);
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
        assertThrows(ParseException.class, () ->
            new GenresContainedPredicate(Collections.singletonList(INVALID_STRING_EMPTY)));

        assertThrows(ParseException.class, () ->
                new GenresContainedPredicate(Collections.singletonList(INVALID_STRING_SPACE)));

        assertThrows(ParseException.class, () ->
            new GenresContainedPredicate(Collections.singletonList(INVALID_STRING_NON_ASCII)));

        assertThrows(ParseException.class, () ->
                new GenresContainedPredicate(Collections.singletonList(INVALID_GENRE_ALPHA)));

        assertThrows(ParseException.class, () ->
                new GenresContainedPredicate(Collections.singletonList(INVALID_GENRE_NUMERIC)));

        assertThrows(ParseException.class, () ->
                new GenresContainedPredicate(Collections.singletonList(INVALID_GENRE_NON_ALPHANUMERIC)));
    }

    @Test
    public void test_genresSetContainsGenre_returnsTrue() throws ParseException {
        // One keyword
        GenresContainedPredicate predicate =
            new GenresContainedPredicate(Collections.singletonList(VALID_GENRE_COMEDY));
        assertTrue(predicate.test(new AnimeBuilder().withGenres(VALID_GENRE_HORROR, VALID_GENRE_COMEDY).build()));

        // Multiple keywords
        predicate = new GenresContainedPredicate(Arrays.asList(VALID_GENRE_COMEDY, VALID_GENRE_HORROR));
        assertTrue(predicate.test(new AnimeBuilder().withGenres(VALID_GENRE_HORROR, VALID_GENRE_COMEDY).build()));

        // Only one matching keyword
        predicate = new GenresContainedPredicate(Arrays.asList(VALID_GENRE_COMEDY, VALID_GENRE_ACTION));
        assertTrue(predicate.test(new AnimeBuilder().withGenres(VALID_GENRE_HORROR, VALID_GENRE_COMEDY).build()));
    }

    @Test
    public void test_genresSetContainsMixedCaseGenre_returnsTrue() throws ParseException {
        // Genre will always be lower-cased
        // One keyword
        GenresContainedPredicate predicate =
            new GenresContainedPredicate(Collections.singletonList(VALID_GENRE_COMEDY_MIXED_CASE));
        assertTrue(predicate.test(new AnimeBuilder().withGenres(VALID_GENRE_HORROR, VALID_GENRE_COMEDY).build()));


        // Multiple keywords
        predicate = new GenresContainedPredicate(Arrays.asList(VALID_GENRE_COMEDY_MIXED_CASE,
                VALID_GENRE_HORROR_MIXED_CASE));
        assertTrue(predicate.test(new AnimeBuilder().withGenres(VALID_GENRE_HORROR, VALID_GENRE_COMEDY).build()));

        // Only one matching keyword
        predicate = new GenresContainedPredicate(Arrays.asList(VALID_GENRE_COMEDY_MIXED_CASE,
                VALID_GENRE_SUPERNATURAL_MIXED_CASE));
        assertTrue(predicate.test(new AnimeBuilder().withGenres(VALID_GENRE_HORROR, VALID_GENRE_COMEDY).build()));
    }

    @Test
    public void test_genresSetDoesNotContainGenre_returnsFalse() throws ParseException {
        // Zero keywords
        // Note: This is how it should work if you pass an empty list into genresContainedPredicates's
        // constructor. However, a predicate is not created if it does not have a g/ arg.
        GenresContainedPredicate predicate = new GenresContainedPredicate(Collections.emptyList());
        assertFalse(predicate.test(new AnimeBuilder().withGenres(VALID_GENRE_HORROR, VALID_GENRE_COMEDY).build()));

        // Non-matching keyword
        predicate = new GenresContainedPredicate(List.of(VALID_GENRE_SUPERNATURAL_MIXED_CASE));
        assertFalse(predicate.test(new AnimeBuilder().withGenres(VALID_GENRE_HORROR, VALID_GENRE_COMEDY).build()));

        // Keywords match names, but does not match genre
        predicate = new GenresContainedPredicate(Arrays.asList(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE,
                VALID_GENRE_ACTION));
        assertFalse(predicate.test(new AnimeBuilder().withName(VALID_NAME_AOT)
            .withGenres(VALID_GENRE_HORROR, VALID_GENRE_COMEDY).build()));
    }

}
