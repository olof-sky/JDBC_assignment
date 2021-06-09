package org.skylan.data;

import org.skylan.models.Person;
import org.skylan.models.ToDo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ToDoItemsImpl implements ToDoItemsDAO {

    private static String URL = "jdbc:mysql://localhost:3306/todoit?&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
    private static String USER = "root";
    private static String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public ToDo create(ToDo toDo) {
        if(toDo == null) throw new IllegalArgumentException("ToDo was null");
        if(toDo.getId() != 0) throw new IllegalArgumentException("ToDo is already created");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ToDo created = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, toDo.getTitle());
            statement.setString(2, toDo.getDescription());
            statement.setObject(3, toDo.getDeadline());
            statement.setInt(4, toDo.getDone());
            statement.setInt(5, toDo.getAssigneeId());
            statement.execute();

            resultSet = statement.getGeneratedKeys();
            while(resultSet.next()){
                created = new ToDo(
                        resultSet.getInt(1),
                        toDo.getTitle(),
                        toDo.getDescription(),
                        toDo.getDeadline(),
                        toDo.getDone(),
                        toDo.getAssigneeId()
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return created;
    }

    public ToDo createWithoutAssignee(ToDo toDo) {
        if(toDo == null) throw new IllegalArgumentException("ToDo was null");
        if(toDo.getId() != 0) throw new IllegalArgumentException("ToDo is already created");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ToDo created = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO todo_item (title, description, deadline, done) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, toDo.getTitle());
            statement.setString(2, toDo.getDescription());
            statement.setObject(3, toDo.getDeadline());
            statement.setInt(4, toDo.getDone());
            statement.execute();

            resultSet = statement.getGeneratedKeys();
            while(resultSet.next()){
                created = new ToDo(
                        resultSet.getInt(1),
                        toDo.getTitle(),
                        toDo.getDescription(),
                        toDo.getDeadline(),
                        toDo.getDone()
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return created;
    }

    @Override
    public Collection<ToDo> findAll() {
        List<ToDo> toDoList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM todo_item");
            while (resultSet.next()){
                ToDo toDo = new ToDo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getObject(4, LocalDateTime.class),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                toDoList.add(toDo);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return toDoList;
    }

    @Override
    public ToDo findById(int id) {
        ToDo toDo = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item WHERE todo_id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                toDo = new ToDo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getObject(4, LocalDateTime.class),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return toDo;
    }

    @Override
    public Collection<ToDo> findByDoneStatus(boolean done) {
        List<ToDo> toDoList = new ArrayList<>();
        ToDo toDo;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item WHERE done = ?");
            statement.setBoolean(1, done);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                toDo = new ToDo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getObject(4, LocalDateTime.class),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                toDoList.add(toDo);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return toDoList;
    }

    @Override
    public Collection<ToDo> findByAssignee(int id) {
        Collection<ToDo> toDoList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item WHERE assignee_id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                ToDo toDo = new ToDo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getObject(4, LocalDateTime.class),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                toDoList.add(toDo);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return toDoList;
    }

    @Override
    public Collection<ToDo> findByAssignee(Person person) {
        Collection<ToDo> toDoList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item LEFT JOIN person ON assignee_id = person.person_id WHERE person.person_id = ?");
            statement.setInt(1, person.getId());
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                ToDo toDo = new ToDo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getObject(4, LocalDateTime.class),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                toDoList.add(toDo);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return toDoList;
    }

    @Override
    public Collection<ToDo> findByUnassignedToDoItems() {
        Collection<ToDo> toDoList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item WHERE assignee_id IS NULL");
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                ToDo toDo = new ToDo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getObject(4, LocalDateTime.class),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                toDoList.add(toDo);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return toDoList;
    }

    @Override
    public ToDo update(ToDo toDo) {
        if(toDo == null) throw new IllegalArgumentException("ToDo was null");
        if(toDo.getId() == 0) throw new IllegalArgumentException("ToDo is not created");
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?");
            statement.setString(1, toDo.getTitle());
            statement.setString(2, toDo.getDescription());
            statement.setObject(3, toDo.getDeadline());
            statement.setInt(4, toDo.getDone());
            statement.setInt(5, toDo.getAssigneeId());
            statement.setInt(6, toDo.getId());
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                assert statement != null;
                statement.close();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return toDo;
    }

    @Override
    public boolean deleteById(int id) {
        int deleted = 0;
        try
        {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM todo_item WHERE todo_id = ?");
            statement.setInt(1, id);
            deleted = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return deleted > 0;
    }
}
