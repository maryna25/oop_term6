package dao.contract;

import model.Assignment;

import java.util.List;

public interface AssignmentDAO {

    List<Assignment> getAssignmentsByStudent(int studentId);
    List<Assignment> getAssignmentsByCourse(int courseId);
    List<Assignment> getAssgnmentByTeacher(int teacherId);
    public void createAssignment(Assignment ass);
    public void updateAssignment(Assignment ass);
}
