package com.example.d308app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Excursion")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String excursionName;
    private String excursionDate;

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    private int vacationID;

    private String vacayStartDate;
    private String vacayEndDate;

    public String getVacayStartDate() {
        return vacayStartDate;
    }

    public void setVacayStartDate(String vacayStartDate) {
        this.vacayStartDate = vacayStartDate;
    }

    public String getVacayEndDate() {
        return vacayEndDate;
    }

    public void setVacayEndDate(String vacayEndDate) {
        this.vacayEndDate = vacayEndDate;
    }

    public Excursion(int ID, String excursionName, String excursionDate, int vacationID, String vacayStartDate, String vacayEndDate) {
        this.ID = ID;
        this.excursionName = excursionName;
        this.excursionDate = excursionDate;
        this.vacationID = vacationID;
        this.vacayStartDate = vacayStartDate;
        this.vacayEndDate = vacayEndDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }
}
