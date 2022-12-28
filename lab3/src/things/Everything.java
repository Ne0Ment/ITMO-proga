package things;

import enums.time;
import places.Place;

public class Everything extends Thing implements IChangeable {
    public Everything(String name, String parentCaseName){
        super(name, parentCaseName);

    }

    public String changedAfter(Place where, time changeTime, String afterAction){
        return " " + this.getName() + " " + where.getName() + " совершенно переменилось " + changeTime.getDescription() + ", как " + afterAction + "\n";
    }
}