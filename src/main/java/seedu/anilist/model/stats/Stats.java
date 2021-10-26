package seedu.anilist.model.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import seedu.anilist.model.genre.Genre;

public class Stats {
    private static final int genreCountLimit = 6;

    //stats to be displayed
    private final int watchingCount;
    private final int toWatchCount;
    private final int finishedCount;

    private final long episodesCount;

    private final int numUniqueGenres;

    private final HashMap<Genre, Integer> topGenres;

    /**
     * Creates a Stats object containing the user statistics.
     */
    public Stats(int watchingCount, int toWatchCount, int finishedCount, long episodesCount,
                 HashMap<Genre, Integer> genres) {
        this.watchingCount = watchingCount;
        this.toWatchCount = toWatchCount;
        this.finishedCount = finishedCount;
        this.episodesCount = episodesCount;
        this.topGenres = extractTopGenres(genres);
        this.numUniqueGenres = genres.size();
    }

    private HashMap<Genre, Integer> extractTopGenres(HashMap<Genre, Integer> genres) {
        Comparator<Entry<Genre, Integer>> genreCountComparator = new Comparator<>() {
            @Override
            public int compare(Entry<Genre, Integer> e1, Entry<Genre, Integer> e2) {
                int count1 = e1.getValue();
                int count2 = e2.getValue();
                return count2 - count1;
            }
        };

        //sorts genres input by value i.e. count
        List<Entry<Genre, Integer>> listOfEntries = new ArrayList<>(genres.entrySet());
        Collections.sort(listOfEntries, genreCountComparator);

        //number of items to be included in the stats display is the lower
        //of the genreCountLimit and the number of unique genres tagged to animes
        int numItems = Math.min(genreCountLimit, genres.size());
        LinkedHashMap<Genre, Integer> topGenresSorted = new LinkedHashMap<>(numItems);

        for (int i = numItems - 1; i >= 0; i--) {
            topGenresSorted.put(listOfEntries.get(i).getKey(), listOfEntries.get(i).getValue());
        }
        return topGenresSorted;
    }

    public int getTotalAnimesCount() {
        return this.watchingCount + this.toWatchCount + this.finishedCount;
    }

    public int getWatchingCount() {
        return this.watchingCount;
    }

    public int getToWatchCount() {
        return this.toWatchCount;
    }

    public int getFinishedCount() {
        return this.finishedCount;
    }

    public long getEpisodesCount() {
        return this.episodesCount;
    }

    public int getNumUniqueGenres() {
        return this.numUniqueGenres;
    }

    public HashMap<Genre, Integer> getTopGenres() {
        return this.topGenres;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this // short circuit if same object
                || (other instanceof Stats)) {
            return true;
        } // instanceof handles nulls

        Stats otherStats = (Stats) other;
        return this.watchingCount == otherStats.watchingCount
                && this.toWatchCount == otherStats.toWatchCount
                && this.finishedCount == otherStats.finishedCount
                && this.episodesCount == otherStats.episodesCount
                && this.numUniqueGenres == otherStats.numUniqueGenres
                && this.topGenres.equals(otherStats.topGenres);
    }
}
