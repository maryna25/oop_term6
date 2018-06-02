package dao.contract;

import model.Course;

import java.util.List;

public interface CourseDAO {
    public List<Course> getCourses();
    public Course getCourse(int courseId);
    public List<Course> getCoursesByTeacherId(int teacherId);
    public void createCourse(Course course);
    public Course getCourseByName(String name);
}
