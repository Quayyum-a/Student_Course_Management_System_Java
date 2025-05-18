package data.model;

public class Course {
    private int id;
    private String title;
    private int instructorId;

    public Course(int id, String title, int instructorId) {
        this.id = id;
        this.title = title;
        this.instructorId = instructorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
}
