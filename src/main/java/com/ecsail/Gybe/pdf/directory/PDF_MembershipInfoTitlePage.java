package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
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


public class PDF_MembershipInfoTitlePage extends Table {


    public PDF_MembershipInfoTitlePage(int numColumns, String chapterText, PDF_Directory pdfDirectory) {
        super(numColumns);
        float paddingTop = pdfDirectory.setting("membershipInfoTitlePaddingTop");
        float titleFontSize = pdfDirectory.setting("membershipInformationTitleFontSize");
        float titlePaddingBottom = pdfDirectory.setting("membershipInfoTitlePaddingBottom");
        float normalFontSize = pdfDirectory.setting("normalFontSize");
        String membershipEmail = pdfDirectory.setting("membershipEmail");
        setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
        setHorizontalAlignment(HorizontalAlignment.CENTER);

        addCell(PdfCell.verticalSpaceCellWithPadding(paddingTop, false));
        Cell cell = PdfCell.cellOf(Border.NO_BORDER, VerticalAlignment.MIDDLE);
        Paragraph paragraph = PdfParagraph.paragraphOf(chapterText, titleFontSize,
                pdfDirectory.getFont(), pdfDirectory.getMainColor(), TextAlignment.CENTER);
        cell.add(paragraph);
        addCell(cell);

        addCell(PdfCell.verticalSpaceCellWithPadding(titlePaddingBottom, false));
        cell = PdfCell.cellOf(Border.NO_BORDER, VerticalAlignment.MIDDLE);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = today.format(formatter);
        String line1 = "Includes paid members as of " + formattedDate;
        cell.add(PdfParagraph.paragraphOf(line1, normalFontSize, TextAlignment.CENTER));
        addCell(cell);

        cell = PdfCell.cellOf(Border.NO_BORDER, VerticalAlignment.MIDDLE);
        String line2 = ("Please notify the ECSC Membership Chair of any updates: ");
        cell.add(PdfParagraph.paragraphOf(line2, normalFontSize, TextAlignment.CENTER));
        addCell(cell);

        cell = PdfCell.cellOf(Border.NO_BORDER, VerticalAlignment.MIDDLE);
        paragraph = PdfParagraph.paragraphOf(membershipEmail, normalFontSize, TextAlignment.CENTER);
        paragraph.setFontColor(ColorConstants.BLUE);
        cell.add(paragraph);
        addCell(cell);
    }
}
