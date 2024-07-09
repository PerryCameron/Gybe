package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.BoardPositionDTO;
import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.dto.OfficerDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.pdf.tools.PdfTable;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.time.Year;
import java.util.*;
import java.util.function.Predicate;


public class PDF_BoardOfDirectors extends Table {
    private final ArrayList<BoardPositionDTO> positionData;
    PDF_Object_Settings set;
    ArrayList<MembershipInfoDTO> memberships;
    ArrayList<OfficerDTO> positions;
    Set<PersonDTO> people = new HashSet<>();

    public PDF_BoardOfDirectors(int numColumns, PDF_Directory pdfDirectory) {
        super(numColumns);
        this.set = pdfDirectory.getSet();
        this.memberships = pdfDirectory.getMembershipInfoDTOS();
        this.positions = extractPositions(Year.now().toString());
        this.positionData = pdfDirectory.getPositionData();
        // sort positions by order
        positionData.sort(Comparator.comparingInt(BoardPositionDTO::order));
        setWidth(set.getPageSize().getWidth() * 0.9f);  // makes table 90% of page width
        setHorizontalAlignment(HorizontalAlignment.CENTER);
        Cell cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
//        cell.add(new Paragraph("\n"));
        cell.add(createOfficersTable());
        cell.add(new Paragraph("\n"));
        cell.add(createChairmenTable());
        //cell.add(new Paragraph("\n"));
//        cell.add(createBODTable());
        addCell(cell);

        cell = new Cell();
        cell.add(new Paragraph("\n"));
        cell.setBorder(Border.NO_BORDER);
        addCell(cell);

        cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        Paragraph p = new Paragraph("©Eagle Creek Sailing club 1969-" + set.getSelectedYear() + " - This directory may not be used for commercial purposes");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(8);  // Set the desired font size here
        cell.add(p);
        addCell(cell);
    }

    private ArrayList<OfficerDTO> extractPositions(String year) {
        ArrayList<OfficerDTO> officers = new ArrayList<>();
        for (MembershipInfoDTO membership : memberships) {
            for (PersonDTO person : membership.getPeople()) {
                // crack open the list of officers
                List<OfficerDTO> personOfficers = person.getOfficers();
                if (personOfficers != null) {
                    for (OfficerDTO officer : personOfficers) {
                        // get correct officer that matches year
                        if (officer.getFiscalYear().equals(year)) {
                            // populate pid since it comes null by default
                            officer.setpId(person.getPId());
                            // this is a set to prevent duplicating people
                            people.add(person);
                            officers.add(officer);
                        }
                    }
                }
            }
        }
        return officers;
    }

    public Table createOfficersTable() {
        Table table = new Table(2);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setWidth(this.getWidth().getValue() * 0.6f);
        //mainTable.setWidth(590);
        Cell cell;
        Paragraph p;
        cell = new Cell(1, 2);
        cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("\n" + set.getSelectedYear() + " Officers");
        p.setFontSize(set.getNormalFontSize() + 4);
        p.setFont(set.getColumnHead());
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontColor(set.getMainColor());
        cell.add(p);
        table.addCell(cell);
        Cell[] cells = processPositions(BoardPositionDTO::isOfficer); // what if I want to also have the ablity to process conditon or condition?
        for (Cell c : cells) table.addCell(c);
        return table;
    }

    public Cell[] processPositions(Predicate<BoardPositionDTO> condition) {
        List<Cell> cellList = new ArrayList<>();
        for (BoardPositionDTO position : positionData) {
            if (condition.test(position)) {
                for (OfficerDTO officer : positions) {
                    if (officer.getOfficerType().equals(position.identifier())) {
                        cellList.add(addPersonCell(position.position()));
                        for (PersonDTO person : people) {
                            if (officer.getpId() == person.getPId()) {
                                cellList.add(addPersonCell(person.getFirstName() + " " + person.getLastName()));
                            }
                        }
                    }
                }
            }
        }
        return cellList.toArray(new Cell[0]);
    }

