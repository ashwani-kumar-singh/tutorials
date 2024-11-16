package com.sashwa.pdf.event;

import com.google.common.collect.ImmutableList;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * How To Set Header and Footer in pdf Using Itext Example -
 * Using iText library - core java tutorial
 */

public class HeaderAndFooterEvent extends PdfPageEventHelper {
    private PdfTemplate t;
    private Image total;
    private int lastPageNumber;

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
    }

    private void addHeader(PdfWriter writer) {
        if (!ImmutableList.of(1, 2).contains(writer.getPageNumber())) {
            PdfPTable header = new PdfPTable(2);
            try {
                // set defaults
                header.setWidths(new int[]{11, 17});
                header.setTotalWidth(527);
                header.setLockedWidth(true);
                header.getDefaultCell().setFixedHeight(40);
                header.getDefaultCell().setBorder(Rectangle.BOTTOM);

                PdfPCell textBlank = new PdfPCell();
                textBlank.setPaddingBottom(5);
                textBlank.setBorder(Rectangle.BOTTOM);
                textBlank.setBorderColor(BaseColor.ORANGE);
                textBlank.setBorderWidth(2);
                header.addCell(textBlank);

                // add text
                PdfPCell text = new PdfPCell();
                text.setPaddingBottom(5);
                text.setBorder(Rectangle.BOTTOM);
                text.setBorderColor(BaseColor.ORANGE);
                text.setBorderWidth(2);
                text.setHorizontalAlignment(Element.ALIGN_LEFT);
                text.addElement(new Phrase("Cyber Resiliency - Test Run Book", new Font(Font.FontFamily.HELVETICA, 8)));
                header.addCell(text);
                // write content
                header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
    }

    private void addFooter(PdfWriter writer) {
        if (!ImmutableList.of(1, 2, lastPageNumber).contains(writer.getPageNumber())) {
            PdfPTable footer = new PdfPTable(2);
            try {
                // set defaults
                footer.setWidths(new int[]{13, 13});
                footer.setTotalWidth(527);
                footer.setLockedWidth(true);
                footer.getDefaultCell().setFixedHeight(40);
                footer.getDefaultCell().setBorder(Rectangle.TOP);
                footer.getDefaultCell().setBorderColor(BaseColor.ORANGE);
                footer.getDefaultCell().setBorderWidth(2);

                // add current page count
                footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

                Phrase phrase = new Phrase(String.format("Page %d of", writer.getPageNumber()),
                        new Font(Font.FontFamily.HELVETICA, 8));
                footer.addCell(phrase);
                // add placeholder for total page count
                PdfPCell totalPageCount = new PdfPCell(total);
                totalPageCount.setBorder(Rectangle.TOP);
                totalPageCount.setBorderWidth(2);
                totalPageCount.setBorderColor(BaseColor.ORANGE);
                footer.addCell(totalPageCount);
                // write page
                PdfContentByte canvas = writer.getDirectContent();
                canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
                footer.writeSelectedRows(0, -1, 34, 50, canvas);
                canvas.endMarkedContentSequence();
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
    }

    public void setLastPageNumber(int lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

}

