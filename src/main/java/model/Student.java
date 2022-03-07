
package model;

public class Student {
    private String studentName;
    private int studentId;
    private String classId;

    public Student(int studentId, String classId, String studentName) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.classId = classId;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentId() {
        return this.studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return this.classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
