package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_FINISHED_UPPER_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_TOWATCH;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING_MIXED_CASE;

import org.junit.jupiter.api.Test;

import seedu.anilist.testutil.AnimeBuilder;

public class StatusEqualsPredicateTest {
    @Test
    public void equals() {
        Status firstStatus = new Status(VALID_STATUS_TOWATCH);
        Status secondStatus = new Status(VALID_STATUS_FINISHED_UPPER_CASE);
        StatusEqualsPredicate firstPredicate = new StatusEqualsPredicate(firstStatus);
        StatusEqualsPredicate secondPredicate = new StatusEqualsPredicate(secondStatus);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StatusEqualsPredicate firstPredicateCopy = new StatusEqualsPredicate(firstStatus);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different status -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_statusMatches_returnsTrue() {
        Anime animeWatching = new AnimeBuilder().withStatus(VALID_STATUS_FINISHED_UPPER_CASE).build();
        StatusEqualsPredicate predicateWatching = new StatusEqualsPredicate(new Status(
                VALID_STATUS_FINISHED_UPPER_CASE));

        assertTrue(predicateWatching.test(animeWatching));
    }

    @Test
    public void test_statusDoNotMatch_returnsFalse() {
        Anime animeWatching = new AnimeBuilder().withStatus(VALID_STATUS_WATCHING_MIXED_CASE).build();
        StatusEqualsPredicate predicateToWatch = new StatusEqualsPredicate(new Status(VALID_STATUS_TOWATCH));

        assertFalse(predicateToWatch.test(animeWatching));
    }
}
