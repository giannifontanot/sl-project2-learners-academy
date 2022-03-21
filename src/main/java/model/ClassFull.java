
package model;

public class ClassFull {
    private String classId;
    private String className;
    private int studentId;
    private String studentName;
    private int teacherId;
    private String teacherName;
    private String subjectId;
    private String subjectName;

    public ClassFull(String classId, String className, int studentId, String studentName, int teacherId, String teacherName, String subjectId, String subjectName) {
        this.classId = classId;
        this.className = className;
        this.studentId = studentId;
        this.studentName = studentName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
