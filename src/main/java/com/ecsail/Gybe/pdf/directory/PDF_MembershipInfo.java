package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PhoneDTO;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.util.List;

public class PDF_MembershipInfo {


    private final PDF_Directory pdfDirectory;
    private final float normalFontSize;
    private final float fixedLeadingNarrow;
    private final DeviceCmyk mipHeaderColor;
    private float tableWidth;

    public PDF_MembershipInfo(PDF_Directory pdfDirectory) {

        this.pdfDirectory = pdfDirectory;
        this.normalFontSize = pdfDirectory.setting("normalFontSize");
        this.fixedLeadingNarrow = pdfDirectory.setting("fixedLeadingNarrow");
        this.mipHeaderColor = pdfDirectory.setting("mipHeaderColor");
    }

    public Table createPage(List<MembershipInfoDTO> batch, int numberOfColumns) {
        Table table = new Table(numberOfColumns);
        this.tableWidth = pdfDirectory.getPageSize().getWidth() * 0.9f;
        table.setWidth(tableWidth);  // makes table 90% of page width
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // add the header
        for (MembershipInfoDTO membership : batch) {
            Cell[] cell = createMembershipHeading(membership);
            table.addCell(cell[0]);
            table.addCell(cell[1]);
        }
        return table;
    }

    private Cell[] createMembershipHeading(MembershipInfoDTO membership) {
        Cell[] cell = new Cell[2];
        String text = "#" + membership.getMembershipId() + "  Type: " + membership.getMemType()
                + "    " + getSlipNumber(membership);
        Paragraph paragraph = PdfParagraph.paragraphOf(text,getNormalFontSize(),getFixedLeadingNarrow());
        paragraph.setFontColor(getMipHeaderColor());
        cell[0] = new Cell();
        cell[0].setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(0.5f))
                .setBackgroundColor(pdfDirectory.getMainColor()).setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setWidth(tableWidth * 0.5f).setHorizontalAlignment(HorizontalAlignment.CENTER).add(paragraph);

        paragraph = new Paragraph("Emergency: " + getEmergencyPhone(membership));
        paragraph.setFontColor(getMipHeaderColor());
        paragraph.setFontSize(getNormalFontSize()).setFixedLeading(getFixedLeadingNarrow());
        cell[1] = new Cell();
        cell[1].setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(0.5f))
                .setBackgroundColor(pdfDirectory.getMainColor()).setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setWidth(tableWidth * 0.5f).setHorizontalAlignment(HorizontalAlignment.CENTER).add(paragraph);
        return cell;
    }

    private String getEmergencyPhone(MembershipInfoDTO membership) {
        for(PersonDTO person: membership.getPeople()) {
            if(person.getPhones() != null)
            for(PhoneDTO phone: person.getPhones()) if(phone.getPhoneType().equals("E"))
                return phone.getPhone();
        }
        return "";
    }

    private String getSlipNumber(MembershipInfoDTO membership) {
        if(membership.getSlip().getSlipNum() != null) return membership.getSlip().getSlipNum();
        return "";
    }

    public float getNormalFontSize() {
        return normalFontSize;
    }

    public float getFixedLeadingNarrow() {
        return fixedLeadingNarrow;
    }

    public DeviceCmyk getMipHeaderColor() {
        return mipHeaderColor;
    }
}
