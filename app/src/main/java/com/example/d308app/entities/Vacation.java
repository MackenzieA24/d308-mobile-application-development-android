package com.example.d308app.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Vacation")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String vacationName;
    private double price;
    private int excursionID;

    public Vacation(int ID, String vacationName, double price) {
        this.ID = ID;
        this.vacationName = vacationName;
        this.price = price;
        this.excursionID = excursionID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }
}
