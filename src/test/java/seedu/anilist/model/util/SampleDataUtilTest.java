package seedu.anilist.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SCIENCE_FICTION_UPPER_CASE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.genre.Genre;

public class SampleDataUtilTest {

    @Test
    public void getSampleData_getsSampleData() {
        Anime[] sampleAnime = SampleDataUtil.getSampleAnime();

        assertTrue(sampleAnime.length > 0);

        boolean noneNull = Arrays.stream(sampleAnime).allMatch(anime -> anime != null);
        assertTrue(noneNull);
    }

    @Test
    public void getSampleAnimeList_nonNull_getsNonNullSampleAnimeList() {
        assertNotNull(SampleDataUtil.getSampleAnimeList());
    }

    @Test
    public void getGenreSet_getsGenreSet() {
        String genreString1 = VALID_GENRE_SCIENCE_FICTION_UPPER_CASE;
        String genreString2 = VALID_GENRE_ACTION;
        Set<Genre> expectedGenreSet = new HashSet<>() {{
                add(new Genre(genreString1.toLowerCase()));
                add(new Genre(genreString2));
            }};
        assertEquals(expectedGenreSet, SampleDataUtil.getGenreSet(genreString1, genreString2));
    }
}
