package servlets;

import dao.impl.CourseDAOImpl;
import dao.impl.StudentDAOImpl;
import log.AppLogger;
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
import java.util.List;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Student student = (Student) session.getAttribute("student");
        AppLogger.getLogger().info("on student's page");

        // Get student
        if (student == null) {
            int studentId = (int) session.getAttribute("id");
            student = new StudentDAOImpl().getStudent(studentId);
        }
        // Get list of courses
        List<Course> courses = new CourseDAOImpl().getCourses();
        req.setAttribute("courses", courses);
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/student.jsp");
        requestDispatcher.forward(req, resp);
    }
}
