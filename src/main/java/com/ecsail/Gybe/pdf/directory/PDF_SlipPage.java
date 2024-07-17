package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.SlipInfoDTO;
import com.ecsail.Gybe.dto.SlipPlacementDTO;
import com.ecsail.Gybe.dto.SlipStructureDTO;
import com.ecsail.Gybe.pdf.enums.Sections;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

import java.util.ArrayList;
import java.util.Comparator;

import static com.ecsail.Gybe.pdf.enums.Sections.*;

public class PDF_SlipPage {

    private final DirectoryModel model;
//    private String[] dockText;

    public PDF_SlipPage(DirectoryModel model) {
        this.model = model;
//        this.dockText = new String[3];
        model.getSlipPlacementDTOS().sort(Comparator.comparing(SlipPlacementDTO::getOrderPlaced));
//        this.dockText[0] = "Test Dock 4 \n Test Dock 3";
//        this.dockText[1] = "Test Dock 2 \n Test Dock 1";
//        this.dockText[2] = "X";
    }

    public Table createPage(int page) {
        Table table = new Table(2);
        table.setWidth(model.getPageSize().getWidth());
        float cellWidth = model.getMainTableWidth() / 2;
        Cell cellLeft = PdfCell.cellOf(Border.NO_BORDER);
        Cell cellRight = PdfCell.cellOf(Border.NO_BORDER);
        cellLeft.setPadding(0).setWidth(cellWidth);
        cellRight.setPadding(0).setWidth(cellWidth);
        table.addCell(cellLeft).addCell(cellRight);
        for(SlipPlacementDTO place: model.getSlipPlacementDTOS()) {
            if(place.getPagePlaced() == page) {
                if(place.getTablePlacedTo() == 1) {
                    cellLeft.add(new Paragraph("").setHeight(10)); // add space above table
                    cellLeft.add(createDock(place.getDock()).setHorizontalAlignment(HorizontalAlignment.CENTER));
                } else {
                    cellRight.add(new Paragraph("").setHeight(10)); // add space above table
                    cellRight.add(createDock(place.getDock()).setHorizontalAlignment(HorizontalAlignment.CENTER));
                }
            }
        }
        return table;
    }

    private Cell[] createSection(Sections section, String[] dockText, float height) {
        float leftWidth = 70;
        float rightWidth = 70;
        if(section == RIGHT_ONLY) leftWidth += 1f; // compensates for missing border
        if(section == LEFT_ONLY) rightWidth += 1f;
        Cell[] cell = new Cell[3];
                cell[0] = PdfCell.dockLeft(leftWidth, height, DeviceCmyk.YELLOW, section, dockText[0]);
                cell[1] = PdfCell.dockCenter(20, height, DeviceCmyk.YELLOW, section, dockText[2]);
                cell[2] = PdfCell.dockRight(rightWidth, height, DeviceCmyk.YELLOW, section, dockText[1]);
        return cell;
    }

    private Table createDock(String dock) {
        Table table = new Table(3);
        ArrayList<SlipStructureDTO> structure = new ArrayList<>();
        for(Cell cell: createSection(TOP_SECTION,new String[3],5)) table.addCell(cell);
        structure.clear();
        for(SlipStructureDTO dockSection: model.getSlipStructureDTOS()) {
            if(dockSection.getDock().equals(dock)) {
                structure.add(dockSection);
            }
        }
        structure.sort(Comparator.comparing(SlipStructureDTO::getDockSection));
        String [] dockText = new String[3];
        for(SlipStructureDTO dockSection: structure) {
            if(dockSection.getSlip3().equals("none") && dockSection.getSlip4().equals("none")) {
                dockText[0] = "";
                dockText[1] = getNameR(dockSection.getSlip2()) + "\n" + getNameR(dockSection.getSlip1());
                for(Cell cell: createSection(RIGHT_ONLY, dockText,18)) table.addCell(cell);
            } else {
                dockText[0] = getNameL(dockSection.getSlip4()) + "\n" + getNameL(dockSection.getSlip3());
                dockText[1] = getNameR(dockSection.getSlip2()) + "\n" + getNameR(dockSection.getSlip1());
                for(Cell cell: createSection(FULL_SECTION, dockText,18)) table.addCell(cell);
            }
            for (Cell cell : createSection(NON_SECTION, new String[3], 5)) table.addCell(cell);
        }
        String[] dockName = {"", "", dock};
        for(Cell cell: createSection(BOTTOM_SECTION,dockName,12)) table.addCell(cell);
        return table;
    }

    private String getNameR(String slip) {
        for(SlipInfoDTO info: model.getSlipInfoDTOS()) {
            if(info.getSlipNumber().equals(slip)) return  info.getSlipNumber() + " " + info.getOwnerLastName();
        }
        return "";
    }

    private String getNameL(String slip) {
        for(SlipInfoDTO info: model.getSlipInfoDTOS()) {
            if(info.getSlipNumber().equals(slip)) return info.getOwnerLastName() + " " + info.getSlipNumber();
        }
        return "";
    }


//    private String[] addInfo(SlipStructureDTO dockSection) {
//        String [] dockText = new String[3];
//        String [] dock = new String[4];
//        for(SlipInfoDTO info: model.getSlipInfoDTOS()) {
//            System.out.println(dockSection.getSlip1() + "  " + info.getSlipNumber());
//            if(dockSection.getSlip1().equals(info)) dock[0] = info.getSlipNumber() + " " + info.getOwnerLastName();
//            if(dockSection.getSlip2().equals(info)) dock[1] = info.getSlipNumber() + " " + info.getOwnerLastName();
//            if(dockSection.getSlip3().equals(info)) dock[2] = info.getOwnerLastName() + info.getSlipNumber();
//            if(dockSection.getSlip4().equals(info)) dock[3] = info.getOwnerLastName() + info.getSlipNumber();
//            if(dockSection.getSlip1().equals("none")) dock[0] = "";
//            if(dockSection.getSlip2().equals("none")) dock[1] = "";
//            if(dockSection.getSlip3().equals("none")) dock[2] = "";
//            if(dockSection.getSlip4().equals("none")) dock[3] = "";
//        }
//        dockText[0] = dock[0] + "\n" + dock[1];
//        dockText[1] = dock[2] + "\n" + dock[3];
//        dockText[2] = "";
//        return dockText;
//    }

//    private String returnSlipOwners() {
//        if(dockSection.getSlip1().equals(info.getSlipNumber())) getSlipNumber()).append(" ").append(info.getOwnerLastName());
//        if(dockSection.getSlip2())
//    }
}
//        this.dockText[0] = "Test Dock 4 \n Test Dock 3";
//        this.dockText[1] = "Test Dock 2 \n Test Dock 1";