package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.SlipInfoDTO;
import com.ecsail.Gybe.dto.SlipPlacementDTO;
import com.ecsail.Gybe.dto.SlipStructureDTO;
import com.ecsail.Gybe.pdf.enums.Sections;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static com.ecsail.Gybe.pdf.enums.Sections.*;

public class PDF_SlipPage {

    private final DirectoryModel model;

    public PDF_SlipPage(DirectoryModel model) {
        this.model = model;
        model.getSlipPlacementDTOS().sort(Comparator.comparing(SlipPlacementDTO::getOrderPlaced));
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
        for (SlipPlacementDTO place : model.getSlipPlacementDTOS()) {
            if (place.getPagePlaced() == page) {
                if (place.getTablePlacedTo() == 1) {
                    cellLeft.add(new Paragraph("").setHeight(10)); // add space above table
                    cellLeft.add(createDock(place.getDock()).setHorizontalAlignment(HorizontalAlignment.CENTER));
                } else { // it is page 2
                    cellRight.add(new Paragraph("").setHeight(10)); // add space above table
                    cellRight.add(createDock(place.getDock()).setHorizontalAlignment(HorizontalAlignment.CENTER));
                }
            }
        }
        return table;
    }

    private Cell[] createSection(Sections section, Paragraph[] dockText, float height) {
        float leftWidth = 70;
        float rightWidth = 70;
        if (section == RIGHT_ONLY) leftWidth += 1f; // compensates for missing border
        if (section == LEFT_ONLY) rightWidth += 1f;
        Cell[] cell = new Cell[3];
        cell[0] = PdfCell.dockLeft(leftWidth, height, model.getSlipColor(), section, dockText);
        cell[1] = PdfCell.dockCenter(20, height, model.getSlipColor(), section, dockText[2]);
        cell[2] = PdfCell.dockRight(rightWidth, height, model.getSlipColor(), section, dockText);
        return cell;
    }

    private Table createDock(String dock) {
        Table table = new Table(3);
        ArrayList<SlipStructureDTO> structure = new ArrayList<>();
        Paragraph[] dockText = new Paragraph[5];
        for (Cell cell : createSection(TOP_SECTION, dockText, 5)) table.addCell(cell);
        structure.clear();
        structure = (ArrayList<SlipStructureDTO>) model.getSlipStructureDTOS().stream()
                .filter(dockSection -> dockSection.getDock().equals(dock))
                .collect(Collectors.toList());
        structure.sort(Comparator.comparing(SlipStructureDTO::getDockSection));
        for (SlipStructureDTO dockSection : structure) {
            if (dockSection.getSlip3().equals("none") && dockSection.getSlip4().equals("none")) {
                dockText[0] = new Paragraph("");
                dockText[1] = new Paragraph("");
                dockText[2] = new Paragraph(dockSection.getDock());
                dockText[3] = getName(dockSection.getSlip2(), true);
                dockText[4] = getName(dockSection.getSlip1(), true);
                for (Cell cell : createSection(RIGHT_ONLY, dockText, 18)) table.addCell(cell);
            } else {
                dockText[0] = getName(dockSection.getSlip4(), false);
                dockText[1] = getName(dockSection.getSlip3(), false);
                dockText[2] = new Paragraph(dockSection.getDock());
                dockText[3] = getName(dockSection.getSlip2(), true);
                dockText[4] = getName(dockSection.getSlip1(), true);
                for (Cell cell : createSection(FULL_SECTION, dockText, 18)) table.addCell(cell);
            }
            for (Cell cell : createSection(NON_SECTION, dockText, 5)) table.addCell(cell);
        }
        for (Cell cell : createSection(BOTTOM_SECTION, dockText, 12)) table.addCell(cell);
        return table;
    }

    private Paragraph getName(String slip, boolean rightSide) {
        Paragraph paragraph = new Paragraph();
        paragraph.setFontSize(6);
        for (SlipInfoDTO info : model.getSlipInfoDTOS()) {
            if (info.getOwnerMsid() == 0) {
                if (info.getSlipNumber().equals("F02")) setSlipInfo("48", info);
                if (info.getSlipNumber().equals("F04")) setSlipInfo("48", info);
                if (info.getSlipNumber().equals("CR2")) setSlipInfo("Racing", info);
                if (info.getSlipNumber().equals("CR1")) setSlipInfo("Racing", info);
            }
            if (rightSide) {
                if (info.getSlipNumber().equals(slip))
                    if (info.getSubleaserMsid() == 0) return paragraph.add(info.getRightSlipOwner());
                    else return paragraph.add(info.getRightSlipLeaser()).setFontColor(model.getSlipSubleaseColor());
            } else {
                if (info.getSlipNumber().equals(slip))
                    if (info.getSubleaserMsid() == 0) return paragraph.add(info.getLeftSlipOwner());
                    else return paragraph.add(info.getLeftSlipLeaser()).setFontColor(model.getSlipSubleaseColor());
            }
        }
        return paragraph.add("");
    }

    private void setSlipInfo(String type, SlipInfoDTO info) {
        if(type.equals("48")) {
            info.setOwnerLastName("48-Hour");
            info.setOwnerFirstName("");
        }
        if(type.equals("Racing")) {
            info.setOwnerLastName("Racing");
            info.setOwnerFirstName("");
        }
    }
}
