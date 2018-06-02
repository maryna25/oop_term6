package model;

public class Assignment {

    public enum Status {
        IN_PROGRESS, COMPLETED
    }

    private int id;

    private Student student;
    private Course course;
    private int mark;
    private String response;
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Status getStatus() { return status; }

    public void setStatus(Status status) {
        this.status = status;
    }
}
