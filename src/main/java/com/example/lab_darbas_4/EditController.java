package com.example.lab_darbas_4;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditController extends MasterController implements Initializable {
    @FXML
    private TextField groupNumberField, idField, nameField, surnameField, editIdField, editNameField, editSurnameField;

    @FXML
    private ChoiceBox<Integer> groupBox, editGroupBox;

    @FXML
    private Label addStudentLabel, addGroupLabel;

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, Integer> idColumn, groupColumn;

    @FXML
    private TableColumn<Student, String> nameColumn, surnameColumn;

    private ObservableList<Student> studentList;
    private ObservableList<Integer> groupList;

    private int selectedStudentIndex;

    @FXML
    protected void addStudent(ActionEvent event){
        //Check if all fields have input
        if (Objects.equals(idField.getText(), "") || Objects.equals(nameField.getText(), "") ||
                Objects.equals(surnameField.getText(), "") || Objects.equals(groupBox.getValue(), null)) {
            addStudentLabel.setText("Fill all fields");
            return;
        }
        //Check if student with the same ID exists
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String surname = surnameField.getText();
        for (Student st : studentList) {
            if (st.id == id) {
                addStudentLabel.setText("ID already exists");
                return;
            }
        }

        //Add student
        studentList.add(new Student(id, name, surname, groupBox.getValue()));
        addStudentLabel.setText("Student added");
        table.setItems(studentList);
        sendDataToSingleton();
    }
    @FXML
    protected void addGroup(ActionEvent event){
        if(Objects.equals(groupNumberField.getText(), "")){
            addGroupLabel.setText("Field must be filled");
            return;
        }

        //Check if group already exists
        int groupNumber = Integer.parseInt(groupNumberField.getText());
        for (Integer i: groupBox.getItems()) {
            if (i == groupNumber){
                addGroupLabel.setText("Group already exists");
                return;
            }
        }

        //Add group
        groupBox.getItems().add(groupNumber);
        editGroupBox.getItems().add(groupNumber);
        addGroupLabel.setText("Group added");
        groupList = groupBox.getItems();
        sendDataToSingleton();
    }

    //TODO: Add search bar (optional)
    /*
    //...
    */

    @FXML
    protected void loadEditData(MouseEvent event){
        selectedStudentIndex = table.getSelectionModel().getSelectedIndex();

        //When table is empty, do nothing
        if(nameColumn.getCellData(selectedStudentIndex) != null){
            editNameField.setText(nameColumn.getCellData(selectedStudentIndex));
            editSurnameField.setText(surnameColumn.getCellData(selectedStudentIndex));
            editIdField.setText(Integer.toString(idColumn.getCellData(selectedStudentIndex)));
            editGroupBox.setValue(groupColumn.getCellData(selectedStudentIndex));
        }
        //Using any column (name, surname, id, group) is okay, because there will not be any students with null columns
    }

    @FXML
    protected void updateStudent(ActionEvent event){
        studentList.set(selectedStudentIndex, new Student(Integer.parseInt(editIdField.getText()),
                editNameField.getText(), editSurnameField.getText(), editGroupBox.getValue()));
        table.setItems(studentList);
        //TODO: Check if fields are null
        //TODO: Check if ID already exists
        sendDataToSingleton();
    }

    @FXML
    protected void deleteStudent(ActionEvent event){
        studentList.remove(selectedStudentIndex);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Adding initial data to this class
        Singleton singleton = Singleton.getInstance();
        studentList = singleton.getStudentList();
        groupList = singleton.getGroupList();
        groupBox.getItems().addAll(groupList);
        editGroupBox.getItems().addAll(groupList);

        //Set up table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));

        table.setItems(studentList);
    }

    private void sendDataToSingleton(){
        Singleton singleton = Singleton.getInstance();
        singleton.setStudentList(studentList);
        singleton.setGroupList(groupList);

    }
}
