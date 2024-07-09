package com.ecsail.Gybe.pdf.directory;


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


public class PDF_Directory {

    public static Logger logger = LoggerFactory.getLogger(PDF_Directory.class);
    private final ArrayList<MembershipInfoDTO> membershipInfoDTOS;
    private final ArrayList<BoardPositionDTO> positionData;
    PDF_Object_Settings set;
    static Document doc;


    public PDF_Directory(ArrayList<MembershipInfoDTO> membershipInfoDTOS, CommodoreMessageDTO commodoreMessage, ArrayList<BoardPositionDTO> positionData) {
        this.membershipInfoDTOS = membershipInfoDTOS;
        this.positionData = positionData;
        set = new PDF_Object_Settings(Year.now());


//		this.rosters = (ArrayList<MembershipListDTO>) membershipRepository.getRoster(year, true);
//		HalyardPaths.checkPath(HalyardPaths.DIRECTORIES);

        PdfWriter writer = null;
        try {
            writer = new PdfWriter(System.getProperty("user.home") + "/" + Year.now() + "_ECSC_directory.pdf");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Initialize PDF document
        assert writer != null;
        PdfDocument pdf = new PdfDocument(writer);
        //PageSize A5v = new PageSize(PageSize.A5.getWidth(), PageSize.A5.getHeight());
        PDF_Directory.doc = new Document(pdf, new PageSize(set.getPageSize()));
        doc.setLeftMargin(0.5f);
        doc.setRightMargin(0.5f);
        doc.setTopMargin(1f);
        doc.setBottomMargin(0.5f);

//		rosters.sort(Comparator.comparing(MembershipListDTO::getLastName));

        doc.add(new PDF_Cover(1, set));
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        doc.add(new PDF_CommodoreMessage(1, set, commodoreMessage));
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//
        doc.add(new PDF_BoardOfDirectors(1, this));
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//
//				doc.add(new PDF_TableOfContents(1, set));
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//				textArea.appendText("Created Table of Contents\n");
//
//				doc.add(new PDF_ChapterPage(1, "Membership Information", set));
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//				textArea.appendText("Created Membership Information Chapter Page\n");
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
//				File file = new File(System.getProperty("user.home") + "/" + Year.now() + "_ECSC_directory.pdf");

        // Open the document
//				try {
//					desktop.open(file);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return "Directory Successfully Created!";
//			}
//		};
//	    task.setOnScheduled(e -> System.out.println("scheduled"));
//	    task.setOnSucceeded(e -> {
//	    	textArea.setText((String) e.getSource().getValue());
//	    	logger.info("Finished making directory");});
//	    task.setOnFailed(e -> System.out.println("This failed" + e.getSource().getMessage()));
//	    exec.execute(task);

    }


//	private void createMemberInfoPages(Document doc) {
//			int count = 0;
//			doc.add(new Paragraph("\n"));
//			for(MembershipListDTO l: rosters) {
//			textArea.appendText("Creating entry for " + l.getFirstName() + " " + l.getLastName() + "\n");
//			doc.add(new PDF_MemberShipInformation(2,l,set));
//			count++;
//			if(count % 6 == 0) {
//				doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
//				textArea.appendText("<----New Page---->");
//				if(count < rosters.size()) // prevents adding a return for after this section
//					doc.add(new Paragraph("\n")); // I think this is screwing up
//			}
//			//if(count == 60) break;  // this reduces pages made for testing
//		}
//	}


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
}
