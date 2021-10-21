package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.anilist.testutil.AnimeBuilder;

public class NameMatchesKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameMatchesKeywordsPredicate firstPredicate = new NameMatchesKeywordsPredicate(firstPredicateKeywordList);
        NameMatchesKeywordsPredicate secondPredicate = new NameMatchesKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameMatchesKeywordsPredicate firstPredicateCopy = new NameMatchesKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different anime -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameMatchesKeywordsPredicate predicate =
                new NameMatchesKeywordsPredicate(Collections.singletonList("Attack"));
        assertTrue(predicate.test(new AnimeBuilder().withName("Attack Black").build()));

        // Multiple keywords
        predicate = new NameMatchesKeywordsPredicate(Arrays.asList("Attack", "Black"));
        assertTrue(predicate.test(new AnimeBuilder().withName("Attack Black").build()));

        // Only one matching keyword
        predicate = new NameMatchesKeywordsPredicate(Arrays.asList("Black", "Chainsaw"));
        assertTrue(predicate.test(new AnimeBuilder().withName("Attack Chainsaw").build()));

        // Mixed-case keywords
        predicate = new NameMatchesKeywordsPredicate(Arrays.asList("Attack", "Black"));
        assertTrue(predicate.test(new AnimeBuilder().withName("Attack Black").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameMatchesKeywordsPredicate predicate = new NameMatchesKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new AnimeBuilder().withName("Attack on Titan").build()));

        // Non-matching keyword
        predicate = new NameMatchesKeywordsPredicate(Arrays.asList("Chainsaw"));
        assertFalse(predicate.test(new AnimeBuilder().withName("Black Rock Shooter").build()));

        // Keywords match genres, but does not match name
        predicate = new NameMatchesKeywordsPredicate(Arrays.asList("action", "adventure"));
        assertFalse(predicate.test(new AnimeBuilder().withName("Attack on Titan")
                .withGenres("action", "adventure").build()));
    }
}
