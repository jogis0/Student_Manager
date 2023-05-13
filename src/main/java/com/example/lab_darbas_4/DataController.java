package com.example.lab_darbas_4;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DataController extends MasterController {

    @FXML
    private DatePicker fromDatePicker, toDatePicker;

    @FXML
    private Label csvLabel, excelLabel;

    @FXML
    protected void csvImport(ActionEvent event) throws IOException {
        CSVHandler csvHandler = new CSVHandler();
        csvHandler.importData();
        csvLabel.setText("Imported successfully");
    }

    @FXML
    protected void csvExport(ActionEvent event) throws IOException {
        CSVHandler csvHandler = new CSVHandler();
        csvHandler.exportData();
        csvLabel.setText("Exported successfully");
    }

    @FXML
    protected void excelImport(ActionEvent event) throws IOException {
        ExcelHandler excelHandler = new ExcelHandler();
        excelHandler.importData();
        excelLabel.setText("Imported successfully");
    }

    @FXML
    protected void excelExport(ActionEvent event) throws IOException {
        ExcelHandler excelHandler = new ExcelHandler();
        excelHandler.exportData();
        excelLabel.setText("Exported successfully");
    }

    @FXML
    protected void exportPdf(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Report file");
        FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF file (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(pdfFilter);
        File file = fileChooser.showSaveDialog(new Stage());

        Singleton singleton = Singleton.getInstance();
        ObservableList<Student> studentList = singleton.getStudentList();

        LocalDate start = fromDatePicker.getValue();
        LocalDate end = toDatePicker.getValue();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        PDFont font = PDType1Font.TIMES_ROMAN;
        contentStream.setFont(font, 24);
        String title = "Attendance between " + start.toString() + " and " + end.toString();
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText(title);
        contentStream.endText();

        int offset = 685;
        long totalDays = ChronoUnit.DAYS.between(start, end) + 1;

        PDFont fontItalic = PDType1Font.TIMES_BOLD_ITALIC;
        contentStream.setFont(fontItalic, 18);
        contentStream.beginText();
        offset -= 25;
        contentStream.newLineAtOffset(100, offset);
        contentStream.showText("ID   NAME   SURNAME   GROUP   ATTENDANCE");
        contentStream.endText();

        for (Student student : studentList) {
            List<LocalDate> attendanceDates = student.getAttendanceDates();
            int attendedDays = 0;

            for (LocalDate date : attendanceDates) {
                if (date.isAfter(start.minusDays(1)) && date.isBefore(end.plusDays(1))) {
                    attendedDays++;
                }
            }

            contentStream.setFont(font, 24);
            contentStream.beginText();
            offset -= 20;
            contentStream.newLineAtOffset(100, offset);
            contentStream.showText(Integer.toString(student.getId()) + " " + student.getName() + " "
                    + student.getSurname() + " " + Integer.toString(student.getGroup()) + ": " + attendedDays + "/"
                    + totalDays);
            contentStream.endText();
        }

        contentStream.close();
        document.save(file);
        document.close();
    }
}
