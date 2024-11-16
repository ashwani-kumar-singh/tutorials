package com.sashwa.pdf.event;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class TOCEvent extends PdfPageEventHelper {

    protected List<AbstractMap.SimpleEntry<String, Integer>> toc = new ArrayList<>();

    @Override
    public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {
        toc.add(new AbstractMap.SimpleEntry<>(text, writer.getPageNumber()));
    }

    public List<AbstractMap.SimpleEntry<String, Integer>> getTOC() {
        return toc;
    }
}
