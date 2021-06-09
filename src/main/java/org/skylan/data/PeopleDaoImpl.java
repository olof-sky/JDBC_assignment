package org.skylan.data;
import org.skylan.models.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PeopleDaoImpl implements PeopleDAO {

    private static String URL = "jdbc:mysql://localhost:3306/todoit?&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
    private static String USER = "root";
    private static String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public Person create(Person person) {
        if(person == null) throw new IllegalArgumentException("Person was null");
        if(person.getId() != 0) throw new IllegalArgumentException("Person is already persisted");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Person created = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("INSERT INTO person (first_name, last_name) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.execute();

            resultSet = statement.getGeneratedKeys();
            while(resultSet.next()){
                created = new Person(
                        resultSet.getInt(1),
                        person.getFirstName(),
                        person.getLastName()
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
    public Collection<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM person");
            while (resultSet.next()){
                Person person = new Person(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
                personList.add(person);
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
        return personList;
    }

    @Override
    public Person findById(int id) {
        Person person = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("SELECT person_id, first_name, last_name FROM person WHERE person_id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                person = new Person(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
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
        return person;
    }

    @Override
    public Collection<Person> findByName(String inputName) {
        List<Person> personList = new ArrayList<>();
        Person person;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = getConnection();
            statement = connection.prepareStatement("SELECT person_id, first_name, last_name FROM person WHERE first_name = ?");
            statement.setString(1, inputName);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                person = new Person(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
                personList.add(person);
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
        return personList;
    }

    @Override
    public Person update(Person person) {
        if(person == null) throw new IllegalArgumentException("Person was null");
        if(person.getId() == 0) throw new IllegalArgumentException("Person is not created");
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?");
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getId());
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
        return person;
    }

    @Override
    public boolean deleteById(int id) {
        int deleted = 0;
        try
        {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE person_id = ?");
            statement.setInt(1, id);
            deleted = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return deleted > 0;
    }
}
