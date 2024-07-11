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
    private final PDF_Directory pdfDirectory;
    private final ArrayList<OfficerDTO> positions;
    private final Set<PersonDTO> people = new HashSet<>();
    float fixedLeading;


    public PDF_BoardOfDirectors(int numColumns, PDF_Directory pdfDirectory) {
        super(numColumns);
        this.pdfDirectory = pdfDirectory;
        this.positions = extractPositions(Year.now().getValue());
        this.positionData = pdfDirectory.getPositionData();
        fixedLeading = pdfDirectory.setting("positionFixedLeading");

        // sort positions by order
        positionData.sort(Comparator.comparingInt(BoardPositionDTO::order));
        setWidth(pdfDirectory.getPageSize().getWidth() * 0.9f);  // makes table 90% of page width
        setHorizontalAlignment(HorizontalAlignment.CENTER);
        addCell(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("bodTopPadding"), false));
        Cell cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(createOfficersTable());
        cell.add(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("bodTablePadding"), false));
        cell.add(createChairmenTable());
        cell.add(PdfCell.verticalSpaceCellWithPadding(pdfDirectory.setting("bodTablePadding"), false));
        cell.add(createBODTable());
        addCell(cell);
        cell = PdfCell.cellOf(Border.NO_BORDER);
        int selectedYear = pdfDirectory.setting("selectedYear");
        String footerText = "©Eagle Creek Sailing club 1969-" + selectedYear + " - This directory may not be used for commercial purposes";
        cell.add(PdfParagraph.paragraphOf(footerText, pdfDirectory.setting("bodFooterFontSize"), TextAlignment.CENTER));
        addCell(cell);
    }

    public Table createOfficersTable() {
        Table table = PdfTable.TableOf(2,HorizontalAlignment.CENTER, this.getWidth().getValue() * 0.6f);
        Cell cell = PdfCell.cellOf(1,2,Border.NO_BORDER);
        int selectedYear = pdfDirectory.setting("selectedYear");
        Paragraph paragraph = PdfParagraph.paragraphOf(selectedYear + " Officers", pdfDirectory.setting("PositionHeadingFontSize"),
                pdfDirectory.getHeadingFont(),pdfDirectory.setting("mainColor"),TextAlignment.CENTER);
        table.addCell(cell.add(paragraph));
        Cell[] cells = processPositions(BoardPositionDTO::isOfficer); // what if I want to also have the ablity to process conditon or condition?
        for (Cell c : cells) table.addCell(c);
        return table;
    }

    public Table createChairmenTable() {
        Table table = PdfTable.TableOf(2,HorizontalAlignment.CENTER, this.getWidth().getValue() * 0.7f);
        Cell cell = PdfCell.cellOf(1,2,Border.NO_BORDER);
        Paragraph paragraph = PdfParagraph.paragraphOf("Committee Chairs",pdfDirectory.setting("PositionHeadingFontSize"),
                pdfDirectory.getHeadingFont(),pdfDirectory.setting("mainColor"),TextAlignment.CENTER);
        table.addCell(cell.add(paragraph));
        Cell[] cells = processPositions(position -> position.isChair() || position.isAssist());
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
        for (MembershipInfoDTO membership : pdfDirectory.getMembershipInfoDTOS()) {
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
        Paragraph paragraph = PdfParagraph.paragraphOf(content,pdfDirectory.setting("normalFontSize"),fixedLeading);
        cell.add(paragraph);
        return cell;
    }

    private Table createBODTable() {
        Table table = PdfTable.TableOf(3,HorizontalAlignment.CENTER, this.getWidth().getValue());
        Cell cell = PdfCell.cellOf(1,3,Border.NO_BORDER);
        Paragraph paragraph = PdfParagraph.paragraphOf("Current Board Members",
                pdfDirectory.setting("PositionHeadingFontSize"),pdfDirectory.getHeadingFont(),pdfDirectory.setting("mainColor"),TextAlignment.CENTER);
        table.addCell(cell.add(paragraph));
        createBoardMemberTables(table); // will create 3 more cells and put a table in each
        return table;
    }

    private void createBoardMemberTables(Table bodTable) {
        int selectedYear = pdfDirectory.setting("selectedYear");
        Cell cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(createBoardMemberColumn(selectedYear));
        bodTable.addCell(cell);

        cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(createBoardMemberColumn(selectedYear + 1));
        bodTable.addCell(cell);

        cell = PdfCell.cellOf(Border.NO_BORDER);
        cell.add(createBoardMemberColumn(selectedYear + 2));
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
        Cell cell = PdfCell.cellOf(Border.NO_BORDER);
        Paragraph paragraph = PdfParagraph.paragraphOf(String.valueOf(year),pdfDirectory.setting("normalFontSize"), pdfDirectory.getHeadingFont(),fixedLeading);
        paragraph.setTextAlignment(TextAlignment.LEFT);
        cell.add(paragraph);
        columnTable.addCell(cell);

        for (String name : boardMemberList) {
            cell = PdfCell.cellOf(Border.NO_BORDER);
            paragraph = PdfParagraph.paragraphOf(name, pdfDirectory.setting("normalFontSize"), fixedLeading);
            cell.add(paragraph);
            columnTable.addCell(cell);
        }
        return columnTable;
    }

    private static String getLastName(String fullName) {
        String[] parts = fullName.split(" ");
        return parts[parts.length - 1];
    }
}
