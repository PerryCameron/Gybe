package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.ecsail.Gybe.pdf.tools.PdfTable;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.PageSize;
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

    public Table createTocPage() {
        Table table = PdfTable.TableOf(1, HorizontalAlignment.CENTER, model.getMainTableWidth());
        ////////////////// Table Properties //////////////////
        float cellPadding = model.getTocChapterPadding();
        ///////////////// Cells ////////////////////////////
        table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getTocTopMarginPadding(), false));
        table.addCell(addPageCell("Membership Directory", 0, model.getTocTitleFontSize(), model.getMainColor()));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(addPageCell("Commodore Greeting", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(addPageCell("Officers, Committee Chairs, Board Members", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(addPageCell("Membership Information", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(addPageCell("Members - Listed by Number", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(addPageCell("Wet Slip Chart", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(addPageCell("Sportsmanship of the Year Award", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        table.addCell(addPageCell("Past Commodores", 0, model.getTocChapterFontSize(), null));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getTocAddressPadding(), false));
        table.addCell(addPageCell("Eagle Creek Sailing Club", 12, model.getTocAddressFontSize(), null));
        table.addCell(addPageCell("8901 W. 46th Street", 15, model.getTocAddressFontSize(), null));
        table.addCell(addPageCell("Indianapolis, IN 46234", 15, model.getTocAddressFontSize(), null));
        table.addCell(addPageCell("Website: ecsail.org", 15, model.getTocAddressFontSize(), null));
        return table;
    }

    private Cell addPageCell(String content, float fixLeadOffset, float fontSize, Color color) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
        Paragraph paragraph = PdfParagraph.paragraphOf(content, fontSize, TextAlignment.CENTER);
        if (fixLeadOffset != 0) paragraph.setFixedLeading(fixLeadOffset);
        if (color != null) paragraph.setFontColor(color);
        cell.add(paragraph);
        return cell;
    }
}
