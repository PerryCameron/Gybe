package com.ecsail.Gybe.wrappers;

import com.ecsail.Gybe.dto.*;

import java.util.ArrayList;

public class DirectoryDataWrapper {
    ArrayList<MembershipInfoDTO> membershipInfoDTOS;
    CommodoreMessageDTO commodoreMessage;
    ArrayList<BoardPositionDTO> positionData;
    ArrayList<SlipStructureDTO> slipStructureDTOS;
    ArrayList<AppSettingsDTO> appSettingsDTOS;

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
}
