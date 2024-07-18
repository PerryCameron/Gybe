package com.ecsail.Gybe.pdf.tools;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

public class PdfParagraph {
    public static Paragraph paragraphOf(String cellContent, float fontSize, PdfFont font, float fixedLeading) {
        Paragraph paragraph = new Paragraph(cellContent);
        paragraph.setFontSize(fontSize);
        paragraph.setFont(font);
        paragraph.setFixedLeading(fixedLeading);
        return paragraph;
    }

    public static Paragraph paragraphOfA(String cellContent, float fontSize, PdfFont font, float fixedLeading, TextAlignment textAlignment) {
        Paragraph paragraph = new Paragraph(cellContent);
        paragraph.setFontSize(fontSize);
        paragraph.setFont(font);
        paragraph.setFixedLeading(fixedLeading);
        paragraph.setTextAlignment(textAlignment);
        return paragraph;
    }

    public static Paragraph paragraphOf(String cellContent, float fontSize, float fixedLeading) {
        Paragraph paragraph = new Paragraph(cellContent);
        paragraph.setFontSize(fontSize);
        paragraph.setFixedLeading(fixedLeading);
        return paragraph;
    }

    public static Paragraph paragraphOf(String cellContent, float fontSize, float fixedLeading, DeviceRgb color) {
        Paragraph paragraph = new Paragraph(cellContent);
        paragraph.setFontSize(fontSize);
        paragraph.setFixedLeading(fixedLeading);
        paragraph.setFontColor(color);
        return paragraph;
    }

    public static Paragraph paragraphOf(String cellContent, float fontSize, PdfFont font, DeviceRgb color, TextAlignment textAlignment) {
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
