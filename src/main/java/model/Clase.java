
package model;

public class Clase {
    private String classId;
    private String className;

    public Clase(String classId, String class_name) {
        this.classId = classId;
        this.className = class_name;
    }

    public String getClassId() {
        return this.classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
