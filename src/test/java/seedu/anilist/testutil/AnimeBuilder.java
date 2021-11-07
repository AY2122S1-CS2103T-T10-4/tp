package seedu.anilist.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.anilist.model.anime.Anime;
import seedu.anilist.model.anime.Episode;
import seedu.anilist.model.anime.Name;
import seedu.anilist.model.anime.Status;
import seedu.anilist.model.genre.Genre;
import seedu.anilist.model.util.SampleDataUtil;

/**
 * A utility class to help with building Anime objects.
 */
public class AnimeBuilder {

    public static final String DEFAULT_NAME = "Attack on Titan";
    public static final String DEFAULT_EPISODE = Episode.DEFAULT_EPISODE;
    public static final String DEFAULT_STATUS = Status.DEFAULT_STATUS;

    private Name name;
    private Episode episode;
    private Set<Genre> genres;
    private Status status;

    /**
     * Creates a {@code AnimeBuilder} with the default details.
     */
    public AnimeBuilder() {
        name = new Name(DEFAULT_NAME);
        episode = new Episode(DEFAULT_EPISODE);
        status = new Status(DEFAULT_STATUS);
        genres = new HashSet<>();
    }

    /**
     * Initializes the AnimeBuilder with the data of {@code animeToCopy}.
     */
    public AnimeBuilder(Anime animeToCopy) {
        name = animeToCopy.getName();
        episode = animeToCopy.getEpisode();
        status = animeToCopy.getStatus();
        genres = new HashSet<>(animeToCopy.getGenres());
    }

    /**
     * Sets the {@code Name} of the {@code Anime} that we are building.
     */
    public AnimeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Anime} that we are building.
     */
    public AnimeBuilder withEpisode(String episode) {
        this.episode = new Episode(episode);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Anime} that we are building.
     */
    public AnimeBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Genres} of the {@code Anime} that we are building.
     */
    public AnimeBuilder withGenres(String ... genres) {
        this.genres = SampleDataUtil.getGenreSet(genres);
        return this;
    }

    public Anime build() {
        return new Anime(name, episode, status, genres);
    }

}
