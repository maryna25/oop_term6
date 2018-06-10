package servlets;

import dao.impl.StudentDAOImpl;
import dao.impl.TeacherDAOImpl;
import log.AppLogger;
import model.Student;
import model.Teacher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/sign_up.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String role = req.getParameter("role");

        AppLogger.getLogger().info(String.format("sign up: %s", role));
        if (role.equals("teacher")) {
            Teacher teacher = new Teacher(name, surname, login, password);
            new TeacherDAOImpl().newTeacher(teacher);
            resp.sendRedirect("/api");
        }
        else if (role.equals("student")) {
            Student student = new Student(name, surname, login, password);
            new StudentDAOImpl().newStudent(student);
            resp.sendRedirect("/api");
        }
        else
            AppLogger.getLogger().info("Incorrect role");
    }
}
