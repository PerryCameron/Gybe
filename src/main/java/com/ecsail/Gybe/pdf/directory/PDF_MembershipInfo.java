package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.EmailDTO;
import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PhoneDTO;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.util.List;

public class PDF_MembershipInfo {

    private final PDF_Directory pdfDirectory;
    private final float normalFontSize;
    private final float fixedLeadingNarrow;
    private final DeviceCmyk mipHeaderColor;
    private final DeviceCmyk mipEmailColor;
    private float tableWidth;

    public PDF_MembershipInfo(PDF_Directory pdfDirectory) {
        this.pdfDirectory = pdfDirectory;
        this.normalFontSize = pdfDirectory.setting("normalFontSize");
        this.fixedLeadingNarrow = pdfDirectory.setting("fixedLeadingNarrow");
        this.mipHeaderColor = pdfDirectory.setting("mipHeaderColor");
        this.mipEmailColor = pdfDirectory.setting("mipEmailColor");
    }

    public Table createPage(List<MembershipInfoDTO> batch, int numberOfColumns) {
        Table table = new Table(numberOfColumns);
        this.tableWidth = pdfDirectory.getPageSize().getWidth() * 0.9f;
        table.setWidth(tableWidth);  // makes table 90% of page width
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        for (MembershipInfoDTO membership : batch) {
            table.addCell(createHeadingCell("#" + membership.getMembershipId() + "  Type: " + membership.getMemType()
                    + "    " + getSlipNumber(membership)));
            table.addCell(createHeadingCell(getPhone(membership, 1, "E")));
            table.addCell(createTextCell(getPerson(membership, 1)));
            table.addCell(createTextCell(getPerson(membership, 2)));
            String primaryPhone = getPhone(membership, 1, "C");
            String secondaryPhone = getPhone(membership, 2, "C");
            if ((primaryPhone != null && !primaryPhone.trim().isEmpty()) || (secondaryPhone != null && !secondaryPhone.trim().isEmpty())) {
                table.addCell(createTextCell(primaryPhone));
                table.addCell(createTextCell(secondaryPhone));
            }
            String primaryEmail = getEmail(membership, 1);
            String secondaryEmail = getEmail(membership, 2);
            if ((primaryEmail != null && !primaryEmail.trim().isEmpty()) || (secondaryEmail != null && !secondaryEmail.trim().isEmpty())) {
                table.addCell(createEmailCell(primaryEmail));
                table.addCell(createEmailCell(secondaryEmail));
            }
            table.addCell(createDoubleTextCell(membership.getAddress() + " " + membership.getCity() + " " + membership.getState() + " " + membership.getZip()));
            table.addCell(createDoubleTextCell("Boats---------------------------------------------------------------"));
            table.addCell(createDoubleTextCell("Children------------------------------------------------------------"));
        }
        return table;
    }

    private Cell createHeadingCell(String text) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE,
                new SolidBorder(0.5f), pdfDirectory.getMainColor(), tableWidth * 0.5f);
        cell.add(PdfParagraph.paragraphOf(text, getNormalFontSize(), getFixedLeadingNarrow(), getMipHeaderColor()));
        return cell;
    }

    private Cell createTextCell(String text) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(PdfParagraph.paragraphOf(text, getNormalFontSize(), getFixedLeadingNarrow()));
        return cell;
    }

    private Cell createDoubleTextCell(String text) {
        Cell cell = PdfCell.cellOf(1, 2, Border.NO_BORDER);
        cell.add(PdfParagraph.paragraphOf(text, getNormalFontSize(), getFixedLeadingNarrow()));
        return cell;
    }

    private Cell createEmailCell(String text) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(PdfParagraph.paragraphOf(text, getNormalFontSize(), getFixedLeadingNarrow(), getMipEmailColor()));
        return cell;
    }

    private String getEmail(MembershipInfoDTO membership, int memberType) {
        for (PersonDTO person : membership.getPeople()) {
            if (person.getMemberType() == memberType) {
                if (person.getEmails() != null) {
                    for (EmailDTO email : person.getEmails()) {
                        if (email.getPrimaryUse() == true && email.getEmailListed() == true) {
                            return email.getEmail();
                        } else {
                            PDF_Directory.logger.info(person.getFullName() + "'s email is set to not listed or is not the primary");
                        }
                    }
                }
            }
        }
        return "";
    }

    private String getPhone(MembershipInfoDTO membership, int memberType, String phoneType) {
        for (PersonDTO person : membership.getPeople()) {
            if (person.getMemberType() == memberType) {
                if (person.getPhones() != null) {
                    for (PhoneDTO phone : person.getPhones()) {
                        if (phone.getPhoneType().equals(phoneType)) {
                            if (phone.getPhoneType().equals("E")) return "Emergency: " + phone.getPhone();
                            else if (phone.isPhoneListed() == true) return phone.getPhone();
                            else PDF_Directory.logger.info(person.getFullName() + "'s phone " + phone.getPhone()
                                        + " is set to not listed");
                        }
                    }
                }
            }
        }
        return "";
    }

    private String getPerson(MembershipInfoDTO membership, int type) {
        for (PersonDTO person : membership.getPeople()) {
            if (person.getMemberType() == type && person.isActive() == true)
                return person.getReversedFullName();
        }
        return "";
    }

    private String getSlipNumber(MembershipInfoDTO membership) {
        if (membership.getSlip().getSlipNum() != null) return membership.getSlip().getSlipNum();
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

    public DeviceCmyk getMipEmailColor() {
        return mipEmailColor;
    }
}
