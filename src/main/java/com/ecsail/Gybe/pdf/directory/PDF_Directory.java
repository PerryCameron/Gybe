package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.pdf.enums.Pages;
import com.ecsail.Gybe.pdf.tools.PdfSort;
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
        PDF_Directory.doc = new Document(pdf, new PageSize(calculatePageSize()));
        doc.setLeftMargin(0.5f);
        doc.setRightMargin(0.5f);
        doc.setTopMargin(1f);
        doc.setBottomMargin(0.5f);

        if (model.printCoverPage()) {
            doc.add(new PDF_Cover(model).createPage());
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        if (model.printCommodoreMessagePage()) {
            doc.add(new PDF_CommodoreMessage(model).createPage());
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        if (model.printBoardOfDirectorsPage()) {
            doc.add(new PDF_BoardOfDirectors(model).createPage());
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        if (model.isPrintTableOfContentPage()) {
            doc.add(new PDF_TableOfContents(model).createPage());
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        if (model.printMembershipInformationCoverPage()) {
            doc.add(new PDF_MembershipInfoTitlePage(model).createPage());
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        if (model.printMembershipInformationPages()) {
            PdfSort.sortMembershipsByLastName(model.getMembershipInfoDTOS()); // put them in alphabetical order by last name
            int batchSize = 6; // 6 per page
            PDF_MembershipInfo membershipInfo = new PDF_MembershipInfo(model);
            int count = 0;
            for (int i = 0; i < model.getMembershipInfoDTOS().size(); i += batchSize) {
                // Get the sublist for the current batch
                List<MembershipInfoDTO> batch = model.getMembershipInfoDTOS().subList(i, Math.min(i + batchSize, model.getMembershipInfoDTOS().size()));
                doc.add(membershipInfo.createPage(batch));
                doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                count++;
            }
            if (count % 2 != 0) doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        if(model.printMembershipsByNumberPages()) {
            PdfSort.sortMembershipsByMembershipId(model.getMembershipInfoDTOS());
            PDF_MembersByNumber membersByNumber = new PDF_MembersByNumber(model);
            doc.add(membersByNumber.createPage(1));
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            doc.add(membersByNumber.createPage(2));
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        if(model.printSlipChartPages()) {
            PDF_SlipPage slipPage = new PDF_SlipPage(model);
            doc.add(slipPage.createPage(1));
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            doc.add(slipPage.createPage(2));
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        PDF_ListPage listPage = new PDF_ListPage(model);
        if(model.printSportsmanshipAwardPage()) {
            doc.add(listPage.createPage(Pages.SPORTSMANSHIP_AWARD));
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        }
        if(model.printPastCommodoresPage()) {
            doc.add(listPage.createPage(Pages.PAST_COMMODORES));
            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            doc.close();
        }
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
        return new Rectangle(widthPoints, heightPoints);
    }

    public DirectoryModel getModel() {
        return model;
    }
}
