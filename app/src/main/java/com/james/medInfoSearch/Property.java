package com.james.medInfoSearch;

//Base class to hold information about our property
public class Property {

    //property basics
    private String streetNumber;
    private String pregnantLevel;
    private String medEngName;
    private String medChiName;
    private String ingredient;
    private String dosageForm;
    private String genBaoNum;
    private String HealthDpt;
    private String ATC;
    private String indications;
    private String sideEffect;
    private String taboo;
    private String frequences;
    private String maxDosage;
    private String dosageAdjust;
    private String attention;
    private String interaction;
    private String injectionLimit;
    private String genBaoPay;
    private String seeDatail;
    //constructor
    public Property(String streetNumber, String pregnantLevel, String medEngName, String medChiName, String ingredient, String dosageForm, String genBaoNum, String HealthDpt, String ATC, String indications,
                    String sideEffect, String taboo,String frequences,String maxDosage,String dosageAdjust,String attention,String interaction,String injectionLimit,String genBaoPay,String seeDatail){
        this.streetNumber = streetNumber;
        this.pregnantLevel = pregnantLevel;
        this.medEngName = medEngName;
        this.medChiName = medChiName;
        this.ingredient = ingredient;
        this.dosageForm = dosageForm;
        this.genBaoNum = genBaoNum;
        this.HealthDpt = HealthDpt;
        this.ATC = ATC;
        this.indications = indications;
        this.sideEffect = sideEffect;
        this.taboo = taboo;
        this.frequences = frequences;
        this.maxDosage = maxDosage;
        this.dosageAdjust = dosageAdjust;
        this.attention = attention;
        this.interaction = interaction;
        this.injectionLimit = injectionLimit;
        this.genBaoPay = genBaoPay;
        this.seeDatail = seeDatail;
    }

    //getters
    public String getImageNumber() { return streetNumber; }
    public String getPregnantLevel() { return pregnantLevel; }
    public String getMedEngName() {return medEngName; }
    public String getMedChiName() {return medChiName; }
    public String getIngredient() {return ingredient; }
    public String getDosageForm() {return dosageForm; }
    public String getGenBaoNum() {return genBaoNum; }
    public String getHealthDpt() { return HealthDpt; }
    public String getATC(){ return ATC; }
    public String getIndications(){ return indications; }
    public String getSideEffect(){ return sideEffect; }
    public String getTaboo(){return taboo; }
    public String getFrequences() {return frequences; }
    public String getMaxDosage() {return maxDosage; }
    public String getDosageAdjust() {return dosageAdjust; }
    public String getAttention() { return attention; }
    public String getInteraction(){ return interaction; }
    public String getInjectionLimit(){ return injectionLimit; }
    public String getGenBaoPay(){ return genBaoPay; }
    public String getSeeDatail(){return seeDatail; }
}
