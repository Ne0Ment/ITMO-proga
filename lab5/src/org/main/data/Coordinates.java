package org.main.data;

/**
 * Class for handling coordinate info.
 * @author neoment
 * @version 0.1
 */
public class Coordinates{
    private Integer x; //Значение поля должно быть больше -720, Поле не может быть null
    private int y;

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
