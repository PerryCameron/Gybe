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
	private final CommodoreMessageDTO commodoreMessage;
	PDF_Object_Settings set;
	public PDF_CommodoreMessage(int numColumns, PDF_Directory pdfDirectory) {
		super(numColumns);
		this.set = pdfDirectory.getSet();
		this.commodoreMessage = pdfDirectory.getCommodoreMessage();
		String[] paragraphs = extractTextBetweenPTags(commodoreMessage.getMessage());
		setWidth(set.getPageSize().getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		addCell(PdfCell.verticalSpaceCellWithPadding(10,false));
		addCell(newParagraph(commodoreMessage.getSalutation()));
		addCell(PdfCell.verticalSpaceCellWithPadding(5,false));
		for (String paragraph : paragraphs) {
			addCell(newParagraph(paragraph));
			addCell(PdfCell.verticalSpaceCellWithPadding(5, false));
		}
		addCell(newParagraph(commodoreMessage.getCommodore() + "                           "
				+ commodoreMessage.getFiscalYear() + " ECSC Commodore"));
	}
	
	private Cell newParagraph(String text) {
		Cell cell = PdfCell.cellOf(Border.NO_BORDER);
		cell.add(PdfParagraph.paragraphOf(text, set.getNormalFontSize()));
		return cell;
	}

}
