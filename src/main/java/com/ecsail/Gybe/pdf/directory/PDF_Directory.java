package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.wrappers.DirectoryDataWrapper;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.properties.AreaBreakType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Year;
import java.util.Comparator;
import java.util.List;


public class PDF_Directory {

    public static Logger logger = LoggerFactory.getLogger(PDF_Directory.class);
    static Document doc;
    private final DirectoryModel model;

    public PDF_Directory(DirectoryDataWrapper directoryDataWrapper) {
        this.model = new DirectoryModel(directoryDataWrapper);
        model.setPageSize(calculatePageSize());
        model.setFont(constructFontHeading(model.getFontName()));
        PdfWriter writer = getPdfWriter();
        // Initialize PDF document
        assert writer != null;
        PdfDocument pdf = new PdfDocument(writer);
        //PageSize A5v = new PageSize(PageSize.A5.getWidth(), PageSize.A5.getHeight());
        PDF_Directory.doc = new Document(pdf, new PageSize(calculatePageSize()));
        doc.setLeftMargin(0.5f);
        doc.setRightMargin(0.5f);
        doc.setTopMargin(1f);
        doc.setBottomMargin(0.5f);

        doc.add(new PDF_Cover(model).createCover());
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        doc.add(new PDF_CommodoreMessage(model).createMessage());
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        doc.add(new PDF_BoardOfDirectors( model).createBodPage());
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

		doc.add(new PDF_TableOfContents(model).createTocPage());
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        doc.add(new PDF_MembershipInfoTitlePage(model).createTitlePage());
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        sortMemberships(); // put them in alphabetical order by last name
        int batchSize = 6; // 6 per page
        PDF_MembershipInfo membershipInfo = new PDF_MembershipInfo(model);
        for (int i = 0; i < model.getMembershipInfoDTOS().size(); i += batchSize) {
            // Get the sublist for the current batch
            List<MembershipInfoDTO> batch = model.getMembershipInfoDTOS().subList(i, Math.min(i + batchSize, model.getMembershipInfoDTOS().size()));
            doc.add(membershipInfo.createPage(batch));
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }

//
//				new PDF_MembersByNumber(set, doc, rosters);
//
//				doc.add(new PDF_SlipPageL(2, set));
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//				textArea.appendText("Created D and A dock page\n");
//
//				doc.add(new PDF_SlipPageR(2, set));
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//				textArea.appendText("Created B and C dock page\n");
//
//				doc.add(new PDF_SportsmanAward(2, set));
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//				textArea.appendText("Created sportsman award page\n");
//
//				doc.add(new PDF_CommodoreList(2, set));
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//				textArea.appendText("Created directory page\n");
        doc.close();

        logger.info("destination=" + System.getProperty("user.home") + "/" + Year.now() + "_ECSC_directory.pdf");
    }


    private static PdfWriter getPdfWriter() {
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(System.getProperty("user.home") + "/" + Year.now() + "_ECSC_directory.pdf");
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return writer;
    }

    private void  sortMemberships() {
        Comparator<MembershipInfoDTO> comparator = (m1, m2) -> {
            String lastName1 = m1.getPeople().stream()
                    .filter(person -> person.getMemberType() == 1)
                    .map(PersonDTO::getLastName)
                    .findFirst()
                    .orElse("");

            String lastName2 = m2.getPeople().stream()
                    .filter(person -> person.getMemberType() == 1)
                    .map(PersonDTO::getLastName)
                    .findFirst()
                    .orElse("");

            return lastName1.compareTo(lastName2);
        };
        model.getMembershipInfoDTOS().sort(comparator);
    }

    protected PdfFont constructFontHeading(String font) {
        PdfFont pdfFont = null;
        try {
            FontProgram fontProgram = FontProgramFactory.createFont(model.getFontPath() + font);
            pdfFont = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return pdfFont;
    }

    private Rectangle calculatePageSize() {
        float widthPoints = 72 * model.getWidth();
        float heightPoints = 72 * model.getHeight();
        Rectangle sheet = new Rectangle(widthPoints, heightPoints);
        return sheet;
    }

    public DirectoryModel getModel() {
        return model;
    }
}
