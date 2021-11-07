package seedu.anilist.model.anime;

import static seedu.anilist.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.anilist.model.genre.Genre;

/**
 * Represents an Anime in the anime list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Anime {

    // Identity fields
    private final Name name;

    // Data fields
    private final Episode episode;
    private final Status status;
    private final Set<Genre> genres = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Anime(Name name, Episode episode, Status status, Set<Genre> genres) {
        requireAllNonNull(name, genres, episode, status);
        this.name = name;
        this.episode = episode;
        this.status = status;
        this.genres.addAll(genres);
    }

    public Name getName() {
        return name;
    }

    public Episode getEpisode() {
        return episode;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns an immutable genre set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    /**
     * Returns true if both anime have the same name.
     * This defines a weaker notion of equality between two anime.
     */
    public boolean isSameAnime(Anime otherAnime) {
        if (otherAnime == this) {
            return true;
        }
        if (otherAnime == null) {
            return false;
        }
        Name otherAnimeName = otherAnime.getName();
        return otherAnimeName.equals(this.getName());
    }

    /**
     * Returns true if both anime have the same identity and data fields.
     * This defines a stronger notion of equality between two anime.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Anime)) {
            return false;
        }

        Anime otherAnime = (Anime) other;

        return hasSameName(otherAnime)
                && hasSameEpisode(otherAnime)
                && hasSameStatus(otherAnime)
                && hasSameGenres(otherAnime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, episode, status, genres);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(String.format(" (Episode: %s)", getEpisode()));
        Set<Genre> genres = getGenres();
        if (!genres.isEmpty()) {
            builder.append("; Genres: ");
            Object[] genresArray = genres.toArray();
            Arrays.sort(genresArray);
            String genresString = Arrays.toString(genresArray);
            genresString = genresString.substring(1, genresString.length() - 1);
            builder.append(genresString);
        }
        builder.append(String.format("; (Status: %s)", getStatus()));
        return builder.toString();
    }

    private boolean hasSameName(Anime other) {
        return this.name.equals(other.name);
    }

    private boolean hasSameEpisode(Anime other) {
        return this.episode.equals(other.episode);
    }

    private boolean hasSameStatus(Anime other) {
        return this.status.equals(other.status);
    }

    private boolean hasSameGenres(Anime other) {
        return this.genres.equals(other.genres);
    }

}
