package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.ecsail.Gybe.pdf.tools.PdfTable;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PDF_Cover {
	private final DirectoryModel model;

//	private final String logoPath;
//	private final float logoTopPadding;
//	private final float titleTopPadding;
//	private final float titleFontSize;
//	private final float titleFixedLeading;

	public PDF_Cover(DirectoryModel model) {
		this.model = model;
	}

	public Table createCover() {
		Table table = PdfTable.TableOf(1,HorizontalAlignment.CENTER,model.getMainTableWidth());
		Image logoImage = getLogoImage(System.getProperty("user.home") + model.getLogoPath());
		logoImage.scaleToFit(table.getWidth().getValue(), table.getWidth().getValue());
		logoImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
		table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getLogoTopPadding(), false));
		Cell cell = PdfCell.cellOf(Border.NO_BORDER, VerticalAlignment.MIDDLE);
		cell.add(logoImage);
		table.addCell(cell);
		table.addCell(PdfCell.verticalSpaceCellWithPadding(model.getTitleTopPadding(), false));
		table.addCell(addTitle("Membership"));
		table.addCell(addTitle("Directory"));
		return table;
	}

	private Image getLogoImage(String imagePath) {
		Image logoImage = null;
		try (InputStream in = new FileInputStream(imagePath)) {
			logoImage = new Image(ImageDataFactory.create(toByteArray(in)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return logoImage;
	}
	
	   private static byte[] toByteArray(InputStream in) throws IOException {
	          //InputStream is = new BufferedInputStream(System.in);
	          ByteArrayOutputStream os = new ByteArrayOutputStream();
	          byte [] buffer = new byte[1024];
	          int len;
	          // read bytes from the input stream and store them in buffer
	            while ((len = in.read(buffer)) != -1) {
	                // write bytes from the buffer into output stream
	                os.write(buffer, 0, len);
	            }
	            return os.toByteArray();
	       }
		
		private Cell addTitle(String heading) {
			Cell cell = PdfCell.cellOf(Border.NO_BORDER,HorizontalAlignment.CENTER,VerticalAlignment.MIDDLE);
			Paragraph paragraph = PdfParagraph.paragraphOfA(heading,model.getTitleFontSize(), model.getFont(),
					model.getTitleFixedLeading(), TextAlignment.CENTER);
			cell.add(paragraph);
			return cell;
		}
}
