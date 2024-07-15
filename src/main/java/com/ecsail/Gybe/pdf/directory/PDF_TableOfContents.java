package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.ecsail.Gybe.pdf.tools.PdfTable;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

public class PDF_TableOfContents {
    private final DirectoryModel model;

    public PDF_TableOfContents(DirectoryModel model) {
        this.model = model;
    }

    public Table createPage() {
        Table table = PdfTable.TableOf(1, HorizontalAlignment.CENTER, model.getMainTableWidth());
        ////////////////// Table Properties //////////////////
        float cellPadding = model.getTocChapterPadding();
        ///////////////// Cells ////////////////////////////
        table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getTocTopMarginPadding(), false));
        table.addCell(PdfCell.addPageCell(1,"Membership Directory", 0, model.getTocTitleFontSize(), model.getMainColor()));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(PdfCell.addPageCell(1,"Commodore Greeting", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(PdfCell.addPageCell(1,"Officers, Committee Chairs, Board Members", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(PdfCell.addPageCell(1,"Membership Information", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(PdfCell.addPageCell(1,"Members - Listed by Number", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(PdfCell.addPageCell(1,"Wet Slip Chart", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(PdfCell.addPageCell(1,"Sportsmanship of the Year Award", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(PdfCell.addPageCell(1,"Past Commodores", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getTocAddressPadding(), false));
        table.addCell(PdfCell.addPageCell(1,"Eagle Creek Sailing Club", model.getTocTitleFixedLeading(), model.getTocAddressFontSize(), null));
        table.addCell(PdfCell.addPageCell(1,"8901 W. 46th Street", model.getTocTextFixedLeading(), model.getTocAddressFontSize(), null));
        table.addCell(PdfCell.addPageCell(1,"Indianapolis, IN 46234", model.getTocTextFixedLeading(), model.getTocAddressFontSize(), null));
        table.addCell(PdfCell.addPageCell(1,"Website: ecsail.org", model.getTocTextFixedLeading(), model.getTocAddressFontSize(), null));
        return table;
    }
}
