package org.skylan.data;

import org.skylan.models.Person;
import org.skylan.models.ToDo;

import java.util.Collection;

public class ToDoItemsImpl implements ToDoItemsDAO {

    @Override
    public ToDo create(ToDo toDo) {
        return null;
    }

    @Override
    public Collection<ToDo> findAll() {
        return null;
    }

    @Override
    public ToDo findById(int id) {
        return null;
    }

    @Override
    public Collection<ToDo> findByDoneStatus(boolean done) {
        return null;
    }

    @Override
    public Collection<ToDo> findByAssignee(int id) {
        return null;
    }

    @Override
    public Collection<ToDo> findByAssignee(Person person) {
        return null;
    }

    @Override
    public Collection<ToDo> findByUnassignedToDoItems() {
        return null;
    }

    @Override
    public ToDo update(ToDo toDo) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
