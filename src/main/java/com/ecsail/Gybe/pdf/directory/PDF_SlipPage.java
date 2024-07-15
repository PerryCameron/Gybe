package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.pdf.enums.Sections;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

import static com.ecsail.Gybe.pdf.enums.Sections.*;

public class PDF_SlipPage {

    private final DirectoryModel model;

    public PDF_SlipPage(DirectoryModel model) {
        this.model = model;
    }

    public Table createPage(int Page) {
        Table table = new Table(2);
        table.setWidth(model.getMainTableWidth());
        Cell cellLeft = PdfCell.cellOf(Border.NO_BORDER);
        cellLeft.setPadding(0);
        Cell cellRight = PdfCell.cellOf(Border.NO_BORDER);
        cellRight.setPadding(0);

        cellLeft.add(createDock());
        table.addCell(cellLeft);
        table.addCell(cellRight);
        return table;
    }

    private Cell[] createSection(Sections section) {
        Cell[] cell = new Cell[3];
                cell[0] = PdfCell.dockLeft(70, 20, DeviceCmyk.YELLOW, section);
                cell[1] = PdfCell.dockCenter(20, 20, DeviceCmyk.YELLOW, section);
                cell[2] = PdfCell.dockRight(70, 20, DeviceCmyk.YELLOW, section);
        return cell;
    }

    private Table createDock() {
        Table table = new Table(3);
        for(Cell cell: createSection(TOP_SECTION)) table.addCell(cell);
        for(Cell cell: createSection(FULL_SECTION)) table.addCell(cell);
        for(Cell cell: createSection(NON_SECTION)) table.addCell(cell);
        for(Cell cell: createSection(LEFT_ONLY)) table.addCell(cell);
        for(Cell cell: createSection(NON_SECTION)) table.addCell(cell);
        for(Cell cell: createSection(LEFT_ONLY)) table.addCell(cell);
        for(Cell cell: createSection(NON_SECTION)) table.addCell(cell);
        for(Cell cell: createSection(FULL_SECTION)) table.addCell(cell);
        for(Cell cell: createSection(NON_SECTION)) table.addCell(cell);
        for(Cell cell: createSection(RIGHT_ONLY)) table.addCell(cell);
        for(Cell cell: createSection(NON_SECTION)) table.addCell(cell);
        for(Cell cell: createSection(RIGHT_ONLY)) table.addCell(cell);
        for(Cell cell: createSection(NON_SECTION)) table.addCell(cell);
        for(Cell cell: createSection(BOTTOM_SECTION)) table.addCell(cell);
        return table;
    }
}
