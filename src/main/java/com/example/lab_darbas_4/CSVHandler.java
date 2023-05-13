package com.example.lab_darbas_4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class CSVHandler implements FileHandler {
    private ObservableList<Student> studentList;
    private ObservableList<Integer> groupList;
    @Override
    public void exportData() throws IOException {
        getSingletonData();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export file");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);
        File file = fileChooser.showSaveDialog(new Stage());

        FileWriter writer = new FileWriter(file);
        for(int i = 0; i < studentList.size(); ++i){
            writer.write(Integer.toString(studentList.get(i).getId()) + ";" + studentList.get(i).getName() + ";" +
                    studentList.get(i).getSurname() + ";" + Integer.toString(studentList.get(i).getGroup()) + "\n");
        }
        writer.close();
    }

    @Override
    public void importData() throws IOException {
        studentList = FXCollections.observableArrayList();
        groupList = FXCollections.observableArrayList();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import file");
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);
        File file = fileChooser.showOpenDialog(new Stage());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = "";

        while((line = bufferedReader.readLine()) != null){
            String[] student = line.split(";");
            int group = Integer.parseInt(student[3]);
            studentList.add(new Student(Integer.parseInt(student[0]), student[1], student[2], group));
            if(!groupList.contains(group)){
                groupList.add(group);
            }
        }
        //TODO: Sort groupList
        //groupList.sorted();

        setSingletonData();
    }

    private void getSingletonData(){
        Singleton singleton = Singleton.getInstance();
        studentList = singleton.getStudentList();
        groupList = singleton.getGroupList();
    }

    private void setSingletonData(){
        Singleton singleton = Singleton.getInstance();
        singleton.setStudentList(studentList);
        singleton.setGroupList(groupList);
    }
}
