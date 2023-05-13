package com.example.lab_darbas_4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Singleton {
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private ObservableList<Integer> groupList = FXCollections.observableArrayList();
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if(instance == null)
            instance = new Singleton();
        return instance;
    }

    public void setStudentList(ObservableList<Student> list) {
        this.studentList = list;
    }

    public ObservableList<Student> getStudentList() {
        return this.studentList;
    }

    public void setGroupList(ObservableList<Integer> groupList) {
        this.groupList = groupList;
    }

    public ObservableList<Integer> getGroupList() {
        return groupList;
    }
}
