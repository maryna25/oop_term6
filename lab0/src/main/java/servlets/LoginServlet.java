package servlets;

import dao.contract.TeacherDAO;
import dao.contract.StudentDAO;
import dao.impl.StudentDAOImpl;
import log.AppLogger;
import model.Teacher;
import model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Role: " + req.getSession().getAttribute("role"));
        HttpSession session = req.getSession();
        // Teacher page
        if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("teacher")) {
            resp.sendRedirect("/api/teacher");
        }
        // Student page
        else if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("student")) {
            resp.sendRedirect("/api/student");
        }
        // Login page
        else {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        AppLogger.getLogger().info("login");
        Teacher teacher = getTeacher(login, password);
        Student student;
        // Check login as teacher first
        if (teacher != null) {
            System.out.println("Logged in as teacher");

            // Invalidate old session
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) oldSession.invalidate();
            // Create new session
            HttpSession newSession = req.getSession(true);

            // 30 minutes expiry time
            newSession.setMaxInactiveInterval(30 * 60);
            newSession.setAttribute("role", "teacher");
            newSession.setAttribute("id", teacher.getId());
            newSession.setAttribute("name", teacher.getName());

            AppLogger.getLogger().info("Teacher login");
            resp.sendRedirect("/api/teacher");
        }
        // Check login as student
        else if ((student = getStudent(login, password)) != null) {
            System.out.println("Logged in as student");

            // Invalidate old session
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) oldSession.invalidate();
            // Create new session
            HttpSession newSession = req.getSession(true);

            newSession.setAttribute("student", student);

            // 30 minutes expiry time
            newSession.setMaxInactiveInterval(30 * 60);
            newSession.setAttribute("role", "student");
            newSession.setAttribute("id", student.getId());
            newSession.setAttribute("name", student.getName());

            AppLogger.getLogger().info("Student login");
            resp.sendRedirect("/api/student");
        }
        else {
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp");
            req.setAttribute("msg", "Incorrect credentials");
            requestDispatcher.forward(req, resp);
        }
    }

    private Teacher getTeacher(String login, String password) {
        TeacherDAO teacherDAO = new dao.impl.TeacherDAOImpl();
        return teacherDAO.checkLogin(login, password);
    }

    private Student getStudent(String login, String password) {
        StudentDAO studentDAO = new StudentDAOImpl();
        return studentDAO.checkLogin(login, password);
    }
}
