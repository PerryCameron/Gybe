package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkBuilderService {

//    SettingsRepository settingrepo;

//    ObjectRepository objRepo;

    PersonRepository personRepository;
//    ListRepository listRepo;

//    GeneralRepository genRepo;

    @Autowired
    public LinkBuilderService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

//    public String createGetRequestWithParameters(MembershipListDTO ml, int selectedYear)  {
//        FormSettingsDTO formSettings = settingrepo.getFormSettings();
//        List<PersonDTO> people = new ArrayList<>();
//        ArrayList<BoatDTO> boats = (ArrayList<BoatDTO>) listRepo.getBoatsByMsid(ml.getMs_id());
//        ArrayList<InvoiceItemDTO> invoiceItems = (ArrayList<InvoiceItemDTO>) listRepo.getInvoiceItems(ml.getMs_id(), selectedYear);
//        PersonDTO primaryMember = null;
//        PersonDTO secondaryMember = null;
//        PhoneDTO phone = null;
//
//        HttpUrl.Builder queryUrlBuilder = HttpUrl.get(formSettings.getForm_url() + formSettings.getForm_id()).newBuilder();
//        // Membership ID
//        queryUrlBuilder.addQueryParameter("memid", String.valueOf(ml.getMembership_id()));
//        // Membership Type
//        queryUrlBuilder.addQueryParameter("membershipType",String.valueOf(MembershipType.getByCode(ml.getMem_type())));
//        // Address line 1
//        queryUrlBuilder.addQueryParameter("address[addr_line1]", ml.getAddress());
//        // City
//        queryUrlBuilder.addQueryParameter("address[city]",ml.getCity());
//        // State
//        queryUrlBuilder.addQueryParameter("address[state]",ml.getState());
//        // Zip
//        queryUrlBuilder.addQueryParameter("address[postal]",ml.getZip());
//        // Work Credits
//        queryUrlBuilder.addQueryParameter("workCredit", getInvoiceItemValue(invoiceItems, "Work Credits"));
//        // Winter Storage
//        queryUrlBuilder.addQueryParameter("winterStorage", getInvoiceItemQty(invoiceItems, "Winter Storage"));
//        // get any additional credit
//        queryUrlBuilder.addQueryParameter("additionalCredit", getInvoiceItemValue(invoiceItems, "Other Credit"));
//        // get any additional fee
//        queryUrlBuilder.addQueryParameter("otherFee", getInvoiceItemValue(invoiceItems, "Other Fee"));
//        // get initiation fee
//        queryUrlBuilder.addQueryParameter("initiation", getInvoiceItemValue(invoiceItems, "Initiation"));
//
//        queryUrlBuilder.addQueryParameter("positionCredit", getInvoiceItemValue(invoiceItems, "Position Credit"));
//
//        // get all information related to primary member
//        if(genRepo.personExistsByType(String.valueOf(ml.getMs_id()),"1")) {
//            //  get the primary member
//            primaryMember = objRepo.getPerson(ml.getMs_id(), 1);
//            //  add primary member to our list
////            people.add(primaryMember);
//
//            queryUrlBuilder.addQueryParameter("primaryMember[first]",primaryMember.getF_name());
//
//            queryUrlBuilder.addQueryParameter("primaryMember[last]",primaryMember.getL_name());
//
//            queryUrlBuilder.addQueryParameter("primaryOccupation",primaryMember.getOccupation());
//
//            queryUrlBuilder.addQueryParameter("primaryCompany",primaryMember.getBusiness());
//
//            setJotFormDate(queryUrlBuilder, primaryMember.getBirthday(), "primaryMemberBirthday");
//
//            if(genRepo.emailExists(primaryMember))
//                queryUrlBuilder.addQueryParameter("primaryemail", objRepo.getEmail(primaryMember).getEmail());
//
//            if(genRepo.phoneOfTypeExists(String.valueOf(primaryMember.getP_id()),"C"))
//                phone = objRepo.getPhoneByType(primaryMember.getP_id(),"C");
//                queryUrlBuilder.addQueryParameter("primaryPhone", phone.getPhone() );
//
//            if(genRepo.phoneOfTypeExists(String.valueOf(primaryMember.getP_id()),"E")) {
//                phone = objRepo.getPhoneByType(primaryMember.getP_id(), "E");
//                queryUrlBuilder.addQueryParameter("emergencyPhone", phone.getPhone());
//            }
//        }
//
//        // get all information related to secondary member
//        if(genRepo.personExistsByType(String.valueOf(ml.getMs_id()),"2")) {
//            // get secondary member
//            secondaryMember = objRepo.getPerson(ml.getMs_id(), 2);
//            // add secondary member to our list
////            people.add(secondaryMember);
//            // does member have spouse
//            queryUrlBuilder.addQueryParameter("haveSpouse","Yes");
//            queryUrlBuilder.addQueryParameter("spouseName[first]",secondaryMember.getF_name());
//            queryUrlBuilder.addQueryParameter("spouseName[last]",secondaryMember.getL_name());
//            queryUrlBuilder.addQueryParameter("spouseOccupation",secondaryMember.getOccupation());
//            queryUrlBuilder.addQueryParameter("spouseCompany",secondaryMember.getBusiness());
//            setJotFormDate(queryUrlBuilder, secondaryMember.getBirthday(), "secondaryMemberBirthday");
//            if(genRepo.emailExists(secondaryMember))
//                queryUrlBuilder.addQueryParameter("spouseEmail",objRepo.getEmail(secondaryMember).getEmail());
//
//            if(genRepo.phoneOfTypeExists(String.valueOf(secondaryMember.getP_id()),"C"))
//                phone = objRepo.getPhoneByType(secondaryMember.getP_id(),"C");
//                queryUrlBuilder.addQueryParameter("spousePhone",phone.getPhone());
//
//        } else {
//            queryUrlBuilder.addQueryParameter("haveSpouse","No");
//        }
//        // lets get the children now
//        if(genRepo.personExistsByType(String.valueOf(ml.getMs_id()),"3")) {
//            people = listRepo.getChildrenByMsId(ml.getMs_id());
//            queryUrlBuilder.addQueryParameter("numberOfDependents", String.valueOf(people.size()));
//            for(int i = 0; i < people.size(); i++) {
//                queryUrlBuilder.addQueryParameter("dependentName"+(i+1)+"[first]",people.get(i).getF_name());
//                queryUrlBuilder.addQueryParameter("dependentName"+(i+1)+"[last]",people.get(i).getL_name());
//                setJotFormDate(queryUrlBuilder, people.get(i).getBirthday(), "dependentBirthday"+(i+1));
//            }
//            // TODO
//        }
//        // lets find if membership has officer
//        if(genRepo.membershipHasOfficer(ml.getMs_id(),selectedYear)) {
//            queryUrlBuilder.addQueryParameter("positionCredit", "Yes");
//        } else {
//            queryUrlBuilder.addQueryParameter("positionCredit", "No");
//        }
//        // lets find out about the slip
//        if(genRepo.slipExistsByMsid(ml.getMs_id())) {
//            SlipDTO slip = objRepo.getSlipFromMsid(ml.getMs_id());
//            queryUrlBuilder.addQueryParameter("slipNumber", slip.getSlip_num());
//        } else {
//            queryUrlBuilder.addQueryParameter("slipNumber","");
//        }
//
//        queryUrlBuilder.addQueryParameter("numberOfBoats", String.valueOf(genRepo.getNumberOfBoats(ml.getMs_id())));
//
//        addMembershipBoats(queryUrlBuilder, boats);
//
//        return queryUrlBuilder.toString();
//    }
//
//    private String getInvoiceItemValue(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName) {
//        InvoiceItemDTO invoiceItemDTO = invoiceItems.stream()
//                .filter(item -> item.getField_Name().equals(fieldName)).findFirst().orElse(null);
//        return  invoiceItemDTO.getValue();
//    }
//
//    private String getInvoiceItemQty(ArrayList<InvoiceItemDTO> invoiceItems, String fieldName) {
//        InvoiceItemDTO invoiceItemDTO = invoiceItems.stream()
//                .filter(item -> item.getField_Name().equals(fieldName)).findFirst().orElse(null);
//        return String.valueOf(invoiceItemDTO.getQty());
//    }
//
//    private void addMembershipBoats(HttpUrl.Builder queryUrlBuilder, ArrayList<BoatDTO> boats) {
//        if(boats.size() != 0)
//        for(int i =1; i < boats.size() + 1; i++) {
//            queryUrlBuilder.addQueryParameter("addBoat" + i, "Yes");
//            if (boats.get(i -1) != null)
//            queryUrlBuilder.addQueryParameter("boat_id" + i, String.valueOf(boats.get(i-1).getBoat_id()));
//            queryUrlBuilder.addQueryParameter("boatName" + i,boats.get(i-1).getBoat_name());
//            queryUrlBuilder.addQueryParameter("registrationNumber" + i,boats.get(i-1).getRegistration_num());
//            queryUrlBuilder.addQueryParameter("manufacturer" + i,boats.get(i-1).getManufacturer());
//            queryUrlBuilder.addQueryParameter("manufactureYear" + i,boats.get(i-1).getManufacture_year());
//            queryUrlBuilder.addQueryParameter("model" + i,boats.get(i-1).getModel());
//            queryUrlBuilder.addQueryParameter("length" + i,boats.get(i-1).getLength());
//            queryUrlBuilder.addQueryParameter("draft" + i,boats.get(i-1).getDraft());
//            queryUrlBuilder.addQueryParameter("sail" + i,boats.get(i-1).getSail_number());
//            queryUrlBuilder.addQueryParameter("hasTrailer" + i,trueToYes(boats.get(i-1).getHasTrailer()));
//         }
//    }
//
//    private String trueToYes(Boolean answer) {
//        String answerIsTrue = "Yes";
//        if(!answer)
//        answerIsTrue = "No";
//        return answerIsTrue;
//    }
//
//    private void setJotFormDate(HttpUrl.Builder queryUrlBuilder, String date, String keyName) {
//        String birthDay[] = date.split("-");
//        queryUrlBuilder.addQueryParameter(keyName + "[day]", birthDay[2]);
//        queryUrlBuilder.addQueryParameter(keyName + "[month]", birthDay[1]);
//        queryUrlBuilder.addQueryParameter(keyName + "[year]", birthDay[0]);
//    }

}
