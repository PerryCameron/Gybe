package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.pdf.tools.PdfCell;
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

public class PDF_Cover extends Table {


	private final PDF_Directory pdfDirectory;

	public PDF_Cover(int numColumns, PDF_Directory pdfDirectory) {
		super(numColumns);
		this.pdfDirectory = pdfDirectory;
		String logoPath = pdfDirectory.setting("logoPath");

		////////////////// Table Properties //////////////////
		setWidth(PageSize.A5.getWidth() * 0.9f);  // makes table 90% of page width
		setHorizontalAlignment(HorizontalAlignment.CENTER);


		Image logoImage = getLogoImage(System.getProperty("user.home") + logoPath);
		logoImage.scaleToFit(this.getWidth().getValue(), this.getWidth().getValue());
		logoImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
		
		addCell(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("logoTopPadding"), false));
		Cell cell;
		cell = new Cell();
		//cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setBorder(Border.NO_BORDER);
		cell.add(logoImage);
		
		addCell(cell);
		addCell(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("titleTopPadding"), false));
		addCell(addTitle("Membership"));
		addCell(addTitle("Directory"));
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
			Cell cell = new Cell();
			Paragraph p;
			cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
			cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			cell.setBorder(Border.NO_BORDER);
			p = new Paragraph(heading);
			p.setFontSize(pdfDirectory.setting("titleFontSize"));
			// TODO add font ability + bold
//			p.setFont(set.getColumnHead());
			p.setFixedLeading(pdfDirectory.setting("titleFixedLeading"));  // sets spacing between lines of text
			p.setTextAlignment(TextAlignment.CENTER);
			cell.add(p);
			return cell;
		}
		

}
