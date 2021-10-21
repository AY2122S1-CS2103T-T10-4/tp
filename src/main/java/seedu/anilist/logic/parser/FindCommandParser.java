package seedu.anilist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.List;

import seedu.anilist.logic.commands.FindCommand;
import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.anime.GenresMatchKeywordsPredicate;
import seedu.anilist.model.anime.NameMatchesKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENRE);
        List<String> nameKeywords = List.of(argMultimap.getValue(PREFIX_NAME).get().split(" "));
        List<String> genreKeywords = argMultimap.getAllValues(PREFIX_GENRE);
        System.out.println(nameKeywords.toString());

        boolean isInvalidFormat = (nameKeywords.size() == 0 && genreKeywords.size() == 0);
        if (isInvalidFormat) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        } else if (nameKeywords.size() == 0) {
            return new FindCommand(new GenresMatchKeywordsPredicate(genreKeywords));
        } else if (genreKeywords.size() == 0) {
            return new FindCommand(new NameMatchesKeywordsPredicate(nameKeywords));
        } else {
            return new FindCommand(new NameMatchesKeywordsPredicate(nameKeywords),
                    new GenresMatchKeywordsPredicate(genreKeywords));
        }
    }

}
