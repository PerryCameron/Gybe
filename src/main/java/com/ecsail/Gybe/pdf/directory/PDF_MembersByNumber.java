package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfTable;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class PDF_MembersByNumber {

    private DirectoryModel model = null;
    private float cellWidth;
    private int[] group;

    public PDF_MembersByNumber(DirectoryModel model) {
        this.model = model;
        this.cellWidth = model.getMainTableWidth() / 5;
        this.group = getHighestNumberDivisibleBy5MoreThanHalf();
    }

    public Table createPage(int page) {
        Table table = PdfTable.TableOf(5,HorizontalAlignment.CENTER, model.getMainTableWidth()); // Assuming 5 columns
        table.addCell(PdfCell.verticalSpaceCellWithPadding(20,5));
        table.addCell(PdfCell.addPageCell(5,createHeading(page), 0, model.getTocTitleFontSize(), model.getMainColor()));
        StringBuilder sb = new StringBuilder();
        Cell cell;
        Paragraph p;
        int count = 0;
        if(page == 2) count = group[0];
        System.out.println("Page: " + page);
         // To keep track of "box 1", "box 2", etc.
        for (int j = 0; j < 5; j++) {
            cell = PdfCell.cellOf(cellWidth);
            if(page == 2 && j == 4) // do this for last column on page 2
                for (int i = 0; i < group[3]; i++) {
                    getMembershipName(count, sb);
                    count++;
                }
            else
            for (int i = 0; i < group[2]; i++) {
                getMembershipName(count, sb);
                count++;
            }
            p = new Paragraph(sb.toString());
            p.setFontSize(8);
            p.setFixedLeading(18);
            cell.add(p);
            sb.setLength(0);
            table.addCell(cell);
        }
        return table;
    }

    private String createHeading(int page) {
        int firstNumber = 0;
        int secondNumber = 0;
        if(page == 1) {
            firstNumber = model.getMembershipInfoDTOS().get(0).getMembershipId();
            secondNumber = model.getMembershipInfoDTOS().get(group[0] -1).getMembershipId();
        } else {
            firstNumber = model.getMembershipInfoDTOS().get(group[0]).getMembershipId();
            secondNumber = model.getMembershipInfoDTOS().get(group[4] -1).getMembershipId();
        }
        return "Memberships "+firstNumber+" through "+secondNumber;
    }

    private void getMembershipName(int element, StringBuilder sb) {
        System.out.print("Getting element " + element);
        MembershipInfoDTO membership = model.getMembershipInfoDTOS().get(element);
        System.out.println(" " + membership.getMembershipId());
        sb.append(membership.getMembershipId() + " ");
        for(PersonDTO person: membership.getPeople()) {
            if(person.getMemberType() == 1)  sb.append(person.getLastName() + "\n");
        }
    }

    public int[] getHighestNumberDivisibleBy5MoreThanHalf() {
        int[] result = new int[5]; // Change array size to 5
        result[4] = model.getMembershipInfoDTOS().size();  // Use 'this' to refer to the instance's method directly
        int halfSize = result[4] / 2;
        // Find the highest number divisible by 5 that is more than half the size
        result[0] = ((halfSize / 5) + 1) * 5;
        // This is the total that is left
        result[1] = result[4] - result[0];
        // This is how many rows there are in page 1 and the first 4 columns of page 2
        result[2] = result[0] / 5;
        // This is the number of rows for the 5th column of page 2
        result[3] = result[2] - (result[0] - result[1]);  // the first 4 columns of the second page
        return result;
    }
}


