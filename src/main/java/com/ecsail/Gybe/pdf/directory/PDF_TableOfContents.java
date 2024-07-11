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
    private final PDF_Directory pdfDirectory;

    public PDF_TableOfContents(int numColumns, PDF_Directory pdfDirectory) {
        super(numColumns);
        this.pdfDirectory = pdfDirectory;
        ////////////////// Table Properties //////////////////
        setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
        setHorizontalAlignment(HorizontalAlignment.CENTER);

        float cellPadding = pdfDirectory.setting("tocChapterPadding");
        ///////////////// Cells ////////////////////////////
        addCell(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("tocTopMarginPadding"), false));
        addCell(addPageCell("Membership Directory", 0, pdfDirectory.setting("tocTitleFontSize"), pdfDirectory.getMainColor()));
        addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        addCell(addPageCell("Commodore Greeting", 0, pdfDirectory.setting("tocChapterFontSize"), null));
        addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        addCell(addPageCell("Officers, Committee Chairs, Board Members", 0, pdfDirectory.setting("tocChapterFontSize"), null));
        addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        addCell(addPageCell("Membership Information", 0, pdfDirectory.setting("tocChapterFontSize"), null));
        addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        addCell(addPageCell("Members - Listed by Number", 0, pdfDirectory.setting("tocChapterFontSize"), null));
        addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        addCell(addPageCell("Wet Slip Chart", 0, pdfDirectory.setting("tocChapterFontSize"), null));
        addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        addCell(addPageCell("Sportsmanship of the Year Award", 0, pdfDirectory.setting("tocChapterFontSize"), null));
        addCell(PdfCell.verticalSpaceCellWithPadding(cellPadding, false));
        addCell(addPageCell("Past Commodores", 0, pdfDirectory.setting("tocChapterFontSize"), null));
        addCell(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("tocAddressPadding"), false));
        addAddress();
    }

    private Cell addPageCell(String content, int fixLeadOffset, int fontSize, Color color) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
        Paragraph paragraph = PdfParagraph.paragraphOf(content, fontSize, TextAlignment.CENTER);
        if (fixLeadOffset != 0) paragraph.setFixedLeading(fixLeadOffset);
        if (color != null) paragraph.setFontColor(color);
        cell.add(paragraph);
        return cell;
    }

    private void addAddress() {
        this.addCell(addPageCell("Eagle Creek Sailing Club", 12, pdfDirectory.setting("tocAddressFontSize"), null));
        this.addCell(addPageCell("8901 W. 46th Street", 15, pdfDirectory.setting("tocAddressFontSize"), null));
        this.addCell(addPageCell("Indianapolis, IN 46234", 15, pdfDirectory.setting("tocAddressFontSize"), null));
        this.addCell(addPageCell("Website: ecsail.org", 15, pdfDirectory.setting("tocAddressFontSize"), null));
    }
}
