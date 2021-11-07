package seedu.anilist.testutil;

import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_GENRE_SCIENCE_FICTION;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_AKIRA;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_NAME_BNHA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import seedu.anilist.model.AnimeList;
import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.genre.Genre;
import seedu.anilist.model.stats.Stats;

/**
 * A utility class containing a list of {@code Anime} objects to be used in tests.
 */
public class TypicalAnimes {
    public static final String FIRST_ANIME_GENRE = "fantasy";
    public static final String SECOND_ANIME_GENRE = "action";

    public static final Anime AOT = new AnimeBuilder()
            .withName("Attack on Titan: The Final Season")
            .withEpisode("2")
            .withGenres(FIRST_ANIME_GENRE)
            .build();
    public static final Anime BRS = new AnimeBuilder()
            .withName("Black Rock Shooter")
            .withEpisode("12")
            .withStatus("F")
            .withGenres(FIRST_ANIME_GENRE, SECOND_ANIME_GENRE)
            .build();
    public static final Anime CSM = new AnimeBuilder()
            .withName("Chainsaw Man")
            .withStatus("T")
            .build();
    public static final Anime DBZ = new AnimeBuilder()
            .withName("Dragon Ball Z")
            .withStatus("T")
            .withGenres(SECOND_ANIME_GENRE)
            .build();
    public static final Anime ELF = new AnimeBuilder()
            .withName("Elfen Lied")
            .withEpisode("21")
            .build();
    public static final Anime FSN = new AnimeBuilder()
            .withName("Fate/stay night")
            .withEpisode("24")
            .withStatus("F")
            .build();
    public static final Anime GS = new AnimeBuilder()
            .withName("Goblin Slayer: Goblin's Crown")
            .withStatus("T")
            .build();

    // Manually added
    public static final Anime HXH = new AnimeBuilder().withName("Hunter x Hunter (2011)").build();
    public static final Anime IDOL = new AnimeBuilder().withName("iDOLM@STER").build();

    // Manually added - Anime's details found in {@code CommandTestUtil}
    public static final Anime AKIRA = new AnimeBuilder().withName(VALID_NAME_AKIRA)
            .withGenres(VALID_GENRE_SCIENCE_FICTION).build();
    public static final Anime BNHA = new AnimeBuilder().withName(VALID_NAME_BNHA)
            .withGenres(VALID_GENRE_ACTION, VALID_GENRE_SCIENCE_FICTION).build();

    public static final String KEYWORD_MATCHING_HUNTER = "Hunter"; // A keyword that matches HUNTER

    private static int watchingCount = 2;
    private static int toWatchCount = 3;
    private static int finishedCount = 2;
    private static HashMap<Genre, Integer> userGenres = new HashMap<>() {{
            put(new Genre(FIRST_ANIME_GENRE), 2);
            put(new Genre(SECOND_ANIME_GENRE), 2);
        }};

    private TypicalAnimes() {
        // prevents instantiation
    }

    /**
     * Returns an {@code AnimeList} with all the typical anime.
     */
    public static AnimeList getTypicalAnimeList() {
        AnimeList ab = new AnimeList();
        for (Anime anime : getTypicalAnime()) {
            ab.addAnime(anime);
        }
        return ab;
    }

    public static List<Anime> getTypicalAnime() {
        return new ArrayList<>(Arrays.asList(AOT, BRS, CSM, DBZ, ELF, FSN, GS));
    }

    public static Stats getTypicalAnimeStats() {
        return new Stats(watchingCount, toWatchCount, finishedCount, userGenres);
    }
}
