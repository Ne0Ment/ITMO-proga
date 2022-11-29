package story;

import things.Thing;

public class Storyteller {
    public Storyteller() {
        super();
    }

    public String notSearchable(Thing[] things) {
        StringBuilder str = new StringBuilder().append("\nНо");
        for (var t : things) {
            str.append(", ни ").append(t.getParentcaseName());
        }
        str.append(" нигде не было видно, да, пожалуй, искать их было бесполезно:");
        return str.toString();
    }
}
