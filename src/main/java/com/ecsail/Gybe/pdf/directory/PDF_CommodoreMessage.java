package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

import static com.ecsail.Gybe.statictools.HtmlParser.extractTextBetweenPTags;
// TODO Give ability to make bold and italic by making the text HTML
public class PDF_CommodoreMessage extends Table {
	private final PDF_Directory pdfDirectory;

	public PDF_CommodoreMessage(int numColumns, PDF_Directory pdfDirectory) {
		super(numColumns);
		this.pdfDirectory = pdfDirectory;
		CommodoreMessageDTO commodoreMessage = pdfDirectory.getCommodoreMessage();
		String[] paragraphs = extractTextBetweenPTags(commodoreMessage.getMessage());
		setWidth(pdfDirectory.getPageSize().getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		addCell(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("salutationTopPadding"),false));
		addCell(newParagraph(commodoreMessage.getSalutation()));
		addCell(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("messageTopPadding"),false));
		for (String paragraph : paragraphs) {
			addCell(newParagraph(paragraph));
			addCell(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("paragraphPadding"), false));
		}
		addCell(newParagraph(commodoreMessage.getCommodore() + "                           "
				+ commodoreMessage.getFiscalYear() + " ECSC Commodore"));
	}
	
	private Cell newParagraph(String text) {
		Cell cell = PdfCell.cellOf(Border.NO_BORDER);
		float fontSize = pdfDirectory.setting("normalFontSize");
		cell.add(PdfParagraph.paragraphOf(text, fontSize)); //normalFontSize
		return cell;
	}

}
