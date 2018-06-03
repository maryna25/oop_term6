package dao.impl;

import dao.DBConnectionFactory;
import dao.contract.CourseDAO;
import dao.contract.TeacherDAO;
import log.AppLogger;
import model.Course;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    private TeacherDAO teacherDAO = new TeacherDAOImpl();

    public Course getCourse(int id) {
        String sql = String.format("SELECT * FROM lab0.courses WHERE id=%s", id);
        return getCourseFromDB(sql);
    }

    private void executeUpdate(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnectionFactory.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            AppLogger.getLogger().error("SQL execution failed", e);
        } finally {
            DBConnectionFactory.closeConnection(statement, connection);
        }
    }

    private ResultSet executeQuery(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnectionFactory.getInstance().getConnection();
            statement = connection.createStatement();
            return statement.executeQuery(sql);

        } catch (SQLException e) {
            AppLogger.getLogger().error("SQL execution failed", e);
        }
        return null;
    }

    public List<Course> getCoursesByTeacherId(int teacherId) {
        String sql = String.format("SELECT * FROM lab0.courses WHERE teacher_id=%d", teacherId);
        ResultSet rs = executeQuery(sql);
        return getCourses(rs);
    }

    private Course getCourseFromDB(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnectionFactory.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String program = rs.getString("program");
                return new Course(id, title, program);
            }

        } catch (SQLException e) {
            AppLogger.getLogger().error(e);
        } finally {
            DBConnectionFactory.closeConnection(statement, connection);
        }

        return null;
    }

    private List<Course> getCourses(ResultSet rs) {
        if (rs == null) return null;
        List<Course> courses = new ArrayList<Course>();
        try {
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setTitle(rs.getString("title"));
                course.setProgram(rs.getString("program"));
                // teacher
                int teacherId = rs.getInt("teacher_id");
                if (teacherId > 0) course.setTeacher(teacherDAO.getTeacher(teacherId));

                courses.add(course);
            }
        }
        catch (SQLException ex) {
            AppLogger.getLogger().error(ex);
        }

        return courses;
    }

    public void createCourse(Course course) {
        String sql = String.format("INSERT INTO lab0.courses(title, program, teacher_id) " +
                        "VALUES('%s', '%s', %d)", course.getTitle(), course.getProgram(), course.getTeacher().getId());
        AppLogger.getLogger().info(sql);
        executeUpdate(sql);
    }

    public List<Course> getCourses(){
        String sql = String.format("SELECT * FROM lab0.courses");
        ResultSet rs = executeQuery(sql);
        return getCourses(rs);
    }

    public Course getCourseByName(String name){
        String sql = String.format("SELECT * FROM lab0.courses WHERE title='%s'", name);
        ResultSet rs = executeQuery(sql);
        return getCourses(rs).get(0);
    }
}
