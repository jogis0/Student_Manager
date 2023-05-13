package com.example.lab_darbas_4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    int id;
    String name;
    String surname;
    int group;
    HashMap<LocalDate, Boolean> attendance = new HashMap<>();

    public Student(int id, String name, String surname, int group) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getGroup(){
        return group;
    }

    public List<LocalDate> getAttendanceDates(){
        List<LocalDate> dates = new ArrayList<>();
        for (Map.Entry<LocalDate, Boolean> entry: attendance.entrySet()){
            if (entry.getValue()){
                dates.add(entry.getKey());
            }
        }
        return dates;
    }
}
