package servlets;

import dao.impl.TeacherDAOImpl;
import dao.impl.StudentDAOImpl;
import dao.impl.AssignmentDAOImpl;
import log.AppLogger;
import model.Assignment;
import model.Teacher;
import model.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/AssignmentsServlet")
public class AssignmentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        AppLogger.getLogger().info("on assignments page");

        List<Assignment> ass = new LinkedList<>();

        if (role == "teacher") {
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            if (teacher == null) {
                int teacherId = (int) session.getAttribute("id");
                teacher = new TeacherDAOImpl().getTeacher(teacherId);
            }
            AppLogger.getLogger().info("teacher is found!");
            ass = new AssignmentDAOImpl().getAssgnmentByTeacher(teacher.getId());
        }
        else {
            AppLogger.getLogger().error("unknown role");
        }
        req.setAttribute("assignments", ass);
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/assignments.jsp");
        requestDispatcher.forward(req, resp);
    }
}
