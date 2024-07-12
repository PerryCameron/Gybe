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


public class PDF_ChapterPage extends Table {

	PDF_Object_Settings set;
	public PDF_ChapterPage(int numColumns, String chapterText, PDF_Directory pdfDirectory) {
		super(numColumns);
		this.set = pdfDirectory.getSet();
		setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);

		addCell(PdfCell.verticalSpaceCellWithPadding(90, false));
		Cell cell = PdfCell.cellOf(Border.NO_BORDER,VerticalAlignment.MIDDLE);
		Paragraph paragraph = PdfParagraph.paragraphOf(chapterText, set.getNormalFontSize() + 18,
				set.getColumnHead(),set.getMainColor(),TextAlignment.CENTER);
		cell.add(paragraph);
		addCell(cell);

		addCell(PdfCell.verticalSpaceCellWithPadding(40, false));
		cell= PdfCell.cellOf(Border.NO_BORDER,VerticalAlignment.MIDDLE);
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String formattedDate = today.format(formatter);
		String line1 = "Includes paid members as of " + formattedDate;
		cell.add(PdfParagraph.paragraphOf(line1, pdfDirectory.setting("normalFontSize"), TextAlignment.CENTER));
		addCell(cell);

		cell = PdfCell.cellOf(Border.NO_BORDER,VerticalAlignment.MIDDLE);
		String line2 = ("Please notify the ECSC Membership Chair of any updates: ");
		cell.add(PdfParagraph.paragraphOf(line2, pdfDirectory.setting("normalFontSize"), TextAlignment.CENTER));
		addCell(cell);

		cell = PdfCell.cellOf(Border.NO_BORDER,VerticalAlignment.MIDDLE);
		paragraph = PdfParagraph.paragraphOf("membership@ecsail.org", pdfDirectory.setting("normalFontSize"), TextAlignment.CENTER);
		paragraph.setFontColor(ColorConstants.BLUE);
		cell.add(paragraph);
		addCell(cell);
	}
}
