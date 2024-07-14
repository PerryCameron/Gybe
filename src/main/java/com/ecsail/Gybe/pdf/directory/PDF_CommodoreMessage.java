package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.ecsail.Gybe.pdf.tools.PdfTable;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

import static com.ecsail.Gybe.statictools.HtmlParser.extractTextBetweenPTags;
// TODO Give ability to make bold and italic by making the text HTML
public class PDF_CommodoreMessage {
	private final PDF_Directory pdfDirectory;
	private final float salutationTopPadding;
	private final float messageTopPadding;
	private final float paragraphPadding;
	private final float normalFontSize;

	public PDF_CommodoreMessage(PDF_Directory pdfDirectory) {
		this.pdfDirectory = pdfDirectory;
		this.salutationTopPadding = pdfDirectory.setting("salutationTopPadding");
		this.messageTopPadding = pdfDirectory.setting("messageTopPadding");
		this.paragraphPadding = pdfDirectory.setting("paragraphPadding");
		this.normalFontSize = pdfDirectory.setting("normalFontSize");
	}

	public Table createMessage(int numColumns) {
		Table table = PdfTable.TableOf(numColumns,HorizontalAlignment.CENTER,pdfDirectory.getPageSize().getWidth() * 0.9f);
		CommodoreMessageDTO commodoreMessage = pdfDirectory.getCommodoreMessage();
		String[] paragraphs = extractTextBetweenPTags(commodoreMessage.getMessage());
		table.addCell(PdfCell.verticalSpaceCellWithPadding(salutationTopPadding,false));
		table.addCell(newParagraph(commodoreMessage.getSalutation()));
		table.addCell(PdfCell.verticalSpaceCellWithPadding(messageTopPadding,false));
		for (String paragraph : paragraphs) {
			table.addCell(newParagraph(paragraph));
			table.addCell(PdfCell.verticalSpaceCellWithPadding(paragraphPadding, false));
		}
		table.addCell(newParagraph(commodoreMessage.getCommodore() + "                           "
				+ commodoreMessage.getFiscalYear() + " ECSC Commodore"));
		return table;
	}
	
	private Cell newParagraph(String text) {
		Cell cell = PdfCell.cellOf(Border.NO_BORDER);
		float fontSize = normalFontSize;
		cell.add(PdfParagraph.paragraphOf(text, fontSize)); //normalFontSize
		return cell;
	}
}
