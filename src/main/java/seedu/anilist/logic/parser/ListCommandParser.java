package seedu.anilist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.anilist.model.Model.PREDICATE_SHOW_ALL_ANIME;

import seedu.anilist.logic.commands.ListCommand;
import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.anime.Status;
import seedu.anilist.model.anime.StatusEqualsPredicate;


public class ListCommandParser implements Parser<ListCommand> {
    @Override
    public ListCommand parse(String args) throws ParseException {
        if (args.trim().length() == 0) {
            return new ListCommand(PREDICATE_SHOW_ALL_ANIME);
        }
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STATUS);
        Status statusToMatch;

        if (argMultimap.getValue(PREFIX_STATUS).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ListCommand.MESSAGE_USAGE));
        }

        statusToMatch = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());

        return new ListCommand(new StatusEqualsPredicate(statusToMatch));
    }
}
