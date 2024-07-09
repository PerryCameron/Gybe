package com.ecsail.Gybe.pdf.tools;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class PdfCell {
    public static Cell cellOf(Border border, HorizontalAlignment horizontalAlignment) {
        Cell cell = new Cell();
        cell.setBorder(border);
        cell.setHorizontalAlignment(horizontalAlignment);
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

    public static Cell verticalSpaceCell(int space) {
        String carrageReturn = "";
        for(int i = 0; i < space; i++) {
            carrageReturn += "\n";
        }
        Cell cell = new Cell();
        Paragraph p = new Paragraph(carrageReturn);
        p.setFixedLeading(7);
        cell.add(p);
        cell.setBorder(Border.NO_BORDER);
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

}
