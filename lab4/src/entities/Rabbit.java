package entities;

import enums.time;
import exceptions.NothingToSearchException;
import things.Thing;

import java.util.Objects;
import java.util.stream.Stream;

public class Rabbit extends Person implements IExistable, INoticable{
    public class MissableItem {
        private String name;
        private Boolean multiple;

        public MissableItem(String name, Boolean multiple) {
            this.name = name;
            this.multiple = multiple;
        }

        public String miss() {
            if (!this.multiple) {
                return "пропала " + this.name;
            } else {
                return "пропали " + this.name;
            }
        }
    }

    public static class Voice{
        public static String mutter(String mutterText) {
            return "и продолжал бормотать себе под нос: - " + mutterText;
        }
        public static String shout(String name) {  return "и сердито окликнул " + name; }

        public static String dialogSay(String text) { return " - " + text; }

        public static String encourage() { return "А ну пошевеливайся!"; }
    }

    interface IOrderable {
        public String orderToExecute();
    }
    IOrderable queen = new IOrderable() {
        public String orderToExecute() {
            return " Велит она меня казнить!\n";
        }
    };

    private String fullName;
    private MissableItem[] items;

    public Rabbit(String name, String fullName) {
        super();
        this.setName(name);
        this.setFullName(fullName);
        this.items = new MissableItem[]{
                new MissableItem("моя головушка", false),
                new MissableItem("моя шкурка", false),
                new MissableItem("мои усики", true)
        };
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String newFullName) {
        this.fullName = newFullName;
    }

    public String search(Thing[] items, time searchTime) throws NothingToSearchException {
        if (items.length==0) {
            throw new NothingToSearchException();
        }
        StringBuilder searchString = new StringBuilder()
                .append(this.getName() + " ищет ")
                .append(String.join(" и ", Stream.of(items).map(e -> e.getName()).toArray(String[]::new)));
        return searchString.toString();
    }

    public String existAgain() {
        return "Нет, это опять был " + this.getFullName() + ". ";
    }

    public String trotWithMutter() {
        return this.getName() + " неторопливо трусил обратно, озабоченно озираясь, словно что-то потерял, \n" + Voice.mutter(this.MissItems()) + queen.orderToExecute();
    }

    public String MissItems() {
        StringBuilder missString = new StringBuilder()
                .append(String.join(", и ", Stream.of(this.items).map(e -> e.miss()).toArray(String[]::new)));
        missString.append("!");
        return missString.toString();
    }

    public String noticeAndSay(String noticeName, time noticeTime) {
        return "\nКролик " + noticeTime.getDescription() + " заметил " + noticeName + ", " + Voice.shout("ее") + ".";
    }

    public String askTask(String name) {
        return Voice.dialogSay("Эй, " + name + "! Ты что тут околачиваешься?");
    }

    public String giveCommand(Thing[] items) {
        class CommandGenerator {
            public String run(time runTime, String runLocation) {
                return runTime.getDescription() + " беги " + runLocation;
            }

            public String bring(Thing[] items) {
                return "принеси мне " + String.join(" и ", Stream.of(items).map(e -> e.getName()).toArray(String[]::new));
            }
        }
        CommandGenerator gen = new CommandGenerator();

        return Voice.dialogSay(gen.run(time.THISINSTANT, "домой") + " и " + gen.bring(items)) + " " + Voice.encourage();
    }


    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null) return false;
        if (!this.getClass().equals(otherObj.getClass())) return false;
        Rabbit other = (Rabbit) otherObj;
        return Objects.equals(this.getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name=" + this.getName() + "]";
    }
}