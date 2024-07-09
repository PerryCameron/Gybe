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

    public static Paragraph paragraphOf(String cellContent, float fontSize, PdfFont font, Color color, TextAlignment textAlignment) {
        Paragraph p = new Paragraph(cellContent);
        p.setFontSize(fontSize);
        p.setFont(font);
        p.setFontColor(color);
        p.setTextAlignment(textAlignment);
        return p;
    }

    public static Paragraph paragraphOf(String cellContent, float fontSize) {
        Paragraph p = new Paragraph(cellContent);
        p.setFontSize(fontSize);
        return p;
    }

}
