package com.james.medInfoSearch;

import java.util.ArrayList;

/**
 * Created by 101716 on 2017/8/9.
 */

public class mediaican {
    private String medName;
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
