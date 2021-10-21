package seedu.anilist.model.anime;

import java.util.List;
import java.util.function.Predicate;

import seedu.anilist.commons.util.StringUtil;

/**
 * Tests that a {@code Anime}'s {@code Name} matches all of the keywords given.
 */
public class NameMatchesKeywordsPredicate implements Predicate<Anime> {
    private final List<String> keywords;

    public NameMatchesKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Anime anime) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(anime.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameMatchesKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameMatchesKeywordsPredicate) other).keywords)); // state check
    }

}
