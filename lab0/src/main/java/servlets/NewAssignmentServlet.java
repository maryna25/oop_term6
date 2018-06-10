package servlets;

import dao.impl.CourseDAOImpl;
import dao.impl.AssignmentDAOImpl;
import log.AppLogger;
import model.Course;
import model.Student;
import model.Assignment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/NewAssignmentServlet")
public class NewAssignmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/new_assignment.jsp");
        requestDispatcher.forward(req, resp);
        AppLogger.getLogger().info("on new assignment page");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession newSession = req.getSession(true);
        Integer student_id = (Integer) newSession.getAttribute("id");
        if (student_id != null && student_id != 0) {
            Student student = new Student(student_id, "", "");
            String title = req.getParameter("title");
            Course course = new CourseDAOImpl().getCourseByName(title);
            Assignment ass = new Assignment();
            ass.setStudent(student);
            ass.setCourse(course);
            AppLogger.getLogger().info("before creation ass");
            new AssignmentDAOImpl().createAssignment(ass);
            AppLogger.getLogger().info("new assignment created");
            resp.sendRedirect("/api/student");
        }
    }
}

