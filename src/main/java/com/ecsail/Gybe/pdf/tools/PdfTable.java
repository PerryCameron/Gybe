package com.ecsail.Gybe.pdf.tools;

import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;

public class PdfTable {

    public static Table TableOf(int columns, HorizontalAlignment alignment, float width) {
        Table table = new Table(columns);
        table.setHorizontalAlignment(alignment);
        table.setWidth(width);
        return table;
    }
}
