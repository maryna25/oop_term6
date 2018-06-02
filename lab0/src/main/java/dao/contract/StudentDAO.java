package dao.contract;

        import model.Student;

public interface StudentDAO {
    public Student checkLogin(String login, String password);
    public Student getStudent(int studentId);
    public void newStudent(Student student);
    public Student getStudentBySurname(String surname);
}
