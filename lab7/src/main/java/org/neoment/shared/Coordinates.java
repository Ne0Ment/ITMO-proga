package org.neoment.shared;

import java.io.Serializable;

/**
 * Class for handling coordinate info.
 * @author neoment
 * @version 0.1
 */
public class Coordinates implements Serializable {
    private Integer x; //Значение поля должно быть больше -720, Поле не может быть null
    private int y;

    public Coordinates(Integer x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public Coordinates() {};

    private void checkNull(Object v) {
        if (v==null) throw new IllegalArgumentException("Value can't be null.");
    }

    public void setX(Integer x) {
        checkNull(x);
        if (x <= -720) throw new IllegalArgumentException("X should be greater than -720");
        this.x = x;
    }

    public void setY(Integer y) {
        checkNull(y);
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
