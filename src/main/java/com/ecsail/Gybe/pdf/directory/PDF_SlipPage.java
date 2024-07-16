package com.ecsail.Gybe.pdf.directory;

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
    private String[] dockText;

    public PDF_SlipPage(DirectoryModel model) {
        this.model = model;
        this.dockText = new String[3];
        model.getSlipPlacementDTOS().sort(Comparator.comparing(SlipPlacementDTO::getOrderPlaced));
        this.dockText[0] = "Test Dock 4 \n Test Dock 3";
        this.dockText[1] = "Test Dock 2 \n Test Dock 1";
        this.dockText[2] = "X";
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
        Cell[] cell = new Cell[3];
                cell[0] = PdfCell.dockLeft(70, height, DeviceCmyk.YELLOW, section, dockText[0]);
                cell[1] = PdfCell.dockCenter(20, height, DeviceCmyk.YELLOW, section, dockText[2]);
                cell[2] = PdfCell.dockRight(70, height, DeviceCmyk.YELLOW, section, dockText[1]);
        return cell;
    }

    private Table createDock(String dock) {
        Table table = new Table(3);
        ArrayList<SlipStructureDTO> structure = new ArrayList<>();
        for(Cell cell: createSection(TOP_SECTION,dockText,5)) table.addCell(cell);
        structure.clear();
        for(SlipStructureDTO dockSection: model.getSlipStructureDTOS()) {
            if(dockSection.getDock().equals(dock)) {
                structure.add(dockSection);
            }
        }
        structure.sort(Comparator.comparing(SlipStructureDTO::getDockSection));
        for(SlipStructureDTO dockSection: structure) {
            if(dockSection.getSlip3().equals("none") && dockSection.getSlip4().equals("none")) {
                for(Cell cell: createSection(RIGHT_ONLY,dockText,18)) table.addCell(cell);
            } else {
                for(Cell cell: createSection(FULL_SECTION,dockText,18)) table.addCell(cell);
            }
            for (Cell cell : createSection(NON_SECTION, dockText, 5)) table.addCell(cell);
        }
        String[] dockName = {"", "", dock};
        for(Cell cell: createSection(BOTTOM_SECTION,dockName,12)) table.addCell(cell);
        return table;
    }
}

//        for(Cell cell: createSection(FULL_SECTION,dockText,20)) table.addCell(cell);
//        for(Cell cell: createSection(NON_SECTION,dockText,10)) table.addCell(cell);
//        for(Cell cell: createSection(LEFT_ONLY,dockText,20)) table.addCell(cell);
//        for(Cell cell: createSection(NON_SECTION,dockText,10)) table.addCell(cell);
//        for(Cell cell: createSection(LEFT_ONLY,dockText,20)) table.addCell(cell);
//        for(Cell cell: createSection(NON_SECTION,dockText,10)) table.addCell(cell);
//        for(Cell cell: createSection(FULL_SECTION,dockText,20)) table.addCell(cell);
//        for(Cell cell: createSection(NON_SECTION,dockText,10)) table.addCell(cell);
//        for(Cell cell: createSection(RIGHT_ONLY,dockText,20)) table.addCell(cell);
//        for(Cell cell: createSection(NON_SECTION,dockText,10)) table.addCell(cell);
//        for(Cell cell: createSection(RIGHT_ONLY,dockText,20)) table.addCell(cell);
//        for(Cell cell: createSection(NON_SECTION,dockText,10)) table.addCell(cell);
//        for(Cell cell: createSection(BOTTOM_SECTION,dockText,20)) table.addCell(cell);