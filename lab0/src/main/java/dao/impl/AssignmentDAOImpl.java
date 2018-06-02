package dao.impl;

import dao.DBConnectionFactory;
import dao.contract.StudentDAO;
import dao.contract.CourseDAO;
import log.AppLogger;
import model.Assignment;
import model.Course;
import model.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AssignmentDAOImpl implements dao.contract.AssignmentDAO {

    private StudentDAO studentDAO = new StudentDAOImpl();
    private CourseDAO courseDAO = new CourseDAOImpl();

    public AssignmentDAOImpl() {
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

    public List<Assignment> getAssgnmentByTeacher(int teacherId){
        List<Course> courses = new CourseDAOImpl().getCoursesByTeacherId(teacherId);
        List<Assignment> res = new LinkedList<>();
        for (int i = 0; i < courses.size(); i++) {
            List<Assignment> r = getAssignmentsByCourse(courses.get(i).getId());
            if (r != null)
                for (int j = 0; j < r.size(); j++)
                    res.add(r.get(j));
        }
        return res;
    }


    public List<Assignment> getAssignmentsByStudent(int studentId) {
        String sql = String.format("SELECT * FROM lab0.assignments WHERE student_id=%s ORDER BY id DESC", studentId);
        ResultSet rs = executeQuery(sql);
        return getAssignments(rs);
    }

    public List<Assignment> getAssignmentsByCourse(int courseId) {
        String sql = String.format("SELECT * FROM lab0.assignments WHERE cource_id=%s ORDER BY id DESC", courseId);
        ResultSet rs = executeQuery(sql);
        return getAssignments(rs);
    }

    private List<Assignment> getAssignments(ResultSet rs) {
        if (rs == null) return null;
        List<Assignment> assignments = new ArrayList<Assignment>();
        try {
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setId(rs.getInt("id"));
                assignment.setMark(rs.getInt("mark"));
                assignment.setResponse(rs.getString("response"));
                // Status
                int st = rs.getInt("status");
                Assignment.Status status;
                switch (st) {
                    case 2: status = Assignment.Status.COMPLETED; break;
                    default: status = Assignment.Status.IN_PROGRESS; break;
                }
                assignment.setStatus(status);
                // Student
                int studentId = rs.getInt("student_id");
                if (studentId > 0) assignment.setStudent(studentDAO.getStudent(studentId));

                // Course
                int courseId = rs.getInt("cource_id");
                if (courseId > 0) assignment.setCourse(courseDAO.getCourse(courseId));

                assignments.add(assignment);
            }
        }
        catch (SQLException ex) {
            AppLogger.getLogger().error(ex);
        }

        return assignments;
    }

    public void createAssignment(Assignment ass){
        String sql = String.format("INSERT INTO lab0.assignments(student_id, cource_id, status) " +
                "VALUES('%s', '%s', 1)", ass.getStudent().getId(), ass.getCourse().getId());
        AppLogger.getLogger().info(sql);
        executeUpdate(sql);
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
        }
    }

    public void updateAssignment(Assignment ass){
        Course course = courseDAO.getCourseByName(ass.getCourse().getTitle());
        Student student = studentDAO.getStudentBySurname(ass.getStudent().getSurname());
        String sql = String.format("SELECT * FROM lab0.assignments WHERE student_id=%s AND cource_id=%s", student.getId(), course.getId());
        ResultSet rs = executeQuery(sql);
        Assignment assFound = getAssignments(rs).get(0);
        sql = String.format("UPDATE lab0.assignments SET mark=%s, response='%s', status=2 WHERE id=%s",
                            ass.getMark(), ass.getResponse(), assFound.getId());
        AppLogger.getLogger().info(sql);
        executeUpdate(sql);
    }
}