    private Cell addPersonCell(String cellContent) {
        Cell cell = new Cell();
        Paragraph p;
        p = new Paragraph(cellContent);
        p.setFontSize(set.getNormalFontSize());
        p.setFont(set.getColumnHead());
        p.setFixedLeading(set.getFixedLeading() - 15);  // sets spacing between lines of text
        cell.setBorder(Border.NO_BORDER).add(p).setHorizontalAlignment(HorizontalAlignment.CENTER);
        return cell;
    }

    public Table createChairmenTable() {
        Table table = PdfTable.TableOf(2,HorizontalAlignment.CENTER, this.getWidth().getValue() * 0.7f);
        Cell cell;
        Paragraph p;
        cell = new Cell(1, 2);
        cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("Committee Chairs");
        p.setFontSize(set.getNormalFontSize() + 4);
        p.setFont(set.getColumnHead());
        p.setFontColor(set.getMainColor());
        p.setTextAlignment(TextAlignment.CENTER);
        cell.add(p);
        table.addCell(cell);
        Cell[] cells = processPositions(position -> position.isChair() || position.isAssist()); // what if I want to also have the ablity to process conditon or condition?
        for (Cell c : cells) table.addCell(c);
        return table;
    }



    private Table createBODTable() {

        Table bodTable = new Table(3);
        bodTable.setWidth(this.getWidth().getValue());
        bodTable.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Cell cell;
        Paragraph p;
        cell = new Cell(1, 3);
        cell.setBorder(Border.NO_BORDER);
        p = new Paragraph("Current Board Members");
        p.setFontSize(set.getNormalFontSize() + 4);
        p.setFont(set.getColumnHead());
        p.setFontColor(set.getMainColor());
        p.setTextAlignment(TextAlignment.CENTER);
        cell.add(p);
        bodTable.addCell(cell);

        createBoardMemberTables(bodTable); // will create 3 more cells and put a table in each

        return bodTable;
    }

    private void createBoardMemberTables(Table bodTable) {
        ArrayList<String> currentYearList = new ArrayList<>();
        ArrayList<String> nextYearList = new ArrayList<>();
        ArrayList<String> afterNextYearList = new ArrayList<>();
        int thisYear = Integer.parseInt(set.getSelectedYear());
        int nextYear = thisYear + 1;
        int afterNextYear = thisYear + 2;


        Cell cell;

        cell = new Cell();  // make a big cell in previous table to put 3 tables in
        cell.add(createBoardMemberColumn(currentYearList, String.valueOf(thisYear)));
        cell.setBorder(Border.NO_BORDER);
        bodTable.addCell(cell);

        cell = new Cell();
        cell.add(createBoardMemberColumn(nextYearList, String.valueOf(nextYear)));
        cell.setBorder(Border.NO_BORDER);
        bodTable.addCell(cell);

        cell = new Cell();
        cell.add(createBoardMemberColumn(afterNextYearList, String.valueOf(afterNextYear)));
        cell.setBorder(Border.NO_BORDER);
        bodTable.addCell(cell);

    }

    private Table createBoardMemberColumn(ArrayList<String> yearList, String year) {
        Table columnTable = new Table(1);
        Cell cell;
        Paragraph p;
        cell = new Cell();
        p = new Paragraph(year);
        p.setFontSize(12);
        p.setFont(set.getColumnHead());
        p.setFixedLeading(set.getFixedLeading() - 15);  // sets spacing between lines of text
        p.setTextAlignment(TextAlignment.LEFT);
        cell.setBorder(Border.NO_BORDER).add(p);
        columnTable.addCell(cell);

        for (String name : yearList) {
            cell = new Cell();
            p = new Paragraph(name);
            p.setFontSize(set.getNormalFontSize());
            p.setFixedLeading(set.getFixedLeading() - 15);  // sets spacing between lines of text
            cell.setBorder(Border.NO_BORDER).add(p);
            columnTable.addCell(cell);
        }

        return columnTable;
    }



//    public PDF_Object_Officer getOfficer(String type) {
//        return pdfObjectOfficers.stream()
//                .filter(o -> !o.getOfficerPlaced() && o.getOfficerType().equals(type))
//                .findFirst()
//                .map(o -> {
//                    o.setOfficerPlaced(true);
//                    return o;
//                })
//                .orElse(null);
//    }


}
