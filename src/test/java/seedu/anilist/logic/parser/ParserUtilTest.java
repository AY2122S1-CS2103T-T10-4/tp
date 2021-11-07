package seedu.anilist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_ACTION_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_ACTION_NUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_ALPHA;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_NON_ALPHANUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_GENRE_NUMERIC;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_NAME_LONGER_THAN_MAX_LENGTH;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_EMPTY;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_NON_ASCII;
import static seedu.anilist.logic.commands.CommandTestUtil.INVALID_STRING_SPACE;
import static seedu.anilist.logic.commands.CommandTestUtil.NAME_DESC_AKIRA;
import static seedu.anilist.logic.commands.CommandTestUtil.STATUS_DESC_WATCHING;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_ACTION_ADD;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SCIENCE_FICTION_UPPER_CASE;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_AKIRA;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_ACTION;
import static seedu.anilist.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.anilist.logic.parser.ParserUtil.parseAction;
import static seedu.anilist.testutil.Assert.assertThrows;
import static seedu.anilist.testutil.TypicalIndexes.INDEX_FIRST_ANIME;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.anilist.logic.commands.Action;
import seedu.anilist.logic.parser.exceptions.IntegerOutOfRangeException;
import seedu.anilist.logic.parser.exceptions.ParseException;
import seedu.anilist.model.anime.Name;
import seedu.anilist.model.genre.Genre;

