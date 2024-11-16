package com.sashwa.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateAndReorderPDF {

    public static final String SRC = "created.pdf";
    public static final String DEST = "reordered.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        // Step 1: Create PDF using iText 5
        createPdf();
    }

    public static void createPdf() throws IOException, DocumentException {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        document.add(new Paragraph("Page 1"));
        document.newPage();
        document.add(new Paragraph("Page 2"));
        document.newPage();
        document.add(new Paragraph("Page 3"));
        document.close();
        reorderPdf(byteArrayOutputStream);
    }

    public static void reorderPdf(ByteArrayOutputStream byteArrayOutputStream) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(byteArrayOutputStream.toByteArray());
        int totalPages = reader.getNumberOfPages();
        Document document = new Document();
        FileOutputStream fileOutputStream = new FileOutputStream(DEST);
        PdfCopy copy = new PdfCopy(document, fileOutputStream);
        document.open();
        for (int i = totalPages; i > 0; i--) {
            copy.addPage(copy.getImportedPage(reader, i));
        }
        document.close();
        reader.close();
    }

}
