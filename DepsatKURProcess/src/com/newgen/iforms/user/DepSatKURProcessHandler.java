package com.newgen.iforms.user;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.webclientcodebase.model.WorkdeskModel;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DepSatKURProcessHandler implements IFormServerEventHandler {
    @Override
    public void beforeFormLoad(FormDef objFormDef, IFormReference objReference) {
    }

    @Override
    public String setMaskedValue(String FieldName, String currentVal) {
        return null;
    }

    @Override
    public JSONArray executeEvent(FormDef objFormDef, IFormReference objReference, String controlId, String eventType) {
        return null;
    }

    @Override
    public String executeServerEvent(IFormReference objReference, String controlId, String eventType, String data) {

        if(data.equals("getBranchName")){
            if(controlId.equals("")){
                return "control Id shouldn't be empty";
            }
            
            try {
                List<ArrayList<String >> branchName = objReference.getDataFromDB(
                        "SELECT brnc.name FROM mst_user AS usr JOIN mst_branch AS brnc ON brnc.id = usr.branch_id WHERE usr.username='"+controlId+"'"
                );
                //objReference.setValue("DepSatTrxLeadsApplicant.branchname", branchName.get(0).get(0));
                return branchName;
            } catch (Exception err){
                return "error : " + err;
            }

        } else {
            String[] controls = controlId.split(",");
            if(controls.length!=2){
                return "control Id must contain two parameters that separated by comma (,)";
            }
            try {
                String nameId = "";
                String tableNameTarget = "";
                String tableNameJoin = "";
                String filter = controls[0];
                String comboId = controls[1];

                if (eventType.equals("cityDropdown")) {
                    nameId = "province";
                    tableNameTarget = "mstcity";
                    tableNameJoin = "mstprovince";

                } else if (eventType.equals("districtDropdown")) {
                    nameId = "city";
                    tableNameTarget = "mstdistrict";
                    tableNameJoin = "mstcity";


                } else if (eventType.equals("villageDropdown")) {
                    nameId = "district";
                    tableNameTarget = "mstvillage";
                    tableNameJoin = "mstdistrict";

                }

                var valueFilter = objReference.getValue(filter);

                List<ArrayList<String>> values = objReference.getDataFromDB(
                        "select mst1.name from " + tableNameTarget + " as mst1 full join " + tableNameJoin + " as mst2 on mst2.id = mst1." + nameId + "id" +
                                " where mst2.name = '" + valueFilter + "' "
                );

                for(int i = 0; i < values.size(); i++) {
                    objReference.addItemInCombo(comboId, values.get(i).get(0), values.get(i).get(0));
                }

                return values.toString();

            } catch (Exception err) {
                return "error : "+err;
            }
        }
    }

    @Override
    public String onChangeEventServerSide(IFormReference objReference, String controlId) {
        return null;
    }

    @Override
    public JSONArray validateSubmittedForm(FormDef objFormDef, IFormReference objReference, String eventType) {
        return null;
    }

    @Override
    public String executeCustomService(FormDef objFormDef, IFormReference objReference, String InputXml, String controlName, String eventType) {
        return null;
    }

    @Override
    public String getCustomFilterXML(FormDef objFormDef, IFormReference objReference, String variableName) {
        return null;
    }

    @Override
    public String generateHTML(EControl objControl) {
        return null;
    }

    @Override
    public String postHookExportToPDF(IFormReference objReference, File f) {
        return null;
    }

    @Override
    public String introduceWorkItemInWorkFlow(IFormReference objReference, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public String introduceWorkItemInWorkFlow(IFormReference objReference, HttpServletRequest request, HttpServletResponse response, WorkdeskModel wdmodel) {
        return null;
    }

    @Override
    public void updateDataInWidget(IFormReference objReference, String WidgetName) {

    }

    @Override
    public String validateDocumentConfiguration(String controlId, String docType, File f, Locale locale) {
        return null;
    }

    @Override
    public void postHookOnDocumentUpload(IFormReference objReference, String controlId, String docType, File f, int docImageIndex) {

    }

    @Override
    public void postHookOnDocumentOperations(IFormReference objReference, String controlId, String docType, int docImageIndex, String operationType) {

    }

    @Override
    public boolean introduceWorkItemInSpecificProcess(IFormReference objReference, String processName) {
        return false;
    }
}
