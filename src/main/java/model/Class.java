
package model;

public class Class {
    private String classId;
    private String class_name;

    public Class(String classId, String class_name) {
        this.classId = classId;
        this.class_name = class_name;
    }

    public String getClassId() {
        return this.classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClass_name() {
        return this.class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
