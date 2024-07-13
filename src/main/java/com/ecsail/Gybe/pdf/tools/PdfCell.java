package com.ecsail.Gybe.pdf.tools;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
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

//    cell[0] = new Cell();
//    cell[0].setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(0.5f))
//            .setBackgroundColor(pdfDirectory.getMainColor()).setVerticalAlignment(VerticalAlignment.MIDDLE)
//                .setWidth(tableWidth * 0.5f).setHorizontalAlignment(HorizontalAlignment.CENTER).add(paragraph);

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
