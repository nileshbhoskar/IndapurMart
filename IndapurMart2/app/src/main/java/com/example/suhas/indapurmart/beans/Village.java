package com.example.suhas.indapurmart.beans;

/**
 * Created by bhoskar1 on 25/8/17.
 */

public class Village {
    private String villageID;
    private String enVillageName;
    private String marVillageName;
    private String _id;
    private String talukaID;
    private String isActive;
    private boolean isSelected;

    public String getVillageID() {
        return villageID;
    }

    public void setVillageID(String villageID) {
        this.villageID = villageID;
    }

    public String getEnVillageName() {
        return enVillageName;
    }

    public void setEnVillageName(String enVillageName) {
        this.enVillageName = enVillageName;
    }

    public String getMarVillageName() {
        return marVillageName;
    }

    public void setMarVillageName(String marVillageName) {
        this.marVillageName = marVillageName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTalukaID() {
        return talukaID;
    }

    public void setTalukaID(String talukaID) {
        this.talukaID = talukaID;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "VillageData{" +
                "villageID='" + villageID + '\'' +
                ", enVillageName='" + enVillageName + '\'' +
                ", marVillageName='" + marVillageName + '\'' +
                ", _id='" + _id + '\'' +
                ", talukaID='" + talukaID + '\'' +
                ", isActive='" + isActive + '\'' +
                ", isSelected='" + isSelected + '\'' +
                '}';
    }
}
