package servlets;

import dao.impl.CourseDAOImpl;
import dao.impl.TeacherDAOImpl;
import log.AppLogger;
import model.Course;
import model.Teacher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/TeacherServlet")
public class TeacherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        AppLogger.getLogger().info("on teacher's page");

        // Get teacher
        if (teacher == null) {
            int teacherId = (int) session.getAttribute("id");
            teacher = new TeacherDAOImpl().getTeacher(teacherId);
        }
        // Get list of courses
        List<Course> courses = new CourseDAOImpl().getCoursesByTeacherId(teacher.getId());
        req.setAttribute("courses", courses);
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/teacher.jsp");
        requestDispatcher.forward(req, resp);
    }
}
