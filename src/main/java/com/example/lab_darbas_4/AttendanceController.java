package com.example.lab_darbas_4;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AttendanceController extends MasterController implements Initializable {
    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, Integer> idColumn, groupColumn;

    @FXML
    private TableColumn<Student, String> nameColumn, surnameColumn;

    @FXML
    private CheckBox MarkCheck;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Integer> filterGroupBox;

    @FXML
    private TextField studentField;

    @FXML
    private Label markLabel;

    private int selectedStudentIndex = -1;

    ObservableList<Student> studentList;
    ObservableList<Integer> groupList;

    @FXML
    protected void mark(ActionEvent event){
        //If student hasn't been selected
        if(selectedStudentIndex == -1){
            return;
        }

        boolean mark = MarkCheck.isSelected();
        LocalDate date = datePicker.getValue();
        studentList.get(selectedStudentIndex).attendance.put(date, mark);
        markLabel.setText("Student marked");
    }

    @FXML
    protected void onDatePick(ActionEvent event){
        markLabel.setText("");

        //If student hasn't been selected
        if(selectedStudentIndex == -1){
            return;
        }

        LocalDate date = datePicker.getValue();
        if(studentList.get(selectedStudentIndex).attendance.containsKey(date)) {
            boolean marked = studentList.get(selectedStudentIndex).attendance.get(date);
            MarkCheck.setSelected(marked);
        } else {
            MarkCheck.setSelected(false);
        }
    }

    @FXML
    protected void filterByGroup(){
        Integer selection = filterGroupBox.getValue();
        ObservableList<Student> sublist = studentList;

        if(selection == null){
            table.setItems(sublist);
        } else {
            table.setItems(sublist.filtered(row -> row.getGroup() == selection));
        }
    }

    @FXML
    protected void loadStudentFieldData(MouseEvent event){
        selectedStudentIndex = table.getSelectionModel().getSelectedIndex();

        if(nameColumn.getCellData(selectedStudentIndex) != null){
            studentField.setText(nameColumn.getCellData(selectedStudentIndex) + " "
                    + surnameColumn.getCellData(selectedStudentIndex));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Adding initial data to this class
        Singleton singleton = Singleton.getInstance();
        studentList = singleton.getStudentList();
        groupList = singleton.getGroupList();

        //Set up table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));

        //Set up filter by group
        filterGroupBox.getItems().add(null);
        filterGroupBox.setValue(null);
        filterGroupBox.getItems().addAll(groupList);

        table.setItems(studentList);
    }
}
