package com.example.coco.model;

public class DiseaseData {


    private String diseaseImage;
    private String diseaseName;
    private String diseaseTreatement;

    private String key;

    public DiseaseData() {

    }

    public DiseaseData(String diseaseImage, String diseaseName, String diseaseTreatement) {
        this.diseaseImage = diseaseImage;
        this.diseaseName = diseaseName;
        this.diseaseTreatement = diseaseTreatement;
    }

    public String getDiseaseImage() {
        return diseaseImage;
    }

    public void setDiseaseImage(String diseaseImage) {
        this.diseaseImage = diseaseImage;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseTreatement() {
        return diseaseTreatement;
    }

    public void setDiseaseTreatement(String diseaseTreatement) {
        this.diseaseTreatement = diseaseTreatement;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
