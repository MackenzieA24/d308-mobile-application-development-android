package com.example.d308app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308app.UI.Excursions;
import com.example.d308app.UI.Vacations;
import com.example.d308app.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM Excursion ORDER BY ID ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM Excursion WHERE vacationID=:vacationID ORDER BY ID ASC")
    List<Excursion> getAssociatedExcursions(int vacationID);

    @Query("SELECT * FROM Excursion WHERE vacayStartDate=:vacayStartDate ORDER BY ID ASC")
    List<Excursion> getAssociatedStarts(String vacayStartDate);
    @Query("SELECT * FROM Excursion WHERE vacayEndDate=:vacayEndDate ORDER BY ID ASC")
    List<Excursion> getAssociatedEnds(String vacayEndDate);
}
