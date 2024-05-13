package com.example.d308app.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308app.UI.Vacations;
import com.example.d308app.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM Vacation ORDER BY ID ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM Vacation WHERE ID = :id")
    LiveData<Vacation> getVacationById(int id);

    @Query("SELECT * FROM Vacation WHERE vacationName LIKE :query")
    LiveData<List<Vacation>> searchVacationsByName(String query);
}
