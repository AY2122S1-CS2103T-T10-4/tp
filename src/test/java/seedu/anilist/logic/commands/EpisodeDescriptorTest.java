package seedu.anilist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_EPISODE_ONE;
import static seedu.anilist.logic.commands.CommandTestUtil.DESC_EPISODE_ZERO;
import static seedu.anilist.logic.commands.CommandTestUtil.VALID_EPISODE_TWO_WITH_ZEROS_PADDED;

import org.junit.jupiter.api.Test;

import seedu.anilist.testutil.EpisodeDescriptorBuilder;

public class EpisodeDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        UpdateEpisodeCommand.EpisodeDescriptor descriptorWithSameValues =
            new UpdateEpisodeCommand.EpisodeDescriptor(DESC_EPISODE_ONE);
        assertTrue(DESC_EPISODE_ONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EPISODE_ONE.equals(DESC_EPISODE_ONE));

        // null -> returns false
        assertFalse(DESC_EPISODE_ONE.equals(null));

        // different types -> returns false
        assertFalse(DESC_EPISODE_ONE.equals(5));

        // different values -> returns false
        assertFalse(DESC_EPISODE_ONE.equals(DESC_EPISODE_ZERO));

        // different episode -> returns false
        UpdateEpisodeCommand.EpisodeDescriptor editedEpisodeDesc =
            new EpisodeDescriptorBuilder(DESC_EPISODE_ONE).withEpisode(VALID_EPISODE_TWO_WITH_ZEROS_PADDED).build();
        assertFalse(DESC_EPISODE_ONE.equals(editedEpisodeDesc));
    }
}
