package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.wrappers.DirectoryDataWrapper;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class DirectoryModel {
    private final ArrayList<AppSettingsDTO> settings;
    private final ArrayList<MembershipInfoDTO> membershipInfoDTOS;
    private final ArrayList<BoardPositionDTO> positionData;
    private final ArrayList<SlipStructureDTO> slipStructureDTOS;
    private final ArrayList<SlipPlacementDTO> slipPlacementDTOS;
    private final ArrayList<SlipInfoDTO> slipInfoDTOS;
    private final ArrayList<SlipAltDTO> slipAltDTOS;
    private ArrayList<OfficerDTO> positions = null;
    private ArrayList<PersonListDTO> personListDTOS;
    private final Set<PersonDTO> people = new HashSet<>();
    private final CommodoreMessageDTO commodoreMessage;
    private Rectangle pageSize = null;
    private final String fontPath;
    private final String fontName;
    private final String logoPath;
    private final String membershipEmail;
    private final DeviceRgb mainColor;
    private final DeviceRgb mipHeaderColor;
    private final DeviceRgb mipEmailColor;
    private final DeviceRgb slipColor;
    private final DeviceRgb slipSubleaseColor;
    private final float fixedLeading;
    private final float bodTopPadding;
    private final float mainTableWidth;
    private final float bodTablePadding;
    private final float bodFooterFontSize;
    private final int selectedYear;
    private final float PositionHeadingFontSize;
    private final float normalFontSize;
	private final float logoTopPadding;
	private final float titleTopPadding;
	private final float titleFontSize;
	private final float titleFixedLeading;
    private final float salutationTopPadding;
    private final float messageTopPadding;
    private final float paragraphPadding;
    private final float paddingTop;
    private final float membershipInformationTitleFontSize;
    private final float titlePaddingBottom;
    private final float tocTopMarginPadding;
    private final float tocTitleFontSize;
    private final float tocChapterFontSize;
    private final float tocAddressPadding;
    private final float tocAddressFontSize;
    private final float tocChapterPadding;
    private final float fixedLeadingNarrow;
    private final float mipTopPadding;
    private final float mipPadding;
    private final float mbnFontSize;
    private final float mbnFixedLeading;
    private final float mbnTopPadding;
    private final float tocTitleFixedLeading;
    private final float tocTextFixedLeading;
    private final float slipPage1TopPadding;
    private final float slipPage2TopPadding;
    private final float dockTopPadding;
    private final float legendTopPadding;
    private final float dockTopHeight;
    private final float dockSectionHeight;
    private final float dockSectionConnectorHeight;
    private final float dockSectionBottomHeight;
    private final float dockFontSize;
    private final float dockWidth;
    private final float centerDockWidth;
    private final float legendSubFontSize;
    private final float legendTitleFontSize;
    private final float pcTopPadding;
    private final float soyTopPadding;
    private final float pcLeftPadding;
    private final float soyLeftPadding;
    private final float pcFixedLeading;
    private final float soyFixedLeading;
    private final float dockTextFixedLeading;
    private float width;
    private float height;
    private PdfFont font = null;

    public DirectoryModel(DirectoryDataWrapper directoryDataWrapper) {
        this.settings = directoryDataWrapper.getAppSettingsDTOS();
        this.membershipInfoDTOS = directoryDataWrapper.getMembershipInfoDTOS();
        this.positionData = directoryDataWrapper.getPositionData();
        this.commodoreMessage = directoryDataWrapper.getCommodoreMessage();
        this.fontPath = directoryDataWrapper.getFontPath();
        this.slipStructureDTOS = directoryDataWrapper.getSlipStructureDTOS();
        this.slipPlacementDTOS = extractSlipPlacementDTO();
        this.slipInfoDTOS = directoryDataWrapper.getSlipInfoDTOS();
        this.slipAltDTOS = extractAlternativeSlips();
        this.personListDTOS = directoryDataWrapper.getPersonListDTOS();
        this.width = setting("width");
        this.height = setting("height");
        this.mainColor = setting("mainColor");
        this.fontName = setting("fontName");
        this.positionData.sort(Comparator.comparingInt(BoardPositionDTO::order));
        this.fixedLeading = setting("positionFixedLeading");
        this.bodTopPadding = setting("bodTopPadding");
        this.bodTablePadding = setting("bodTablePadding");
        this.bodFooterFontSize = setting("bodFooterFontSize");
        this.selectedYear = setting("selectedYear");
        this.PositionHeadingFontSize = setting("PositionHeadingFontSize");
        this.normalFontSize = setting("normalFontSize");
        this.logoPath = setting("logoPath");
		this.logoTopPadding = setting("logoTopPadding");
		this.titleTopPadding = setting("titleTopPadding");
		this.titleFontSize = setting("titleFontSize");
		this.titleFixedLeading = setting("titleFixedLeading");
        this.salutationTopPadding = setting("salutationTopPadding");
        this.messageTopPadding = setting("messageTopPadding");
        this.paragraphPadding = setting("paragraphPadding");
        this.paddingTop = setting("membershipInfoTitlePaddingTop");
        this.membershipInformationTitleFontSize = setting("membershipInformationTitleFontSize");
        this.titlePaddingBottom = setting("membershipInfoTitlePaddingBottom");
        this.membershipEmail = setting("membershipEmail");
        this.tocTopMarginPadding = setting("tocTopMarginPadding");
        this.tocTitleFontSize = setting("tocTitleFontSize");
        this.tocChapterFontSize = setting("tocChapterFontSize");
        this.tocAddressPadding = setting("tocAddressPadding");
        this.tocAddressFontSize = setting("tocAddressFontSize");
        this.tocChapterPadding = setting("tocChapterPadding");
        this.fixedLeadingNarrow = setting("fixedLeadingNarrow");
        this.mipHeaderColor = setting("mipHeaderColor");
        this.mipEmailColor = setting("mipEmailColor");
        this.mipTopPadding = setting("mipTopPadding");
        this.mipPadding = setting("mipPadding");
        this.mbnFontSize = setting("mbnFontSize");
        this.mbnFixedLeading = setting("mbnFixedLeading");
        this.mbnTopPadding = setting("mbnTopPadding");
        this.tocTitleFixedLeading = setting("tocTitleFixedLeading");
        this.tocTextFixedLeading = setting("tocTextFixedLeading");
        this.slipColor = setting("slipColor");
        this.slipSubleaseColor = setting("slipSubleaseColor");
        this.slipPage1TopPadding = setting("slipPage1TopPadding");
        this.slipPage2TopPadding = setting("slipPage2TopPadding");
        this.dockTopPadding = setting("dockTopPadding");
        this.legendTopPadding = setting("legendTopPadding");
        this.dockTopHeight = setting("dockTopHeight");
        this.dockSectionHeight = setting("dockSectionHeight");
        this.dockSectionConnectorHeight = setting("dockSectionConnectorHeight");
        this.dockSectionBottomHeight = setting("dockSectionBottomHeight");
        this.dockFontSize = setting("dockFontSize");
        this.dockWidth = setting("dockWidth");
        this.centerDockWidth = setting("centerDockWidth");
        this.legendSubFontSize = setting("legendSubFontSize");
        this.legendTitleFontSize = setting("legendTitleFontSize");
        this.pcTopPadding = setting("pcTopPadding");
        this.soyTopPadding = setting("soyTopPadding");
        this.pcLeftPadding = setting("pcLeftPadding");
        this.soyLeftPadding = setting("soyLeftPadding");
        this.pcFixedLeading = setting("pcFixedLeading");
        this.soyFixedLeading = setting("soyFixedLeading");
        this.dockTextFixedLeading =  setting("dockTextFixedLeading");

        this.mainTableWidth = 72 * width * 0.9f;
    }

    private ArrayList<SlipAltDTO> extractAlternativeSlips() {
        ArrayList<SlipAltDTO> altDTOS = new ArrayList<>();
        for(AppSettingsDTO settingsDTO: settings) {
            if(settingsDTO.getDataType().equals("SlipAltDTO")) {
                altDTOS.add(new SlipAltDTO(settingsDTO.getKey(), settingsDTO.getValue()));
            }
        }
        return altDTOS;
    }

    private ArrayList<SlipPlacementDTO> extractSlipPlacementDTO() {
        ArrayList<SlipPlacementDTO> slipPlacements = new ArrayList<>();
        for(AppSettingsDTO settingsDTO: settings) {
            if(settingsDTO.getDataType().equals("SlipPlacementDTO")) {
                String[] values = settingsDTO.getValue().split(":");
                slipPlacements.add(new SlipPlacementDTO(settingsDTO.getKey(),
                        Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]),
                        Integer.parseInt(values[2])));
            }
        }
        return slipPlacements;
    }

    @SuppressWarnings("unchecked")
    protected <T> T setting(String name) {
        for (AppSettingsDTO setting : settings) {
            if (name.equals(setting.getKey())) {  // this is PDF_Directory.java:108
                String value = setting.getValue();
                switch (setting.getDataType()) {
                    case "integer" -> {
                        return (T) Integer.valueOf(value);
                    }
                    case "float" -> {
                        return (T) Float.valueOf(value);
                    }
                    case "webColor" -> { // Handle web color format #RRGGBB
                        return (T) parseWebColor(value);
                    }
                    case "DeviceRgb" -> {
                        return (T) parseDeviceRgb(value);
                    }
                    case "DeviceCmyk" -> {
                        return (T) parseDeviceCmyk(value);
                    }
                    default -> {  // Default case assumes the value is a string
                        return (T) value;
                    }
                }
            }
        }
        return null; // Or throw an exception if the setting is not found
    }

    private DeviceRgb parseWebColor(String value) {
        if (value.startsWith("#")) {
            value = value.substring(1);
        }
        int r = Integer.parseInt(value.substring(0, 2), 16);
        int g = Integer.parseInt(value.substring(2, 4), 16);
        int b = Integer.parseInt(value.substring(4, 6), 16);
        return new DeviceRgb(r, g, b);
    }

    private DeviceRgb parseDeviceRgb(String value) {
        String[] colorStrings = value.split(",");
        float[] col = new float[colorStrings.length];
        for (int i = 0; i < colorStrings.length; i++) {
            col[i] = Float.parseFloat(colorStrings[i]);
        }
        return new DeviceRgb(col[0], col[1], col[2]);
    }

    private DeviceCmyk parseDeviceCmyk(String value) {
        String[] colorStrings = value.split(",");
        float[] col = new float[colorStrings.length];
        for (int i = 0; i < colorStrings.length; i++) {
            col[i] = Float.parseFloat(colorStrings[i]);
        }
        return new DeviceCmyk(col[0], col[1], col[2], col[3]);
    }


    public ArrayList<MembershipInfoDTO> getMembershipInfoDTOS() {
        return membershipInfoDTOS;
    }

    public ArrayList<BoardPositionDTO> getPositionData() {
        return positionData;
    }

    public CommodoreMessageDTO getCommodoreMessage() {
        return commodoreMessage;
    }

    public Rectangle getPageSize() {
        return pageSize;
    }

    public void setPageSize(Rectangle pageSize) {
        this.pageSize = pageSize;
    }

    public String getFontPath() {
        return fontPath;
    }

    public PdfFont getFont() {
        return font;
    }

    public void setFont(PdfFont font) {
        this.font = font;
    }

    public DeviceRgb getMainColor() {
        return mainColor;
    }

    public float getFixedLeading() {
        return fixedLeading;
    }

    public float getBodTopPadding() {
        return bodTopPadding;
    }

    public float getBodTablePadding() {
        return bodTablePadding;
    }

    public float getBodFooterFontSize() {
        return bodFooterFontSize;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public float getPositionHeadingFontSize() {
        return PositionHeadingFontSize;
    }

    public float getNormalFontSize() {
        return normalFontSize;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getFontName() {
        return fontName;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public float getLogoTopPadding() {
        return logoTopPadding;
    }

    public float getTitleTopPadding() {
        return titleTopPadding;
    }

    public float getTitleFontSize() {
        return titleFontSize;
    }

    public float getTitleFixedLeading() {
        return titleFixedLeading;
    }

    public float getSalutationTopPadding() {
        return salutationTopPadding;
    }

    public float getMessageTopPadding() {
        return messageTopPadding;
    }

    public float getParagraphPadding() {
        return paragraphPadding;
    }

    public float getMainTableWidth() {
        return mainTableWidth;
    }

    public ArrayList<OfficerDTO> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<OfficerDTO> positions) {
        this.positions = positions;
    }

    public Set<PersonDTO> getPeople() {
        return people;
    }

    public float getPaddingTop() {
        return paddingTop;
    }

    public float getMembershipInformationTitleFontSize() {
        return membershipInformationTitleFontSize;
    }

    public float getTitlePaddingBottom() {
        return titlePaddingBottom;
    }

    public String getMembershipEmail() {
        return membershipEmail;
    }

    public float getTocTopMarginPadding() {
        return tocTopMarginPadding;
    }

    public float getTocTitleFontSize() {
        return tocTitleFontSize;
    }

    public float getTocChapterFontSize() {
        return tocChapterFontSize;
    }

    public float getTocAddressPadding() {
        return tocAddressPadding;
    }

    public float getTocAddressFontSize() {
        return tocAddressFontSize;
    }

    public float getTocChapterPadding() {
        return tocChapterPadding;
    }

    public float getFixedLeadingNarrow() {
        return fixedLeadingNarrow;
    }

    public DeviceRgb getMipHeaderColor() {
        return mipHeaderColor;
    }

    public DeviceRgb getMipEmailColor() {
        return mipEmailColor;
    }

    public float getMipTopPadding() {
        return mipTopPadding;
    }

    public float getMipPadding() {
        return mipPadding;
    }

    public float getMbnFontSize() {
        return mbnFontSize;
    }

    public float getMbnFixedLeading() {
        return mbnFixedLeading;
    }

    public float getMbnTopPadding() {
        return mbnTopPadding;
    }

    public float getTocTitleFixedLeading() {
        return tocTitleFixedLeading;
    }

    public float getTocTextFixedLeading() {
        return tocTextFixedLeading;
    }

    public ArrayList<SlipStructureDTO> getSlipStructureDTOS() {
        return slipStructureDTOS;
    }

    public ArrayList<SlipPlacementDTO> getSlipPlacementDTOS() {
        return slipPlacementDTOS;
    }

    public ArrayList<SlipInfoDTO> getSlipInfoDTOS() {
        return slipInfoDTOS;
    }

    public DeviceRgb getSlipColor() {
        return slipColor;
    }

    public DeviceRgb getSlipSubleaseColor() {
        return slipSubleaseColor;
    }

    public ArrayList<SlipAltDTO> getSlipAltDTOS() {
        return slipAltDTOS;
    }

    public float getSlipPage1TopPadding() {
        return slipPage1TopPadding;
    }

    public float getSlipPage2TopPadding() {
        return slipPage2TopPadding;
    }

    public float getDockTopPadding() {
        return dockTopPadding;
    }

    public float getLegendTopPadding() {
        return legendTopPadding;
    }

    public float getDockTopHeight() {
        return dockTopHeight;
    }

    public float getDockSectionHeight() {
        return dockSectionHeight;
    }

    public float getDockSectionConnectorHeight() {
        return dockSectionConnectorHeight;
    }

    public float getDockSectionBottomHeight() {
        return dockSectionBottomHeight;
    }

    public float getDockFontSize() {
        return dockFontSize;
    }

    public float getDockWidth() {
        return dockWidth;
    }

    public float getCenterDockWidth() {
        return centerDockWidth;
    }

    public float getLegendSubFontSize() {
        return legendSubFontSize;
    }

    public float getLegendTitleFontSize() {
        return legendTitleFontSize;
    }

    public ArrayList<PersonListDTO> getPersonListDTOS() {
        return personListDTOS;
    }

    public float getPcTopPadding() {
        return pcTopPadding;
    }

    public float getSoyTopPadding() {
        return soyTopPadding;
    }

    public float getPcLeftPadding() {
        return pcLeftPadding;
    }

    public float getSoyLeftPadding() {
        return soyLeftPadding;
    }

    public float getPcFixedLeading() {
        return pcFixedLeading;
    }

    public float getSoyFixedLeading() {
        return soyFixedLeading;
    }

    public float getDockTextFixedLeading() {
        return dockTextFixedLeading;
    }
}
