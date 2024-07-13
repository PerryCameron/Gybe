package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.enums.MemberType;
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
    private final float mipTopPadding;

    public PDF_MembershipInfo(PDF_Directory pdfDirectory) {
        this.pdfDirectory = pdfDirectory;
        this.normalFontSize = pdfDirectory.setting("normalFontSize");
        this.fixedLeadingNarrow = pdfDirectory.setting("fixedLeadingNarrow");
        this.mipHeaderColor = pdfDirectory.setting("mipHeaderColor");
        this.mipEmailColor = pdfDirectory.setting("mipEmailColor");
        this.mipTopPadding = pdfDirectory.setting("mipTopPadding");
    }

    public Table createPage(List<MembershipInfoDTO> batch, int numberOfColumns) {
        Table table = new Table(numberOfColumns);
        this.tableWidth = pdfDirectory.getPageSize().getWidth() * 0.9f;
        table.setWidth(tableWidth);  // makes table 90% of page width
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.addCell(PdfCell.verticalSpaceCellWithPadding(mipTopPadding, false));
        table.addCell(PdfCell.verticalSpaceCellWithPadding(mipTopPadding, false));
        for (MembershipInfoDTO membership : batch) {
            table.addCell(createHeadingCell("#" + membership.getMembershipId() + "  Type: " + membership.getMemType()
                    + "    " + getSlipNumber(membership)));
            table.addCell(createHeadingCell(getPhone(membership, MemberType.PRIMARY, "E")));
            table.addCell(createTextCell(getPerson(membership, MemberType.PRIMARY)));
            table.addCell(createTextCell(getPerson(membership, MemberType.SECONDARY)));
            String primaryPhone = getPhone(membership, MemberType.PRIMARY, "C");
            String secondaryPhone = getPhone(membership, MemberType.SECONDARY, "C");
            if ((primaryPhone != null && !primaryPhone.trim().isEmpty()) || (secondaryPhone != null && !secondaryPhone.trim().isEmpty())) {
                table.addCell(createTextCell(primaryPhone));
                table.addCell(createTextCell(secondaryPhone));
            }
            String primaryEmail = getEmail(membership, MemberType.PRIMARY);
            String secondaryEmail = getEmail(membership, MemberType.SECONDARY);
            if ((primaryEmail != null && !primaryEmail.trim().isEmpty()) || (secondaryEmail != null && !secondaryEmail.trim().isEmpty())) {
                table.addCell(createEmailCell(primaryEmail));
                table.addCell(createEmailCell(secondaryEmail));
            }
            table.addCell(createDoubleTextCell(membership.getFullAddress()));
            String boats = getBoats(membership);
            if (!boats.trim().isEmpty())
                table.addCell(createDoubleTextCell(boats));
            String children = getPerson(membership, MemberType.DEPENDANT);
            if (!children.equals(""))
                table.addCell(createDoubleTextCell(children));
        }
        return table;
    }

    private Cell createHeadingCell(String text) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER, HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE,
                new SolidBorder(0.5f), pdfDirectory.getMainColor(), tableWidth * 0.5f);
        cell.add(PdfParagraph.paragraphOf(text, normalFontSize, fixedLeadingNarrow, mipHeaderColor));
        return cell;
    }

    private Cell createTextCell(String text) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(PdfParagraph.paragraphOf(text, normalFontSize, fixedLeadingNarrow));
        return cell;
    }

    private Cell createDoubleTextCell(String text) {
        Cell cell = PdfCell.cellOf(1, 2, Border.NO_BORDER);
        cell.add(PdfParagraph.paragraphOf(text, normalFontSize, fixedLeadingNarrow));
        return cell;
    }

    private Cell createEmailCell(String text) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(PdfParagraph.paragraphOf(text, normalFontSize, fixedLeadingNarrow, mipEmailColor));
        return cell;
    }

    private String getEmail(MembershipInfoDTO membership, MemberType type) {
        for (PersonDTO person : membership.getPeople()) {
            if (person.getMemberType() == type.getCode()) {
                if (person.getEmails() != null) {
                    for (EmailDTO email : person.getEmails()) {
                        if (email.getPrimaryUse() && email.getEmailListed()) {
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

    private String getPhone(MembershipInfoDTO membership, MemberType type, String phoneType) {
        for (PersonDTO person : membership.getPeople()) {
            if (person.getMemberType() == type.getCode()) {
                if (person.getPhones() != null) {
                    for (PhoneDTO phone : person.getPhones()) {
                        if (phone.getPhoneType().equals(phoneType)) {
                            if (phone.getPhoneType().equals("E")) return "Emergency: " + phone.getPhone();
                            else if (phone.isPhoneListed()) return phone.getPhone();
                            else PDF_Directory.logger.info(person.getFullName() + "'s phone " + phone.getPhone()
                                        + " is set to not listed");
                        }
                    }
                }
            }
        }
        return "";
    }

    private String getBoats(MembershipInfoDTO membership) {
        if (membership.getBoats() != null) {
            StringBuilder boatsBuilder = new StringBuilder();
            List<BoatDTO> boats = membership.getBoats();
            for (int i = 0; i < boats.size(); i++) {
                BoatDTO boat = boats.get(i);
                boatsBuilder.append(boat.getModel());
                String registrationNum = boat.getRegistrationNum();
                String boatName = boat.getBoatName();
                if (registrationNum != null && !registrationNum.isEmpty()) {
                    boatsBuilder.append(" ").append(registrationNum);
                }
                if (boatName != null && !boatName.isEmpty()) {
                    boatsBuilder.append(" ").append(boatName);
                }
                if (i < boats.size() - 1) {
                    boatsBuilder.append(", ");
                }
            }
            return boatsBuilder.toString();
        }
        return "";
    }

    private String getPerson(MembershipInfoDTO membership, MemberType type) {
        StringBuilder peopleBuilder = new StringBuilder();
        for (PersonDTO person : membership.getPeople()) {
            if (person.getMemberType() == type.getCode() && person.isActive()) {
                if (peopleBuilder.length() > 0) {
                    peopleBuilder.append(", ");
                }
                if (type.getCode() == 3) // lets print childrens name normal
                    peopleBuilder.append(person.getFullName());
                else
                    peopleBuilder.append(person.getReversedFullName()); // everyone else, last name first
            }
        }
        if (type.getCode() == 3 && peopleBuilder.length() > 0) {
            peopleBuilder.insert(0, "Children: ");
        }
        return peopleBuilder.toString();
    }


    private String getSlipNumber(MembershipInfoDTO membership) {
        if (membership.getSlip().getSlipNum() != null) return membership.getSlip().getSlipNum();
        return "";
    }
}
