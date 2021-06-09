package org.skylan.models;
import java.time.LocalDateTime;

public class ToDo {
    int id;
    String title;
    String description;
    LocalDateTime deadline;
    int done;
    int assigneeId;

    public ToDo(int id, String title, String description, LocalDateTime deadline, int done, int assigneeId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.assigneeId = assigneeId;
    }

    public ToDo(int id, String title, String description, LocalDateTime deadline, int done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
    }

    public ToDo(String title, String description, LocalDateTime deadline, int done, int assigneeId) {
        this(0, title, description, deadline, done, assigneeId);
    }

    public ToDo(String title, String description, LocalDateTime deadline, int done) {
        this(0, title, description, deadline, done);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", id=" + id +
                ", deadline=" + deadline +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
