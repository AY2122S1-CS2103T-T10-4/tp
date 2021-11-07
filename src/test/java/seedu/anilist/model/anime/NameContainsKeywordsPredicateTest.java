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

public class NameContainsKeywordsPredicateTest {

    @Test
    public void constructor_invalidNameKeywords_throwsParseException() {
        // Empty String
        assertThrows(ParseException.class, () ->
            new GenresContainedPredicate(Collections.singletonList("")));

        // Non-ASCII
        assertThrows(ParseException.class, () ->
            new GenresContainedPredicate(Collections.singletonList("非ascii字符")));
    }

    @Test
    public void equals() throws ParseException {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keyword -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() throws ParseException {
        // One keyword
        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Attack"));
        assertTrue(predicate.test(new AnimeBuilder().withName("Attack Black").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Attack", "Black"));
        assertTrue(predicate.test(new AnimeBuilder().withName("Attack Black").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Black", "Chainsaw"));
        assertTrue(predicate.test(new AnimeBuilder().withName("Attack Chainsaw").build()));
    }

    @Test
    public void test_nameContainsMixedCaseKeywords_returnsTrue() throws ParseException {
        // One keyword
        NameContainsKeywordsPredicate predicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("AtTack"));
        assertTrue(predicate.test(new AnimeBuilder().withName("aTtack black").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("attAck", "Black"));
        assertTrue(predicate.test(new AnimeBuilder().withName("ATtack BLacK").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("bLack", "cHainsaw"));
        assertTrue(predicate.test(new AnimeBuilder().withName("Attack Chainsaw").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() throws ParseException {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new AnimeBuilder().withName("Attack on Titan").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Chainsaw"));
        assertFalse(predicate.test(new AnimeBuilder().withName("Black Rock Shooter").build()));

        // Keywords match genres, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("action", "adventure"));
        assertFalse(predicate.test(new AnimeBuilder().withName("Attack on Titan")
                .withGenres("action", "adventure").build()));
    }
}
