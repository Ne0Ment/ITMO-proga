package things;

import enums.time;
import places.Place;

import java.util.Objects;

public class Everything extends Thing implements IChangeable {
    public Everything(String name, String parentCaseName){
        super(name, parentCaseName);

    }

    public String changedAfter(Place where, time changeTime, String afterAction){
        return " " + this.getName() + " " + where.getName() + " совершенно переменилось " + changeTime.getDescription() + ", как " + afterAction + "\n";
    }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null) return false;
        if (!this.getClass().equals(otherObj.getClass())) return false;
        Everything other = (Everything) otherObj;
        return Objects.equals(this.getName(), other.getName()) & Objects.equals(this.getParentcaseName(), other.getParentcaseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName() + this.getParentcaseName());
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name=" + this.getName() + ",parentCaseName=" + this.getParentcaseName() + "]";
    }
}