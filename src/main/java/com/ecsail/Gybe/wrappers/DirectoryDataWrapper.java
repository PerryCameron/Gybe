package com.ecsail.Gybe.wrappers;

import com.ecsail.Gybe.dto.*;

import java.util.ArrayList;

public class DirectoryDataWrapper {
    private ArrayList<MembershipInfoDTO> membershipInfoDTOS;
    private CommodoreMessageDTO commodoreMessage;
    private ArrayList<BoardPositionDTO> positionData;
    private ArrayList<SlipStructureDTO> slipStructureDTOS;
    private ArrayList<SlipInfoDTO> slipInfoDTOS;
    private ArrayList<AppSettingsDTO> appSettingsDTOS;
    private ArrayList<PersonListDTO> personListDTOS = new ArrayList<>();
    private String fontPath;

    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    public ArrayList<AppSettingsDTO> getAppSettingsDTOS() {
        return appSettingsDTOS;
    }

    public void setAppSettingsDTOS(ArrayList<AppSettingsDTO> appSettingsDTOS) {
        this.appSettingsDTOS = appSettingsDTOS;
    }

    public ArrayList<MembershipInfoDTO> getMembershipInfoDTOS() {
        return membershipInfoDTOS;
    }

    public void setMembershipInfoDTOS(ArrayList<MembershipInfoDTO> membershipInfoDTOS) {
        this.membershipInfoDTOS = membershipInfoDTOS;
    }

    public CommodoreMessageDTO getCommodoreMessage() {
        return commodoreMessage;
    }

    public void setCommodoreMessage(CommodoreMessageDTO commodoreMessage) {
        this.commodoreMessage = commodoreMessage;
    }

    public ArrayList<BoardPositionDTO> getPositionData() {
        return positionData;
    }

    public void setPositionData(ArrayList<BoardPositionDTO> positionData) {
        this.positionData = positionData;
    }

    public ArrayList<SlipStructureDTO> getSlipStructureDTOS() {
        return slipStructureDTOS;
    }

    public void setSlipStructureDTOS(ArrayList<SlipStructureDTO> slipStructureDTOS) {
        this.slipStructureDTOS = slipStructureDTOS;
    }

    public ArrayList<SlipInfoDTO> getSlipInfoDTOS() {
        return slipInfoDTOS;
    }

    public void setSlipInfoDTOS(ArrayList<SlipInfoDTO> slipInfoDTOS) {
        this.slipInfoDTOS = slipInfoDTOS;
    }

    public ArrayList<PersonListDTO> getPersonListDTOS() {
        return personListDTOS;
    }

    public void setPersonListDTOS(ArrayList<PersonListDTO> personListDTOS) {
        this.personListDTOS = personListDTOS;
    }
}
