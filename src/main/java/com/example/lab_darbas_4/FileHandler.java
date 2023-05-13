package com.example.lab_darbas_4;

import java.io.IOException;

public interface FileHandler {
    //DATA FORMAT
    //ID   NAME   SURNAME   GROUP

    //Import CSV
    //Import XLSX
    //Export to CSV
    //Export to XLSX
    //Export to PDF

    //For writing data to file
    void exportData() throws IOException;
    //For importing data from file
    void importData() throws IOException;
}
