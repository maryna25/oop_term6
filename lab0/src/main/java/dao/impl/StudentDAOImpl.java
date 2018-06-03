package dao.impl;

import dao.DBConnectionFactory;
import dao.contract.StudentDAO;
import log.AppLogger;
import model.Student;
import dao.Encode;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    Encode encode = new Encode();

    public Student getStudent(int id) {
        String sql = String.format("SELECT * FROM lab0.students WHERE id=%s", id);
        return getStudentFromDB(sql);
    }

    public Student getStudentBySurname(String surname){
        String sql = String.format("SELECT * FROM lab0.students WHERE surname='%s'", encode.toUTF8(surname));
        return getStudentFromDB(sql);
    }

    public Student checkLogin(String login, String password) {
        String sql = String.format("SELECT * FROM lab0.students WHERE login='%s' AND password = '%s'", encode.toUTF8(login), encode.toUTF8(password));

       return getStudentFromDB(sql);
    }


    private Student getStudentFromDB(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnectionFactory.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                return new Student(id, name, surname);
            }

        } catch (SQLException e) {
            AppLogger.getLogger().error(e);
        } finally {
            DBConnectionFactory.closeConnection(statement, connection);
        }

        return null;
    }

    public void newStudent(Student student){
        String sql = String.format("INSERT INTO lab0.students(name, surname, login, password) " +
                "VALUES('%s', '%s', '%s', '%s')", encode.toUTF8(student.getName()), encode.toUTF8(student.getSurname()), encode.toUTF8(student.getLogin()), encode.toUTF8(student.getPassword()));
        AppLogger.getLogger().info(sql);
        executeUpdate(sql);
    }

    private void executeUpdate(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DBConnectionFactory.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            AppLogger.getLogger().error("SQL execution failed", e);
        } finally {
            DBConnectionFactory.closeConnection(statement, connection);
        }
    }
}
