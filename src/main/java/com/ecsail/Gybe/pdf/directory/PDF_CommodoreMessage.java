package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.ecsail.Gybe.pdf.tools.PdfTable;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

import static com.ecsail.Gybe.statictools.HtmlParser.extractTextBetweenPTags;
// TODO Give ability to make bold and italic by making the text HTML
public class PDF_CommodoreMessage {
	private final DirectoryModel model;
//	private final float salutationTopPadding;
//	private final float messageTopPadding;
//	private final float paragraphPadding;
//	private final float normalFontSize;

	public PDF_CommodoreMessage(DirectoryModel model) {
		this.model = model;
	}

	public Table createMessage() {
		Table table = PdfTable.TableOf(1,HorizontalAlignment.CENTER, model.getMainTableWidth());
		CommodoreMessageDTO commodoreMessage = model.getCommodoreMessage();
		String[] paragraphs = extractTextBetweenPTags(commodoreMessage.getMessage());
		table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getSalutationTopPadding(),false));
		table.addCell(newParagraph(commodoreMessage.getSalutation()));
		table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getMessageTopPadding(),false));
		for (String paragraph : paragraphs) {
			table.addCell(newParagraph(paragraph));
			table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getParagraphPadding(), false));
		}
		table.addCell(newParagraph(commodoreMessage.getCommodore() + "                           "
				+ commodoreMessage.getFiscalYear() + " ECSC Commodore"));
		return table;
	}
	
	private Cell newParagraph(String text) {
		Cell cell = PdfCell.cellOf(Border.NO_BORDER);
		float fontSize = model.getNormalFontSize();
		cell.add(PdfParagraph.paragraphOf(text, fontSize)); //normalFontSize
		return cell;
	}
}
