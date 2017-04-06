package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Course {
    private String name;
    private ObservableList<CourseItem> marks;

    public Course(String name) {
        this.name = name;
        this.marks = FXCollections.observableArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<CourseItem> getMarks() {
        return marks;
    }

    public void setMarks(ObservableList<CourseItem> marks) {
        this.marks = marks;
    }
}
