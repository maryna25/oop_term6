package dao.impl;

import dao.DBConnectionFactory;
import dao.contract.TeacherDAO;
import log.AppLogger;
import model.Teacher;
import dao.Encode;

import java.sql.*;

public class TeacherDAOImpl implements TeacherDAO {
    Encode encode = new Encode();

    public Teacher getTeacher(int id) {
        String sql = String.format("SELECT * FROM lab0.teachers WHERE id=%s", id);
        return getTeacherFromDB(sql);
    }

    public Teacher checkLogin(String login, String password) {
        String sql = String.format("SELECT * FROM lab0.teachers WHERE login='%s' AND password = '%s'", encode.toUTF8(login), encode.toUTF8(password));

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
                "VALUES('%s', '%s', '%s', '%s')", encode.toUTF8(teacher.getName()), encode.toUTF8(teacher.getSurname()), encode.toUTF8(teacher.getLogin()), teacher.getPassword());
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
