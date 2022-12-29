package things;

import entities.Person;

import java.util.Objects;

public class Thing {
    private String name;
    private String parentcaseName;

    public Thing(String name, String parentcaseName) {
        this.setName(name);
        this.setParentcaseName(parentcaseName);
    };

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setParentcaseName(String name){
        this.parentcaseName = name;
    }

    public String getParentcaseName(){
        return this.parentcaseName;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) return true;
        if (otherObj == null) return false;
        if (!this.getClass().equals(otherObj.getClass())) return false;
        Thing other = (Thing) otherObj;
        return Objects.equals(this.name, other.name) & Objects.equals(this.parentcaseName, other.parentcaseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name + this.parentcaseName);
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name=" + this.name + ",parentCaseName=" + this.parentcaseName + "]";
    }
}
