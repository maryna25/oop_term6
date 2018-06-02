package dao.contract;

import model.Teacher;

public interface TeacherDAO {
    public Teacher checkLogin(String login, String password);
    public Teacher getTeacher(int teacherId);
    public void newTeacher(Teacher teacher);
}
