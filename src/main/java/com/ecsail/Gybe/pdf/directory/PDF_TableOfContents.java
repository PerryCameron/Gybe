package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

public class PDF_TableOfContents extends Table {
    PDF_Object_Settings set;

    public PDF_TableOfContents(int numColumns, PDF_Object_Settings set) {
        super(numColumns);
        this.set = set;
        ////////////////// Table Properties //////////////////
        setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
        setHorizontalAlignment(HorizontalAlignment.CENTER);

        ///////////////// Cells ////////////////////////////
        addCell(PdfCell.verticalSpaceCellWithPadding(20, false));
        addCell(addPageCell("Membership Directory", 0, set.getNormalFontSize() + 8, set.getMainColor()));
        addCell(PdfCell.verticalSpaceCellWithPadding(15, false));
        addCell(addPageCell("Commodore Greeting", 0, set.getNormalFontSize() + 4, null));
        addCell(PdfCell.verticalSpaceCellWithPadding(15, false));
        addCell(addPageCell("Officers, Committee Chairs, Board Members", 0, set.getNormalFontSize() + 4, null));
        addCell(PdfCell.verticalSpaceCellWithPadding(15, false));
        addCell(addPageCell("Membership Information", 0, set.getNormalFontSize() + 4, null));
        addCell(PdfCell.verticalSpaceCellWithPadding(15, false));
        addCell(addPageCell("Members - Listed by Number", 0, set.getNormalFontSize() + 4, null));
        addCell(PdfCell.verticalSpaceCellWithPadding(15, false));
        addCell(addPageCell("Wet Slip Chart", 0, set.getNormalFontSize() + 4, null));
        addCell(PdfCell.verticalSpaceCellWithPadding(15, false));
        addCell(addPageCell("Sportsmanship of the Year Award", 0, set.getNormalFontSize() + 4, null));
        addCell(PdfCell.verticalSpaceCellWithPadding(15, false));
        addCell(addPageCell("Past Commodores", 0, set.getNormalFontSize() + 4, null));
        addCell(PdfCell.verticalSpaceCellWithPadding(20, false));
        addAddress();
    }

    private Cell addPageCell(String content, int fixLeadOffset, int fontSize, Color color) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
        Paragraph p = PdfParagraph.paragraphOf(content, fontSize, TextAlignment.CENTER);
        if (fixLeadOffset != 0) p.setFixedLeading(fixLeadOffset);
        if (color != null) p.setFontColor(color);
        cell.add(p);
        return cell;
    }

    private void addAddress() {
        this.addCell(addPageCell("Eagle Creek Sailing Club", 12, set.getNormalFontSize() + 2, null));
        this.addCell(addPageCell("8901 W. 46th Street", 15, set.getNormalFontSize() + 2, null));
        this.addCell(addPageCell("Indianapolis, IN 46234", 15, set.getNormalFontSize() + 2, null));
        this.addCell(addPageCell("Website: ecsail.org", 15, set.getNormalFontSize() + 2, null));
    }
}
