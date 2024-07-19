package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.PersonListDTO;
import com.ecsail.Gybe.pdf.enums.Pages;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
import com.ecsail.Gybe.pdf.tools.PdfTable;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.util.Comparator;

public class PDF_ListPage {

    private final DirectoryModel model;

    public PDF_ListPage(DirectoryModel model) {
        this.model = model;
        model.getPersonListDTOS().sort(Comparator.comparing(PersonListDTO::getYear));
    }

    public Table createPage(Pages pageType) {
        int columnLength = divideIntoTwo(countDTOs(pageType));
        Table table = PdfTable.TableOf(2, HorizontalAlignment.CENTER, model.getMainTableWidth());
        table.addCell(setTopPadding(pageType));
        table.addCell(PdfCell.cellOf(1,2,Border.NO_BORDER).add(getTitle(pageType)));
        Cell[] cell = new Cell[2];
        cell[0] = PdfCell.cellOf(Border.NO_BORDER).setWidth(table.getWidth().getValue() / 2);
        cell[1] = PdfCell.cellOf(Border.NO_BORDER).setWidth(table.getWidth().getValue() / 2);
        int count = 0;
        for(PersonListDTO item: model.getPersonListDTOS()) {
            if(item.getPageType() == pageType) {
                if(count < columnLength)
                    cell[0].add(PdfParagraph.paragraphOf(item.getFullLine(),model.getNormalFontSize()).setFont(model.getFont()));
                else
                    cell[1].add(PdfParagraph.paragraphOf(item.getFullLine(),model.getNormalFontSize()).setFont(model.getFont()));
                count++;
            }
        }
        table.addCell(cell[0]).addCell(cell[1]);
        return table;
    }

    private Cell setTopPadding(Pages pageType) {
        switch (pageType) {
            case PAST_COMMODORES -> { return PdfCell.verticalSpaceCellWithPadding(model.getPcTopPadding(),2); }
            case SPORTSMANSHIP_AWARD -> { return PdfCell.verticalSpaceCellWithPadding(model.getSoyTopPadding(),2); }
        }
        return null;
    }

    private IBlockElement getTitle(Pages pageType) {
        String title = null;
        switch (pageType) {
            case PAST_COMMODORES -> title = "Past Commodores";
            case SPORTSMANSHIP_AWARD -> title = "Sportsmanship Award";
        }
        return PdfParagraph.paragraphOf(title, model.getTocTitleFontSize(), TextAlignment.CENTER)
                .setFontColor(model.getMainColor()).setFont(model.getFont());
    }

    private int countDTOs(Pages pageType) {
        int count = 0;
        for(PersonListDTO item: model.getPersonListDTOS())
            if(item.getPageType() == pageType) count++;
        return count;
    }

    public static int divideIntoTwo(int total) {
        return (total + 1) / 2; // This ensures the number is half plus the remainder
    }
}
