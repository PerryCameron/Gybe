package com.ecsail.Gybe.pdf.tools;

import com.ecsail.Gybe.pdf.enums.Sections;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

public class PdfCell {
    public static Cell cellOf(Border border, HorizontalAlignment horizontalAlignment) {
        Cell cell = new Cell();
        cell.setBorder(border);
        cell.setHorizontalAlignment(horizontalAlignment);
        return cell;
    }

    public static Cell cellOf(Border border, VerticalAlignment verticalAlignment) {
        Cell cell = new Cell();
        cell.setBorder(border);
        cell.setVerticalAlignment(verticalAlignment);
        return cell;
    }

    public static Cell cellOf(Border border, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
        Cell cell = new Cell();
        cell.setBorder(border);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(verticalAlignment);
        return cell;
    }

    public static Cell cellOf(int colspan, Border border, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
        Cell cell = new Cell(1, colspan);
        cell.setBorder(border);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(verticalAlignment);
        return cell;
    }

    public static Cell cellOf(int rowSpan, int colSpan, Border border) {
        Cell cell = new Cell(rowSpan, colSpan);
        cell.setBorder(border);
        return cell;
    }

    public static Cell cellOf(Border border) {
        Cell cell = new Cell();
        cell.setBorder(border);
        return cell;
    }

    public static Cell cellOf(float width) {
        Cell cell = new Cell();
        cell.setWidth(width);
        return cell;
    }

    public static Cell cellOf(Border border, HorizontalAlignment horizontalAlignment
            , VerticalAlignment verticalAlignment, Border borderTop, DeviceCmyk backGroundColor, float width) {
        Cell cell = new Cell();
        cell.setBorder(border);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(verticalAlignment);
        cell.setBorderTop(borderTop);
        cell.setBackgroundColor(backGroundColor);
        cell.setWidth(width);
        return cell;
    }

    public static Cell verticalSpaceCellWithPadding(float padding, boolean isTest) {
        Cell cell = new Cell();
        cell.add(new Paragraph("")); // Add an empty paragraph to the cell
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
        cell.setBorder(Border.NO_BORDER);
        if (isTest) {
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
        }
        return cell;
    }

    public static Cell verticalSpaceCellWithPadding(float padding, int colspan) {
        Cell cell = new Cell(1, colspan); // Set the rowspan to 1 and colspan to the parameter value
        cell.add(new Paragraph("")); // Add an empty paragraph to the cell
        cell.setPaddingTop(padding);
        cell.setPaddingBottom(padding);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }

    public static Cell addPageCell(int colspan, String content, float fixLeadOffset, float fontSize, Color color) {
        Cell cell = PdfCell.cellOf(colspan, Border.NO_BORDER, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
        Paragraph paragraph = PdfParagraph.paragraphOf(content, fontSize, TextAlignment.CENTER);
        if (fixLeadOffset != 0) paragraph.setFixedLeading(fixLeadOffset);
        if (color != null) paragraph.setFontColor(color);
        cell.add(paragraph);
        return cell;
    }

    public static Cell dockLeft(float width, float height, DeviceCmyk dockColor, Sections sections, Paragraph[] paragraph) {
        Cell cell = new Cell();
        cell.setWidth(width);
        cell.setHeight(height);
        cell.setBorder(Border.NO_BORDER);

        switch (sections) {
            case FULL_SECTION, LEFT_ONLY -> {
                cell.setBorderLeft(new SolidBorder(1f));
                cell.setBorderBottom(new SolidBorder(1f));
                cell.setBorderTop(new SolidBorder(1f));
                cell.setBackgroundColor(dockColor);
                cell.add(paragraph[0].setTextAlignment(TextAlignment.RIGHT));
                cell.add(paragraph[1].setTextAlignment(TextAlignment.RIGHT));
            }
            case RIGHT_ONLY, CONNECTOR_SECTION, TOP_SECTION, BOTTOM_SECTION -> {
                return cell;
            }
        }
        return cell;
    }

    public static Cell dockRight(float width, float height, DeviceCmyk dockColor, Sections sections, Paragraph[] paragraph) {
        Cell cell = new Cell();
        cell.setWidth(width);
        cell.setBorder(Border.NO_BORDER);
        switch (sections) {
            case FULL_SECTION, RIGHT_ONLY -> {
                cell.setBorderRight(new SolidBorder(1f));
                cell.setBorderBottom(new SolidBorder(1f));
                cell.setBorderTop(new SolidBorder(1f));
                cell.setBackgroundColor(dockColor);
                cell.add(paragraph[3]).add(paragraph[4]);
            }
            case LEFT_ONLY, CONNECTOR_SECTION, TOP_SECTION, BOTTOM_SECTION -> {
                return cell;
            }
        }
        cell.setHeight(height);
        return cell;
    }

    public static Cell dockCenter(float width, float height, DeviceCmyk dockColor, Sections sections, Paragraph dockText) {
        Cell cell = new Cell();
        cell.setBackgroundColor(dockColor);
        cell.setBorder(Border.NO_BORDER);
        cell.setHeight(height);
        cell.setWidth(width);
        switch (sections) {
            case FULL_SECTION -> {
                return cell;
            }
            case CONNECTOR_SECTION -> {
                cell.setBorderRight(new SolidBorder(1f));
                cell.setBorderLeft(new SolidBorder(1f));
            }
            case LEFT_ONLY -> {
                cell.setBorderRight(new SolidBorder(1f));
            }
            case RIGHT_ONLY -> {
                cell.setBorderLeft(new SolidBorder(1f));
            }
            case TOP_SECTION -> {
                cell.setBorderRight(new SolidBorder(1f));
                cell.setBorderLeft(new SolidBorder(1f));
                cell.setBorderTop(new SolidBorder(1f));
            }
            case BOTTOM_SECTION -> {
                cell.setBorderRight(new SolidBorder(1f));
                cell.setBorderLeft(new SolidBorder(1f));
                cell.setBorderBottom(new SolidBorder(1f));
                dockText.setFontSize(7).setTextAlignment(TextAlignment.CENTER);
                cell.add(dockText);
            }
        }
        return cell;
    }
}
