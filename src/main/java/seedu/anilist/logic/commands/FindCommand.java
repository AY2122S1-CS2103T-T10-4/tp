package seedu.anilist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.anilist.commons.core.Messages;
import seedu.anilist.model.Model;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.GenresMatchKeywordsPredicate;
import seedu.anilist.model.anime.NameMatchesKeywordsPredicate;

import java.util.function.Predicate;

/**
 * Finds and lists all animes in anime list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all animes whose names contain all of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n\n"
            + "Optional genre parameters can be specified, in which case only anime with those genre(s) "
            + "are shown.\n\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example 1: " + COMMAND_WORD + " baka senpai tomodachi\n"
            + "Example 2: " + COMMAND_WORD + " " + PREFIX_GENRE + "comedy\n"
            + "Example 3: " + COMMAND_WORD + " " + PREFIX_NAME + "baka" + " "
            + PREFIX_GENRE + "slice of life" + " " + PREFIX_GENRE + "comedy";

    private final Predicate<Anime> predicate;

    public FindCommand(NameMatchesKeywordsPredicate namePredicate) {
        this.predicate = namePredicate;
    }

    public FindCommand(GenresMatchKeywordsPredicate genrePredicate) {
        this.predicate = genrePredicate;
    }

    public FindCommand(NameMatchesKeywordsPredicate namePredicate,
                       GenresMatchKeywordsPredicate genrePredicate) {
        this.predicate = namePredicate.and(genrePredicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAnimeList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ANIME_LISTED_OVERVIEW, model.getFilteredAnimeList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
