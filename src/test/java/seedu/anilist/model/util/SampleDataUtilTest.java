package seedu.anilist.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String genreString1 = "fantasy";
        String genreString2 = "action";
        Set<Genre> expectedGenreSet = new HashSet<>() {{
                add(new Genre(genreString1));
                add(new Genre(genreString2));
            }};
        assertEquals(expectedGenreSet, SampleDataUtil.getGenreSet(genreString2, genreString1));
    }
}
