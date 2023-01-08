package story;

import exceptions.StorytellerFellAsleepException;
import things.Thing;

public class Storyteller {
    public Storyteller() {
        super();
    }

    public String notSearchable(Thing[] things) {

        if (Math.random()<0.1) {
            throw new StorytellerFellAsleepException();
        }

        StringBuilder str = new StringBuilder().append("\nНо");
        for (var t : things) {
            str.append(", ни ").append(t.getParentcaseName());
        }
        str.append(" нигде не было видно, да, пожалуй, искать их было бесполезно:");
        return str.toString();
    }
}
