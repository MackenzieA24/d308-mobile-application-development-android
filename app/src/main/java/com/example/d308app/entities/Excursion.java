package com.example.d308app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Excursion")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String excursionName;
    private double price;

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    private int vacationID;

    public Excursion(int ID, String excursionName, double price, int vacationID) {
        this.ID = ID;
        this.excursionName = excursionName;
        this.price = price;
        this.vacationID = vacationID;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
