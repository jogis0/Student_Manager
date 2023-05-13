module com.example.lab_darbas_4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml.schemas;
    requires org.apache.pdfbox;


    opens com.example.lab_darbas_4 to javafx.fxml;
    exports com.example.lab_darbas_4;
}