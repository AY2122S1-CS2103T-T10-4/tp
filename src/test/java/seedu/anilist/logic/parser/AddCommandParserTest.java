package seedu.anilist.logic.parser;

import static seedu.anilist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.anilist.logic.commands.CommandTestUtil.NAME_DESC_AKIRA;
import static seedu.anilist.logic.commands.CommandTestUtil.NAME_DESC_BNHA;
import static seedu.anilist.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.anilist.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.anilist.logic.commands.CommandTestUtil.TAG_DESC_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.TAG_DESC_SUPERHERO;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_BNHA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_TAG_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_TAG_SUPERHERO;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.anilist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.anilist.testutil.TypicalAnime.AKIRA;
import static seedu.anilist.testutil.TypicalAnime.BNHA;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.commands.AddCommand;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.Name;
import seedu.anilist.model.tag.Tag;
import seedu.anilist.testutil.AnimeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Anime expectedAnime = new AnimeBuilder(BNHA).withTags(VALID_TAG_SUPERHERO).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BNHA
                + TAG_DESC_SUPERHERO, new AddCommand(expectedAnime));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AKIRA + NAME_DESC_BNHA
                + TAG_DESC_SUPERHERO, new AddCommand(expectedAnime));


        // multiple tags - all accepted
        Anime expectedAnimeMultipleTags = new AnimeBuilder(BNHA).withTags(VALID_TAG_SUPERHERO, VALID_TAG_ACTION)
                .build();
        assertParseSuccess(parser, NAME_DESC_BNHA
                + TAG_DESC_ACTION + TAG_DESC_SUPERHERO, new AddCommand(expectedAnimeMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Anime expectedAnime = new AnimeBuilder(AKIRA).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AKIRA,
                new AddCommand(expectedAnime));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BNHA,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BNHA,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC
                + TAG_DESC_ACTION + TAG_DESC_SUPERHERO, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BNHA
                + INVALID_TAG_DESC + VALID_TAG_SUPERHERO, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BNHA
                + TAG_DESC_ACTION + TAG_DESC_SUPERHERO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
