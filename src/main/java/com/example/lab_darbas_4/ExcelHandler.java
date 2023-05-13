package com.example.lab_darbas_4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelHandler implements FileHandler {
    private ObservableList<Student> studentList;
    private ObservableList<Integer> groupList;
    @Override
    public void exportData() throws IOException {
        getSingletonData();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export file");
        FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(excelFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        FileOutputStream fos = new FileOutputStream(file);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");

        int i = 0;
        for (Student st : studentList) {
            Row row = sheet.createRow(i++);
            row.createCell(0).setCellValue(st.id);
            row.createCell(1).setCellValue(st.name);
            row.createCell(2).setCellValue(st.surname);
            row.createCell(3).setCellValue(st.group);
        }

        workbook.write(fos);
        workbook.close();
    }

    @Override
    public void importData() throws IOException {
        studentList = FXCollections.observableArrayList();
        groupList = FXCollections.observableArrayList();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import file");
        FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(excelFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        FileInputStream fis = new FileInputStream(file);

        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            int id = (int) row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();
            String surname = row.getCell(2).getStringCellValue();
            int group = (int) row.getCell(3).getNumericCellValue();
            studentList.add(new Student(id, name, surname, group));
        }
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
