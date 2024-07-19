package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.PersonListDTO;
import com.ecsail.Gybe.pdf.enums.Pages;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.util.Comparator;

public class PDF_ListPage {

    private DirectoryModel model;

    public PDF_ListPage(DirectoryModel model) {
        this.model = model;
        model.getPersonListDTOS().sort(Comparator.comparing(PersonListDTO::getYear));
    }

    public Table createPage(Pages pageType) {
        int columnLength = divideIntoTwo(countDTOs(pageType));
        Table table = new Table(2);
        Cell cell1 = PdfCell.cellOf(Border.NO_BORDER);
        Cell cell2 = PdfCell.cellOf(Border.NO_BORDER);
        int count = 1;
        for(PersonListDTO item: model.getPersonListDTOS()) {
            if(item.getPageType() == pageType) {
                if(count < columnLength) cell1.add(new Paragraph(item.getFullLine()));
                else cell2.add(new Paragraph(item.getFullLine()));
                count++;
            }
        }
        table.addCell(cell1).addCell(cell2);
        return table;
    }

    private int countDTOs(Pages pageType) {
        int count = 0;
        for(PersonListDTO item: model.getPersonListDTOS())
            if(item.getPageType() == pageType) count++;
        return count;
    }

    public static int divideIntoTwo(int total) {
        int firstNumber = (total + 1) / 2; // This ensures the first number is the largest if the total is odd
        return firstNumber;
    }
}
