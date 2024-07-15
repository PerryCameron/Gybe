package com.ecsail.Gybe.pdf.directory;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.wrappers.DirectoryDataWrapper;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class DirectoryModel {
    private final ArrayList<AppSettingsDTO> settings;

    private ArrayList<MembershipInfoDTO> membershipInfoDTOS;
    private ArrayList<BoardPositionDTO> positionData;
    private ArrayList<OfficerDTO> positions = null;
    private final Set<PersonDTO> people = new HashSet<>();
    private CommodoreMessageDTO commodoreMessage;
    private Rectangle pageSize = null;
    private String fontPath;
    private PdfFont font = null;
    private DeviceCmyk mainColor;
    private final float fixedLeading;
    private final float bodTopPadding;
    private float mainTableWidth;
    private final float bodTablePadding;
    private final float bodFooterFontSize;
    private final int selectedYear;
    private final float PositionHeadingFontSize;
    private final float normalFontSize;
    private float width;
    private float height;
    private String fontName;
    private final String logoPath;
	private final float logoTopPadding;
	private final float titleTopPadding;
	private final float titleFontSize;
	private final float titleFixedLeading;
    private final float salutationTopPadding;
    private final float messageTopPadding;
    private final float paragraphPadding;
    private float paddingTop;
    private float membershipInformationTitleFontSize;
    private float titlePaddingBottom;
    private String membershipEmail;
    private final float tocTopMarginPadding;
    private final float tocTitleFontSize;
    private final float tocChapterFontSize;
    private final float tocAddressPadding;
    private final float tocAddressFontSize;
    private final float tocChapterPadding;
    private final float fixedLeadingNarrow;
    private final DeviceCmyk mipHeaderColor;
    private final DeviceCmyk mipEmailColor;
    private final float mipTopPadding;
    private final float mipPadding;
    private final float mbnFontSize;
    private final float mbnFixedLeading;
    private final float mbnTopPadding;

    public DirectoryModel(DirectoryDataWrapper directoryDataWrapper) {
        this.settings = directoryDataWrapper.getAppSettingsDTOS();
        this.membershipInfoDTOS = directoryDataWrapper.getMembershipInfoDTOS();
        this.positionData = directoryDataWrapper.getPositionData();
        this.commodoreMessage = directoryDataWrapper.getCommodoreMessage();
        this.fontPath = directoryDataWrapper.getFontPath();
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

        this.mainTableWidth = 72 * width * 0.9f;
    }

    @SuppressWarnings("unchecked")
    protected  <T> T setting(String name) {
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
                    case "DeviceCmyk" -> {
                        String[] colorStrings = setting.getValue().split(",");
                        float[] col = new float[colorStrings.length];
                        for (int i = 0; i < colorStrings.length; i++) {
                            col[i] = Float.parseFloat(colorStrings[i]);
                        }
                        return (T) new DeviceCmyk(col[0], col[1], col[2], col[3]);
                    }
                    default -> {  // is a string
                        return (T) value;
                    }
                }
            }
        }
        PDF_Directory.logger.error("No setting found for: " + name);
        return null; // or throw an exception if the setting is not found
    }


    public ArrayList<MembershipInfoDTO> getMembershipInfoDTOS() {
        return membershipInfoDTOS;
    }

    public void setMembershipInfoDTOS(ArrayList<MembershipInfoDTO> membershipInfoDTOS) {
        this.membershipInfoDTOS = membershipInfoDTOS;
    }

    public ArrayList<BoardPositionDTO> getPositionData() {
        return positionData;
    }

    public void setPositionData(ArrayList<BoardPositionDTO> positionData) {
        this.positionData = positionData;
    }

    public CommodoreMessageDTO getCommodoreMessage() {
        return commodoreMessage;
    }

    public void setCommodoreMessage(CommodoreMessageDTO commodoreMessage) {
        this.commodoreMessage = commodoreMessage;
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

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    public PdfFont getFont() {
        return font;
    }

    public void setFont(PdfFont font) {
        this.font = font;
    }

    public DeviceCmyk getMainColor() {
        return mainColor;
    }

    public void setMainColor(DeviceCmyk mainColor) {
        this.mainColor = mainColor;
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

    public void setFontName(String fontName) {
        this.fontName = fontName;
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

    public void setMainTableWidth(float mainTableWidth) {
        this.mainTableWidth = mainTableWidth;
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

    public void setPaddingTop(float paddingTop) {
        this.paddingTop = paddingTop;
    }

    public float getMembershipInformationTitleFontSize() {
        return membershipInformationTitleFontSize;
    }

    public void setMembershipInformationTitleFontSize(float membershipInformationTitleFontSize) {
        this.membershipInformationTitleFontSize = membershipInformationTitleFontSize;
    }

    public float getTitlePaddingBottom() {
        return titlePaddingBottom;
    }

    public void setTitlePaddingBottom(float titlePaddingBottom) {
        this.titlePaddingBottom = titlePaddingBottom;
    }

    public String getMembershipEmail() {
        return membershipEmail;
    }

    public void setMembershipEmail(String membershipEmail) {
        this.membershipEmail = membershipEmail;
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

    public ArrayList<AppSettingsDTO> getSettings() {
        return settings;
    }

    public float getFixedLeadingNarrow() {
        return fixedLeadingNarrow;
    }

    public DeviceCmyk getMipHeaderColor() {
        return mipHeaderColor;
    }

    public DeviceCmyk getMipEmailColor() {
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
}
