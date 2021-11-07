package seedu.anilist.logic.parser;

import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.logic.commands.CommandTestUtil.EPISODE_DESC_EPISODE_ONE;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STATUS_DESC_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_TOWATCH;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_WATCHING;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_STATUS_WATCHING_MIXED_CASE;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.anilist.model.Model.PREDICATE_SHOW_ALL_ANIME;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.commands.ListCommand;
import seedu.anilist.model.anime.Status;
import seedu.anilist.model.anime.StatusEqualsPredicate;

public class ListCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);
    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_wrongFieldSpecified_failure() {
        // wrong field specified
        assertParseFailure(parser, EPISODE_DESC_EPISODE_ONE, MESSAGE_INVALID_FORMAT);
        // extra random characters
        assertParseFailure(parser, "asdfg", MESSAGE_INVALID_FORMAT);
        // wrong field and correct field specified
        assertParseFailure(parser, EPISODE_DESC_EPISODE_ONE + STATUS_DESC_WATCHING, MESSAGE_INVALID_FORMAT);
    }

    @Test void parse_invalidValue_failure() {
        // invalid status
        assertParseFailure(parser, INVALID_STATUS_DESC_ALPHA, Status.MESSAGE_CONSTRAINTS);
        // extra fields specified after parameter
        assertParseFailure(parser, STATUS_DESC_WATCHING + EPISODE_DESC_EPISODE_ONE, MESSAGE_INVALID_FORMAT);
        // empty status
        assertParseFailure(parser, PREFIX_STATUS + "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noFieldSpecified_success() {
        ListCommand expectedCommand = new ListCommand(PREDICATE_SHOW_ALL_ANIME);
        // no status specified
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test void parse_validStatusSpecified_success() {
        StatusEqualsPredicate expectedPredicate = new StatusEqualsPredicate(new Status(
                VALID_STATUS_WATCHING_MIXED_CASE));
        ListCommand expectedCommand = new ListCommand(expectedPredicate);

        assertParseSuccess(parser, STATUS_DESC_WATCHING, expectedCommand);
        //multiple status specified, last status taken
        assertParseSuccess(parser, STATUS_DESC_TOWATCH + STATUS_DESC_WATCHING, expectedCommand);
    }
}
