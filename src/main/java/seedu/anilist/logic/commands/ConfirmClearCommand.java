package seedu.anilist.logic.commands;

import seedu.anilist.model.Model;
import seedu.anilist.model.anime.Anime;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ConfirmClearCommand extends Command {
    public static final String COMMAND_WORD = "y";
    public static final String MESSAGE_SUCCESS = "Anime(s) have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Anime> lastShownList = model.getFilteredAnimeList();
        while (!model.getFilteredAnimeList().isEmpty()) {
            model.deleteAnime(lastShownList.get(0));
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
