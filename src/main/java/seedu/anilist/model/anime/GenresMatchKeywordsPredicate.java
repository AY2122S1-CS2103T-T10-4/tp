package seedu.anilist.model.anime;

import seedu.anilist.model.genre.Genre;
import seedu.anilist.model.util.SampleDataUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Anime}'s {@code Genre} matches any of the keywords given.
 */
public class GenresMatchKeywordsPredicate implements Predicate<Anime> {
    private final List<String> keywords;

    public GenresMatchKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Anime anime) {
        return anime.getGenres().containsAll(SampleDataUtil.getGenreSet(keywords.toArray(new String[0])));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenresMatchKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((GenresMatchKeywordsPredicate) other).keywords)); // state check
    }

}
