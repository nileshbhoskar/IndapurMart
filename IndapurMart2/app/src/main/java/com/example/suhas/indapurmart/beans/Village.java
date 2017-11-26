package com.example.suhas.indapurmart.beans;

/**
 * Created by bhoskar1 on 25/8/17.
 */

public class Village {
    private String VillageID;
    private String enVillageName;
    private String marVillageName;
    private String _id;
    private String talukaID;
    private String isActive;


    public String getVillageID() {
        return VillageID;
    }

    public void setVillageID(String villageID) {
        VillageID = villageID;
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

    @Override
    public String toString() {
        return "VillageData{" +
                "VillageID='" + VillageID + '\'' +
                ", enVillageName='" + enVillageName + '\'' +
                ", marVillageName='" + marVillageName + '\'' +
                ", _id='" + _id + '\'' +
                ", talukaID='" + talukaID + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
