package com.james.medInfoSearch;

import java.util.ArrayList;

/**
 * Created by 101716 on 2017/8/9.
 */

public class mediaican {
    private String medName;
    private String email;
    private String UID;
    private ArrayList<String> favorite = new ArrayList(){};

    public mediaican(String medName, ArrayList favorite) {
        this.medName = medName;
        this.favorite = favorite;
    }
    public mediaican(ArrayList favorite) {
        this.favorite = favorite;
    }
    public mediaican(String medName) {
        this.medName = medName;
    }
    public mediaican() {
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return medName;
    }

    public void setMedicine(String medName) {
        this.medName = medName;
    }

    public ArrayList<String> getArrayListName() {
        return favorite;
    }

    public ArrayList<String> setMedicineDetail(ArrayList favorite) {
        this.favorite = favorite;
        return favorite;
    }
}
