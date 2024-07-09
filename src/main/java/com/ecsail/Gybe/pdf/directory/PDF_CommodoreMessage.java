package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

import static com.ecsail.Gybe.statictools.HtmlParser.extractTextBetweenPTags;
// TODO Give ability to make bold and italic by making the text HTML
public class PDF_CommodoreMessage extends Table {
	PDF_Object_Settings set;
	public PDF_CommodoreMessage(int numColumns, PDF_Object_Settings set, CommodoreMessageDTO commodoreMessage) {
		super(numColumns);
		this.set = set;
		String[] paragraphs = extractTextBetweenPTags(commodoreMessage.getMessage());
		setWidth(set.getPageSize().getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);
		addCell(PdfCell.verticalSpaceCell(2));
		addCell(newParagraph(commodoreMessage.getSalutation()));
		addCell(PdfCell.verticalSpaceCell(1));
		for (String paragraph : paragraphs) {
			addCell(newParagraph(paragraph));
			addCell(PdfCell.verticalSpaceCell(1));
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
