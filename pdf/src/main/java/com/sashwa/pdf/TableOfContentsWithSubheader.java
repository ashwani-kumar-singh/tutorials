package com.sashwa.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class TableOfContentsWithSubheader {

    public static final String DEST = "toc_with_subheader.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        new TableOfContentsWithSubheader().createTOCWithSubheader(DEST);
    }

    public void createTOCWithSubheader(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        // Define sub-header
        Font subheaderFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLDITALIC, BaseColor.BLUE);
        Chunk subheader = new Chunk("Sub-Header", subheaderFont);
        Paragraph subheaderParagraph = new Paragraph(subheader);
        subheaderParagraph.setIndentationLeft(20); // Adjust indentation as needed

        // Create table of contents
        PdfPTable tableOfContents = new PdfPTable(1);
        tableOfContents.setWidthPercentage(100);
        addTableCell(tableOfContents, subheaderParagraph, "Chapter 1: Introduction", 1);
        addTableCell(tableOfContents, null, "Section 1.1: Overview", 2); // Sub-header entry
        addTableCell(tableOfContents, null, "Section 1.2: Background", 2); // Sub-header entry
        addTableCell(tableOfContents, subheaderParagraph, "Chapter 2: Methods", 1);
        addTableCell(tableOfContents, subheaderParagraph, "Chapter 3: Results", 1);
        addTableCell(tableOfContents, subheaderParagraph, "Chapter 4: Conclusion", 1);

        // Add TOC to the document
        document.add(tableOfContents);

        document.close();
        writer.close();
    }

    private void addTableCell(PdfPTable table, Paragraph subheader, String content, int colspan) {
        PdfPCell cell = new PdfPCell();
        if (subheader != null) {
            cell.addElement(subheader);
        }
        cell.addElement(new Paragraph(content, FontFactory.getFont(FontFactory.HELVETICA, 12)));
        cell.setColspan(colspan);
        table.addCell(cell);
    }
}

