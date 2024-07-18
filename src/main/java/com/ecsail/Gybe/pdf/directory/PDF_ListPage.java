package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.pdf.enums.Pages;
import com.itextpdf.layout.element.Table;

public class PDF_ListPage {

    private final DirectoryModel model;

    public PDF_ListPage(DirectoryModel model, Pages pageType) {
        this.model = model;
    }

    public Table createPage() {
        Table table = new Table(2);
        return table;
    }
}
