package com.ecsail.Gybe.wrappers;

import com.ecsail.Gybe.dto.BoardPositionDTO;
import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.dto.SlipStructureDTO;

import java.util.ArrayList;

public class DirectoryDataWrapper {
    ArrayList<MembershipInfoDTO> membershipInfoDTOS;
    CommodoreMessageDTO commodoreMessage;
    ArrayList<BoardPositionDTO> positionData;
    ArrayList<SlipStructureDTO> slipStructureDTOS;

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
