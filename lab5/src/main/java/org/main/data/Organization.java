package org.main.data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class for handling Organization info.
 * @author neoment
 * @version 0.1
 */
public class Organization{
    private String fullName; //Поле не может быть null
    private OrganizationType type; //Поле не может быть null

    private void checkNull(Object v) {
        if (v==null) throw new IllegalArgumentException("Value can't be null.");
    }

    public void setFullName(String fullName) {
        checkNull(fullName);
        this.fullName = fullName;
    }

    public void setType(OrganizationType type) {
        checkNull(type);
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public OrganizationType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(this.fullName, that.fullName) && this.type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.fullName, this.type);
    }

    @Override
    public String toString() {
        return "Наименование организации: " + this.getFullName() + ", " +
                "Тип организации: " + this.getType().toString();
    }
}
