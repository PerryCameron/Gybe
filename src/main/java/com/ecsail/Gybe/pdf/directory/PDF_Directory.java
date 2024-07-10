package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.BoardPositionDTO;
import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.properties.AreaBreakType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class PDF_Directory {

    public static Logger logger = LoggerFactory.getLogger(PDF_Directory.class);
    private final ArrayList<MembershipInfoDTO> membershipInfoDTOS;
    private final ArrayList<BoardPositionDTO> positionData;
    private final CommodoreMessageDTO commodoreMessage;
    PDF_Object_Settings set;
    static Document doc;

    public PDF_Directory(ArrayList<MembershipInfoDTO> membershipInfoDTOS, CommodoreMessageDTO commodoreMessage, ArrayList<BoardPositionDTO> positionData) {
        this.membershipInfoDTOS = membershipInfoDTOS;
        this.positionData = positionData;
        this.commodoreMessage = commodoreMessage;
        set = new PDF_Object_Settings(Year.now());
        PdfWriter writer = getPdfWriter();
        // Initialize PDF document
        assert writer != null;
        PdfDocument pdf = new PdfDocument(writer);
        //PageSize A5v = new PageSize(PageSize.A5.getWidth(), PageSize.A5.getHeight());
        PDF_Directory.doc = new Document(pdf, new PageSize(set.getPageSize()));
        doc.setLeftMargin(0.5f);
        doc.setRightMargin(0.5f);
        doc.setTopMargin(1f);
        doc.setBottomMargin(0.5f);

        doc.add(new PDF_Cover(1, set));
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        doc.add(new PDF_CommodoreMessage(1, this));
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        doc.add(new PDF_BoardOfDirectors(1, this));
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

		doc.add(new PDF_TableOfContents(1, set));
		doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        doc.add(new PDF_ChapterPage(1, "Membership Information", set));
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        sortMemberships(); // put them in alphabetical order by last name
        int batchSize = 6; // 6 per page
        PDF_MembershipInfo membershipInfo = new PDF_MembershipInfo(1, this);
        for (int i = 0; i < membershipInfoDTOS.size(); i += batchSize) {
            // Get the sublist for the current batch
            List<MembershipInfoDTO> batch = membershipInfoDTOS.subList(i, Math.min(i + batchSize, membershipInfoDTOS.size()));
            doc.add(membershipInfo.createPage(batch));
        }



//        membershipInfoDTOS
//        doc.add(new P);

//
////				createMemberInfoPages(doc);  // creates info pages
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
////				 this one below added in if book needs an extra page (should be even number of pages)
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
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
        membershipInfoDTOS.sort(comparator);
    }
    public ArrayList<MembershipInfoDTO> getMembershipInfoDTOS() {
        return membershipInfoDTOS;
    }

    public PDF_Object_Settings getSet() {
        return set;
    }

    public void setSet(PDF_Object_Settings set) {
        this.set = set;
    }

    public ArrayList<BoardPositionDTO> getPositionData() {
        return positionData;
    }

    public CommodoreMessageDTO getCommodoreMessage() {
        return commodoreMessage;
    }
}
