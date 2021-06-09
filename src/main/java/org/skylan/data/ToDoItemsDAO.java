package org.skylan.data;

import org.skylan.models.Person;
import org.skylan.models.ToDo;

import java.util.Collection;

public interface ToDoItemsDAO {
    ToDo create(ToDo toDo);
    Collection<ToDo> findAll();
    ToDo findById(int id);
    Collection<ToDo> findByDoneStatus(boolean done);
    Collection<ToDo> findByAssignee(int id);
    Collection<ToDo> findByAssignee(Person person);
    Collection<ToDo> findByUnassignedToDoItems();
    ToDo update(ToDo toDo);
    boolean deleteById(int id);
}
