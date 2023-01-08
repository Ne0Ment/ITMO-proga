package things;

import enums.time;
import places.Place;

public interface IChangeable {
    public String changedAfter(Place where, time changeTime, String afterAction);
}
