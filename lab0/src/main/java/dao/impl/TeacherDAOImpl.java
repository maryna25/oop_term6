package dao.impl;

import dao.DBConnectionFactory;
import dao.contract.TeacherDAO;
import log.AppLogger;
import model.Teacher;

import java.sql.*;

public class TeacherDAOImpl implements TeacherDAO {

    public Teacher getTeacher(int id) {
        String sql = String.format("SELECT * FROM lab0.teachers WHERE id=%s", id);
        return getTeacherFromDB(sql);
    }

    public Teacher checkLogin(String login, String password) {
        String sql = String.format("SELECT * FROM lab0.teachers WHERE login='%s' AND password = '%s'", login, password);

        return getTeacherFromDB(sql);
    }

    private Teacher getTeacherFromDB(String sql) {
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
                return new Teacher(id, name, surname);
            }

        } catch (SQLException e) {
            AppLogger.getLogger().error(e);
        } finally {
            DBConnectionFactory.closeConnection(statement, connection);
        }

        return null;
    }
    public void newTeacher(Teacher teacher){
        String sql = String.format("INSERT INTO lab0.teachers(name, surname, login, password) " +
                "VALUES('%s', '%s', '%s', '%s')", teacher.getName(), teacher.getSurname(), teacher.getLogin(), teacher.getPassword());
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
        }
    }
}
