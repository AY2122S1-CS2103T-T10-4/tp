package seedu.anilist.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.anilist.model.AnimeList;
import seedu.anilist.model.ReadOnlyAnimeList;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.Episode;
import seedu.anilist.model.anime.Name;
import seedu.anilist.model.anime.Status;
import seedu.anilist.model.genre.Genre;

/**
 * Contains utility methods for populating {@code AnimeList} with sample data.
 */
public class SampleDataUtil {
    public static Anime[] getSampleAnime() {
        return new Anime[] {
            new Anime(new Name("Asobi Asobase"),
                    new Episode("0"),
                    new Status("w"),
                    getGenreSet("comedy")),
            new Anime(new Name("BLEACH"),
                    new Episode("0"),
                    new Status("w"),
                    getGenreSet("adventure", "supernatural")),
            new Anime(new Name("Charlotte"),
                    new Episode("0"),
                    new Status("w"),
                    getGenreSet("drama", "supernatural")),
            new Anime(new Name("Darling in the Franxx"),
                    new Episode("0"),
                    new Status("w"),
                    getGenreSet("action", "sci fi")),
            new Anime(new Name("Haikyuu!!: To the Top"),
                    new Episode("0"),
                    new Status("w"),
                    getGenreSet("sports")),
            new Anime(new Name("Steins;Gate 0"),
                    new Episode("0"),
                    new Status("w"),
                    getGenreSet("sci fi")),
            new Anime(new Name("Watashi ga Motenai no wa Dou Kangaetemo Omaera ga Warui!"),
                    new Episode("0"),
                    new Status("w"),
                    getGenreSet("slice of life")),
            new Anime(new Name("3-gatsu no Lion 2nd Season"),
                    new Episode("22"),
                    new Status("f"),
                    getGenreSet("drama", "slice of life")),
            new Anime(new Name("Fullmetal Alchemist: Brotherhood"),
                    new Episode("64"),
                    new Status("f"),
                    getGenreSet("action", "adventure", "comedy", "drama", "fantasy")),
            new Anime(new Name("Blue Period"),
                    new Episode("0"),
                    new Status("t"),
                    getGenreSet("drama", "slice of life")),
            new Anime(new Name("Komi-san wa, Comyushou desu."),
                    new Episode("0"),
                    new Status("t"),
                    getGenreSet("comedy", "slice of life"))
        };
    }

    public static ReadOnlyAnimeList getSampleAnimeList() {
        AnimeList sampleAb = new AnimeList();
        for (Anime sampleAnime : getSampleAnime()) {
            sampleAb.addAnime(sampleAnime);
        }
        return sampleAb;
    }

    /**
     * Returns a genre set containing the list of strings given.
     */
    public static Set<Genre> getGenreSet(String... strings) {
        return Arrays.stream(strings)
                .map(String::toLowerCase)
                .map(Genre::new)
                .collect(Collectors.toSet());
    }

}
