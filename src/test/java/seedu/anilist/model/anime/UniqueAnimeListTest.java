package seedu.anilist.model.anime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.anilist.testutil.Assert.assertThrows;
import static seedu.anilist.testutil.TypicalAnime.ALICE;
import static seedu.anilist.testutil.TypicalAnime.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.anilist.model.anime.exceptions.AnimeNotFoundException;
import seedu.anilist.model.anime.exceptions.DuplicateAnimeException;
import seedu.anilist.testutil.AnimeBuilder;

public class UniqueAnimeListTest {

    private final UniqueAnimeList uniqueAnimeList = new UniqueAnimeList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnimeList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueAnimeList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueAnimeList.add(ALICE);
        assertTrue(uniqueAnimeList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAnimeList.add(ALICE);
        Anime editedAlice = new AnimeBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueAnimeList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnimeList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueAnimeList.add(ALICE);
        assertThrows(DuplicateAnimeException.class, () -> uniqueAnimeList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnimeList.setAnime(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnimeList.setAnime(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(AnimeNotFoundException.class, () -> uniqueAnimeList.setAnime(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueAnimeList.add(ALICE);
        uniqueAnimeList.setAnime(ALICE, ALICE);
        UniqueAnimeList expectedUniqueAnimeList = new UniqueAnimeList();
        expectedUniqueAnimeList.add(ALICE);
        assertEquals(expectedUniqueAnimeList, uniqueAnimeList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueAnimeList.add(ALICE);
        Anime editedAlice = new AnimeBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueAnimeList.setAnime(ALICE, editedAlice);
        UniqueAnimeList expectedUniqueAnimeList = new UniqueAnimeList();
        expectedUniqueAnimeList.add(editedAlice);
        assertEquals(expectedUniqueAnimeList, uniqueAnimeList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueAnimeList.add(ALICE);
        uniqueAnimeList.setAnime(ALICE, BOB);
        UniqueAnimeList expectedUniqueAnimeList = new UniqueAnimeList();
        expectedUniqueAnimeList.add(BOB);
        assertEquals(expectedUniqueAnimeList, uniqueAnimeList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueAnimeList.add(ALICE);
        uniqueAnimeList.add(BOB);
        assertThrows(DuplicateAnimeException.class, () -> uniqueAnimeList.setAnime(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnimeList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(AnimeNotFoundException.class, () -> uniqueAnimeList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueAnimeList.add(ALICE);
        uniqueAnimeList.remove(ALICE);
        UniqueAnimeList expectedUniqueAnimeList = new UniqueAnimeList();
        assertEquals(expectedUniqueAnimeList, uniqueAnimeList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnimeList.setMultipleAnime((UniqueAnimeList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueAnimeList.add(ALICE);
        UniqueAnimeList expectedUniqueAnimeList = new UniqueAnimeList();
        expectedUniqueAnimeList.add(BOB);
        uniqueAnimeList.setMultipleAnime(expectedUniqueAnimeList);
        assertEquals(expectedUniqueAnimeList, uniqueAnimeList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAnimeList.setMultipleAnime((List<Anime>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueAnimeList.add(ALICE);
        List<Anime> animeList = Collections.singletonList(BOB);
        uniqueAnimeList.setMultipleAnime(animeList);
        UniqueAnimeList expectedUniqueAnimeList = new UniqueAnimeList();
        expectedUniqueAnimeList.add(BOB);
        assertEquals(expectedUniqueAnimeList, uniqueAnimeList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Anime> listWithDuplicateAnimes = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateAnimeException.class, () -> uniqueAnimeList.setMultipleAnime(listWithDuplicateAnimes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueAnimeList.asUnmodifiableObservableList().remove(0));
    }
}
