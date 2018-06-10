package servlets;

import dao.impl.CourseDAOImpl;
import log.AppLogger;
import model.Course;
import model.Teacher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/NewCourseServlet")
public class NewCourseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/new_course.jsp");
        requestDispatcher.forward(req, resp);
        AppLogger.getLogger().info("on new course page");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppLogger.getLogger().info("trying to create 2");
        HttpSession newSession = req.getSession(true);
        Integer teacher_id = (Integer) newSession.getAttribute("id");
        if (teacher_id != null && teacher_id != 0) {
            Teacher teacher = new Teacher(teacher_id, "", "");
            Course course = new Course();
            course.setTitle(req.getParameter("title"));
            course.setProgram(req.getParameter("program"));
            course.setTeacher(teacher);
            new CourseDAOImpl().createCourse(course);
            AppLogger.getLogger().info("new course created");
            resp.sendRedirect("/api/teacher");
        }
    }
}

