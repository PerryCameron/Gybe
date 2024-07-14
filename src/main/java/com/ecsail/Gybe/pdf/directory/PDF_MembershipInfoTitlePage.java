package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.ecsail.Gybe.pdf.tools.PdfTable;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class PDF_MembershipInfoTitlePage {

    private final DirectoryModel model;

    public PDF_MembershipInfoTitlePage(DirectoryModel model) {
        this.model = model;
    }

    public Table createTitlePage() {
        Table table = PdfTable.TableOf(1,HorizontalAlignment.CENTER,model.getMainTableWidth());
        table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getPaddingTop(), false));
        Cell cell = PdfCell.cellOf(Border.NO_BORDER, VerticalAlignment.MIDDLE);
        Paragraph paragraph = PdfParagraph.paragraphOf("Membership Information", model.getTitleFontSize(),
                model.getFont(), model.getMainColor(), TextAlignment.CENTER);
        cell.add(paragraph);
        table.addCell(cell);

        table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getTitlePaddingBottom(), false));
        cell = PdfCell.cellOf(Border.NO_BORDER, VerticalAlignment.MIDDLE);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = today.format(formatter);
        String line1 = "Includes paid members as of " + formattedDate;
        cell.add(PdfParagraph.paragraphOf(line1, model.getNormalFontSize(), TextAlignment.CENTER));
        table.addCell(cell);

        cell = PdfCell.cellOf(Border.NO_BORDER, VerticalAlignment.MIDDLE);
        String line2 = ("Please notify the ECSC Membership Chair of any updates: ");
        cell.add(PdfParagraph.paragraphOf(line2, model.getNormalFontSize(), TextAlignment.CENTER));
        table.addCell(cell);

        cell = PdfCell.cellOf(Border.NO_BORDER, VerticalAlignment.MIDDLE);
        paragraph = PdfParagraph.paragraphOf(model.getMembershipEmail(), model.getNormalFontSize(), TextAlignment.CENTER);
        paragraph.setFontColor(ColorConstants.BLUE);
        cell.add(paragraph);
        table.addCell(cell);
        return table;
    }
}
