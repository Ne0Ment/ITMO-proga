package enums;

public enum time {
    DEFAULT(""),
    STRAIGHTAWAY("сразу"),
    IMMEDIATELY("немедленно"),
    AFTER("с тех пор");

    private String description;

    public String getDescription() {
        return this.description;
    }

    private time(String description){
        this.description = description;
    }
}
