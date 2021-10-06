package seedu.anilist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.anilist.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.anilist.commons.exceptions.IllegalValueException;
import seedu.anilist.commons.util.JsonUtil;
import seedu.anilist.model.AnimeList;
import seedu.anilist.testutil.TypicalAnime;

public class JsonSerializableAnimeListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAniList dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAniList.class).get();
        AnimeList animeListFromFile = dataFromFile.toModelType();
        AnimeList typicalPersonsAnimeList = TypicalAnime.getTypicalAnimeList();
        assertEquals(animeListFromFile, typicalPersonsAnimeList);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAniList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAniList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAniList.MESSAGE_DUPLICATE_ANIME,
                dataFromFile::toModelType);
    }

}
