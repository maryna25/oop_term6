package servlets;

import dao.impl.AssignmentDAOImpl;
import dao.impl.CourseDAOImpl;
import log.AppLogger;
import model.Assignment;
import model.Course;
import model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/UpdateAssignmentServlet")
public class UpdateAssignmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Assignment ass = new Assignment();
        Course course = new Course();
        course.setTitle(req.getParameter("title"));
        ass.setCourse(course);
        Student student = new Student(req.getParameter("surname"));
        ass.setStudent(student);
        ass.setMark(Integer.parseInt(req.getParameter("mark")));
        ass.setResponse(req.getParameter("response"));
        new AssignmentDAOImpl().updateAssignment(ass);
        AppLogger.getLogger().info("new assignment created");
        resp.sendRedirect("/api/teacher");
    }
}