public class ParserUtilTest {

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseAction_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAction(null));
    }

    @Test
    public void parseAction_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAction(INVALID_STRING_EMPTY));
        assertThrows(ParseException.class, () -> ParserUtil.parseAction(INVALID_STRING_SPACE));
        assertThrows(ParseException.class, () -> ParserUtil.parseAction(INVALID_STRING_NON_ASCII));
        assertThrows(ParseException.class, () -> ParserUtil.parseAction(INVALID_ACTION_ALPHA));
        assertThrows(ParseException.class, () -> ParserUtil.parseAction(INVALID_ACTION_NUMERIC));
    }

    @Test
    public void parseAction_validInput_returnsAction() throws Exception {
        Action expectedAction = Action.ADD;
        assertEquals(expectedAction, parseAction(VALID_ACTION_ADD));
    }

    @Test
    public void parseAction_validInputWithSpace_returnsActionTrimmed() throws Exception {
        String actionWithWhitespace = WHITESPACE + VALID_ACTION_ADD + WHITESPACE;
        Action expectedAction = Action.ADD;
        assertEquals(expectedAction, parseAction(actionWithWhitespace));
    }

    @Test
    public void parseAction_validInputUpperCase_returnsLowerCaseAction() throws Exception {
        String upperCaseAction = VALID_ACTION_ADD.toUpperCase();
        Action expectedAction = Action.ADD;
        assertEquals(expectedAction, parseAction(upperCaseAction));
    }

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex(INVALID_STRING_EMPTY));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex(INVALID_STRING_SPACE));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex(INVALID_STRING_NON_ASCII));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("+10"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        String indexOutOfRangeErrorMsg = String.format(
                IntegerOutOfRangeException.OUT_OF_RANGE_MESSAGE, 1, Integer.MAX_VALUE
        );
        assertThrows(
            IntegerOutOfRangeException.class, indexOutOfRangeErrorMsg, () ->
                ParserUtil.parseIndex("-10")
        );

        assertThrows(
            IntegerOutOfRangeException.class, indexOutOfRangeErrorMsg, () ->
                ParserUtil.parseIndex(Long.toString((long) Integer.MAX_VALUE + 1))
        );
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_ANIME, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ANIME, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_STRING_EMPTY));
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_STRING_SPACE));
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_STRING_NON_ASCII));
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME_LONGER_THAN_MAX_LENGTH));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME_AKIRA);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME_AKIRA));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME_AKIRA + WHITESPACE;
        Name expectedName = new Name(VALID_NAME_AKIRA);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseGenre_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGenre(null));
    }

    @Test
    public void parseGenre_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGenre(INVALID_STRING_EMPTY));
        assertThrows(ParseException.class, () -> ParserUtil.parseGenre(INVALID_STRING_SPACE));
        assertThrows(ParseException.class, () -> ParserUtil.parseGenre(INVALID_STRING_NON_ASCII));
        assertThrows(ParseException.class, () -> ParserUtil.parseGenre(INVALID_GENRE_ALPHA));
        assertThrows(ParseException.class, () -> ParserUtil.parseGenre(INVALID_GENRE_NUMERIC));
        assertThrows(ParseException.class, () -> ParserUtil.parseGenre(INVALID_GENRE_NON_ALPHANUMERIC));
    }

    @Test
    public void parseGenre_validValueWithoutWhitespace_returnsGenre() throws Exception {
        Genre expectedGenre = new Genre(VALID_GENRE_ACTION);
        assertEquals(expectedGenre, ParserUtil.parseGenre(VALID_GENRE_ACTION));
    }

    @Test
    public void parseGenre_validValueWithWhitespace_returnsTrimmedGenre() throws Exception {
        String genreWithWhitespace = WHITESPACE + VALID_GENRE_ACTION + WHITESPACE;
        Genre expectedGenre = new Genre(VALID_GENRE_ACTION);
        assertEquals(expectedGenre, ParserUtil.parseGenre(genreWithWhitespace));
    }

    @Test void parseGenre_validValueWithUppercase_returnsLowerCaseGenre() throws Exception {
        String upperCaseGenre = VALID_GENRE_ACTION.toUpperCase();
        Genre expectedGenre = new Genre(VALID_GENRE_ACTION);
        assertEquals(expectedGenre, ParserUtil.parseGenre(upperCaseGenre));
    }

    @Test
    public void parseGenres_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGenres(null));
    }

    @Test
    public void parseGenres_collectionWithInvalidGenres_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGenres(
                Arrays.asList(VALID_GENRE_ACTION, INVALID_GENRE_ALPHA)));
    }

    @Test
    public void parseGenres_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseGenres(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseGenres_collectionWithValidGenres_returnsGenreSet() throws Exception {
        Set<Genre> actualGenreSet = ParserUtil.parseGenres(Arrays.asList(VALID_GENRE_ACTION,
                VALID_GENRE_SCIENCE_FICTION_UPPER_CASE));
        Set<Genre> expectedGenreSet = new HashSet<>(
                Arrays.asList(new Genre(VALID_GENRE_ACTION),
                        new Genre(VALID_GENRE_SCIENCE_FICTION_UPPER_CASE.toLowerCase()))
        );
        assertEquals(expectedGenreSet, actualGenreSet);
    }

    @Test
    public void tokenizeWithCheck_invalidArg_throwsParseException() throws Exception {
        // args contains preamble, preamble not required, no required prefixes, no optional prefixes
        assertThrows(ParseException.class, () -> ParserUtil.tokenizeWithCheck("asdf",
                false, new Prefix[] {}));

        // args contains prefixes, preamble not required, no required prefixes, no optional prefixes
        assertThrows(ParseException.class, () -> ParserUtil.tokenizeWithCheck(STATUS_DESC_WATCHING,
                false, new Prefix[] {}));

        // args contains no preamble, preamble required, no required prefixes, no optional prefixes
        assertThrows(ParseException.class, () -> ParserUtil.tokenizeWithCheck("",
                true, new Prefix[] {}));

        // args does not contains required prefix, preamble not required, one required prefix, no optional prefix
        assertThrows(ParseException.class, () -> ParserUtil.tokenizeWithCheck(NAME_DESC_AKIRA,
                true, new Prefix[] {PREFIX_STATUS}));

        // args does not contain valid prefixes, preamble not required, no required prefix, two optional prefixes
        assertThrows(ParseException.class, () -> ParserUtil.tokenizeWithCheck(NAME_DESC_AKIRA,
                true, new Prefix[] {},
                PREFIX_STATUS, PREFIX_ACTION));

        // args contains extra and required prefixes, preamble not required, one required prefix, no optional prefix
        assertThrows(ParseException.class, () -> ParserUtil.tokenizeWithCheck(NAME_DESC_AKIRA
                + STATUS_DESC_WATCHING, true, new Prefix[] {PREFIX_STATUS}));

        // args contains required prefixes and hasPreamble, preamble required, one required prefix, no optional prefix
        assertEquals(ParserUtil.tokenizeWithCheck("asdf" + STATUS_DESC_WATCHING,
                true, new Prefix[] {PREFIX_STATUS}),
                ArgumentTokenizer.tokenize("asdf" + STATUS_DESC_WATCHING, CliSyntax.getAllPrefixes()));

        // args contains optional prefixes, preamble not required, no required prefix, one optional prefix
        assertEquals(ParserUtil.tokenizeWithCheck(STATUS_DESC_WATCHING,
                false, new Prefix[] {}, PREFIX_STATUS),
                ArgumentTokenizer.tokenize(STATUS_DESC_WATCHING, CliSyntax.getAllPrefixes()));
    }
}
