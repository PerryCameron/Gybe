package com.ecsail.Gybe.dto;



public class InvoiceWithMemberInfoDTO extends InvoiceDTO {

    private int membershipId;  // Member ID used in real life
    private String f_name;
    private String l_name;

    public InvoiceWithMemberInfoDTO(Integer id, Integer msId, Integer year, String paid, String total, String credit, String balance, Integer batch, Boolean committed, Boolean closed, Boolean supplemental, String maxCredit, int membershipId, String f_name, String l_name) {
        super(id, msId, year, paid, total, credit, balance, batch, committed, closed, supplemental, maxCredit);
        this.membershipId = membershipId;
        this.f_name = f_name;
        this.l_name = l_name;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    @Override
    public String toString() {
        return "InvoiceWithMemberInfoDTO{" +
                "membershipId=" + membershipId +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                "} " + super.toString();
    }
}
