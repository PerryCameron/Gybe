package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.BoardPositionDTO;
import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.dto.OfficerDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.pdf.tools.PdfCell;
import com.ecsail.Gybe.pdf.tools.PdfParagraph;
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
        this.positions = extractPositions(Year.now().getValue());
        this.positionData = pdfDirectory.getPositionData();
        // sort positions by order
        positionData.sort(Comparator.comparingInt(BoardPositionDTO::order));
        setWidth(set.getPageSize().getWidth() * 0.9f);  // makes table 90% of page width
        setHorizontalAlignment(HorizontalAlignment.CENTER);
        Cell cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(createOfficersTable());
        cell.add(createChairmenTable());
        cell.add(createBODTable());
        addCell(cell);
        addCell(PdfCell.verticalSpaceCell(1));
        cell = PdfCell.cellOf(Border.NO_BORDER);
        Paragraph p = new Paragraph("©Eagle Creek Sailing club 1969-" + set.getSelectedYear() + " - This directory may not be used for commercial purposes");
        p.setTextAlignment(TextAlignment.CENTER);
        p.setFontSize(8);  // Set the desired font size here
        cell.add(p);
        addCell(cell);
    }

    public Table createOfficersTable() {
        Table table = PdfTable.TableOf(2,HorizontalAlignment.CENTER, this.getWidth().getValue() * 0.6f);
        Cell cell = PdfCell.cellOf(1,2,Border.NO_BORDER);
        Paragraph paragraph = PdfParagraph.paragraphOf(set.getSelectedYear() + " Officers",set.getNormalFontSize() + 4,
                set.getColumnHead(),set.getMainColor(),TextAlignment.CENTER);
        table.addCell(cell.add(paragraph));
        Cell[] cells = processPositions(BoardPositionDTO::isOfficer); // what if I want to also have the ablity to process conditon or condition?
        for (Cell c : cells) table.addCell(c);
        return table;
    }

    public Table createChairmenTable() {
        Table table = PdfTable.TableOf(2,HorizontalAlignment.CENTER, this.getWidth().getValue() * 0.7f);
        Cell cell = PdfCell.cellOf(1,2,Border.NO_BORDER);
        Paragraph paragraph = PdfParagraph.paragraphOf("Committee Chairs",set.getNormalFontSize() + 4,
                set.getColumnHead(),set.getMainColor(),TextAlignment.CENTER);
        table.addCell(cell.add(paragraph));
        Cell[] cells = processPositions(position -> position.isChair() || position.isAssist()); // what if I want to also have the ablity to process conditon or condition?
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
                                cellList.add(addPersonCell(person.getFullName()));
                            }
                        }
                    }
                }
            }
        }
        return cellList.toArray(new Cell[0]);
    }

    private ArrayList<OfficerDTO> extractPositions(int year) {
        ArrayList<OfficerDTO> officers = new ArrayList<>();
        for (MembershipInfoDTO membership : memberships) {
            for (PersonDTO person : membership.getPeople()) {
                // crack open the list of officers
                List<OfficerDTO> personOfficers = person.getOfficers();
                if (personOfficers != null) {
                    for (OfficerDTO officer : personOfficers) {
                        // get correct officer that matches year
                        if (officer.getFiscalYear() == year) {
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

    private Cell addPersonCell(String content) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER, HorizontalAlignment.CENTER);
        cell.add(PdfParagraph.paragraphOf(content,set.getNormalFontSize(),
                set.getColumnHead(),set.getFixedLeading() - 15));
        return cell;
    }

    private Table createBODTable() {
        Table table = PdfTable.TableOf(3,HorizontalAlignment.CENTER, this.getWidth().getValue());
        Cell cell = PdfCell.cellOf(1,3,Border.NO_BORDER);
        Paragraph paragraph = PdfParagraph.paragraphOf("Current Board Members",
                set.getNormalFontSize() + 4,set.getColumnHead(),set.getMainColor(),TextAlignment.CENTER);
        table.addCell(cell.add(paragraph));
        createBoardMemberTables(table); // will create 3 more cells and put a table in each
        return table;
    }

    private void createBoardMemberTables(Table bodTable) {
        Cell cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(createBoardMemberColumn(Integer.parseInt(set.getSelectedYear())));
        bodTable.addCell(cell);

        cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(createBoardMemberColumn(Integer.parseInt(set.getSelectedYear()) + 1));
        bodTable.addCell(cell);

        cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(createBoardMemberColumn(Integer.parseInt(set.getSelectedYear()) + 2));
        bodTable.addCell(cell);
    }

    private String[] selectBoardMemberListFor(int year) {
        List<String> personList = new ArrayList<>();
        for (OfficerDTO officer : positions) {
            if (officer.getBoardYear() == year) {
                for (PersonDTO person : people) {
                    if (officer.getpId() == person.getPId()) {
                        personList.add(person.getFullName());
                    }
                }
            }
        }
        personList.sort(Comparator.comparing(PDF_BoardOfDirectors::getLastName));
        return personList.toArray(new String[0]);
    }

    private Table createBoardMemberColumn(int year) {
        String[] boardMemberList = selectBoardMemberListFor(year);
        Table columnTable = new Table(1);
        Cell cell;
        Paragraph p;
        cell = new Cell();
        p = new Paragraph(String.valueOf(year));
        p.setFontSize(12);
        p.setFont(set.getColumnHead());
        p.setFixedLeading(set.getFixedLeading() - 15);  // sets spacing between lines of text
        p.setTextAlignment(TextAlignment.LEFT);
        cell.setBorder(Border.NO_BORDER).add(p);
        columnTable.addCell(cell);

        for (String name : boardMemberList) {
            cell = new Cell();
            p = new Paragraph(name);
            p.setFontSize(set.getNormalFontSize());
            p.setFixedLeading(set.getFixedLeading() - 15);  // sets spacing between lines of text
            cell.setBorder(Border.NO_BORDER).add(p);
            columnTable.addCell(cell);
        }
        return columnTable;
    }

    private static String getLastName(String fullName) {
        String[] parts = fullName.split(" ");
        return parts[parts.length - 1];
    }
}
