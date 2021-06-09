package org.skylan.models;
import java.time.LocalDateTime;

public class ToDo {
    int assigneeId;
    int done;
    int toDoId;
    LocalDateTime deadline;
    String description;
    String title;

    public ToDo(int assigneeId, int done, int toDoId, LocalDateTime deadline, String description, String title) {
        this.assigneeId = assigneeId;
        this.done = done;
        this.toDoId = toDoId;
        this.deadline = deadline;
        this.description = description;
        this.title = title;
    }

    public ToDo(int done, int toDoId, LocalDateTime deadline, String description, String title) {
        this(0, done, toDoId, deadline, description, title);
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getToDoId() {
        return toDoId;
    }

    public void setToDoId(int toDoId) {
        this.toDoId = toDoId;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "assigneeId=" + assigneeId +
                ", done=" + done +
                ", toDoId=" + toDoId +
                ", deadline=" + deadline +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
