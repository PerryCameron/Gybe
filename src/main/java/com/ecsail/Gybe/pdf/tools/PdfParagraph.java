package com.ecsail.Gybe.pdf.tools;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class PdfParagraph {
    public static Paragraph paragraphOf(String cellContent, float fontSize, PdfFont font, float fixedLeading) {
        Paragraph p = new Paragraph(cellContent);
        p.setFontSize(fontSize);
        p.setFont(font);
        p.setFixedLeading(fixedLeading);
        return p;
    }

    public static Paragraph paragraphOf(String cellContent, float fontSize, float fixedLeading) {
        Paragraph p = new Paragraph(cellContent);
        p.setFontSize(fontSize);
        p.setFixedLeading(fixedLeading);
        return p;
    }

    public static Paragraph paragraphOf(String cellContent, float fontSize, PdfFont font, Color color, TextAlignment textAlignment) {
        Paragraph paragraph = new Paragraph(cellContent);
        paragraph.setFontSize(fontSize);
        paragraph.setFont(font);
        paragraph.setFontColor(color);
        paragraph.setTextAlignment(textAlignment);
        return paragraph;
    }
    public static Paragraph paragraphOf(String cellContent, float fontSize) {
        Paragraph paragraph = new Paragraph(cellContent);
        paragraph.setFontSize(fontSize);
        return paragraph;
    }

    public static Paragraph paragraphOf(String cellContent, float fontSize, TextAlignment textAlignment) {
        Paragraph paragraph = new Paragraph(cellContent);
        paragraph.setFontSize(fontSize);
        paragraph.setTextAlignment(textAlignment);
        return paragraph;
    }

}
