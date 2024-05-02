package com.example.d308app.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308app.R;
import com.example.d308app.database.Repository;
import com.example.d308app.entities.Excursion;
import com.example.d308app.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    String name;
    String date;
    int ID;
    int vacationID;
    String vacayStartDate;
    String vacayEndDate;
    EditText editName;
    EditText editDate;
    Repository repository;

    DatePickerDialog.OnDateSetListener myDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateLabel();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
            String formattedDate = sdf.format(myCalendar.getTime());

            if (editDate != null && !validateDateFormat(formattedDate)) {
                Toast.makeText(ExcursionDetails.this, "Invalid date format", Toast.LENGTH_SHORT).show();
            } else {
                editDate.setText(formattedDate);
            }
        }
    };
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

            editName = findViewById(R.id.titletext);
            editDate = findViewById(R.id.date);
            ID = getIntent().getIntExtra("id", -1);
            vacationID = getIntent().getIntExtra("VacationID", -1);
            vacayStartDate = getIntent().getStringExtra("VacayStart");
            vacayEndDate = getIntent().getStringExtra("VacayEnd");
            name = getIntent().getStringExtra("name");
            date = getIntent().getStringExtra("date");
            editName.setText(name);
            editDate.setText(date);

        if (vacationID != -1) {
            repository.getVacationById(vacationID).observe(this, vacation -> {
                if (vacation != null) {

                    vacayStartDate = vacation.getStartDate();
                    vacayEndDate = vacation.getEndDate();
                }
            });
        }

              String myFormat = "MM/dd/yy";
              SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
              String currentDate = sdf.format(new Date());

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(editDate);
            }
        });

    }
    private void updateDateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendar.getTime()));
    }
    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
                        String formattedDate = sdf.format(selectedDate.getTime());
                        if (editText == editDate && !validateDateFormat(formattedDate)) {
                            Toast.makeText(ExcursionDetails.this, "Invalid date format", Toast.LENGTH_SHORT).show();
                        } else {
                            editText.setText(formattedDate);
                        }
                    }
                },
                year, month, dayOfMonth
        );
        datePickerDialog.show();
    }
    private boolean validateDateFormat(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        sdf.setLenient(false);
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    private boolean isExcursionDateValid(String excursionDate, String vacayStartDate, String vacayEndDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        try {
            Date excursion = sdf.parse(excursionDate);
            Date startDate = sdf.parse(vacayStartDate);
            Date endDate = sdf.parse(vacayEndDate);
            return excursion.compareTo(startDate) >= 0 && excursion.compareTo(endDate) <= 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Excursion excursion;
        String dateInput = editDate.getText().toString();
        if (item.getItemId() == R.id.excursionsave) {
            if (!validateDateFormat(dateInput)) {
                Log.d("DateValidation", "Invalid date format: " + dateInput);
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Log.d("DateValidation", "Valid date format: " + dateInput);
            }
            if (ID == -1) {
//                if (repository.getmAllExcursions().size() == 0) ID = 1;
//                else
//                    ID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getID() + 1;
                excursion = new Excursion(0, editName.getText().toString(), editDate.getText().toString(), vacationID, vacayStartDate, vacayEndDate);
                if (!isExcursionDateValid(dateInput, vacayStartDate, vacayEndDate)) {
                    Toast.makeText(this, "Excursion date must be within vacation dates", Toast.LENGTH_SHORT).show();
                    return true;
                }
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursion(ID, editName.getText().toString(), editDate.getText().toString(), vacationID, vacayStartDate, vacayEndDate);
                if (!isExcursionDateValid(dateInput, vacayStartDate, vacayEndDate)) {
                    Toast.makeText(this, "Excursion date must be within vacation dates", Toast.LENGTH_SHORT).show();
                    return true;
                }
                repository.update(excursion);
                this.finish();
            }
        } else if (item.getItemId() == R.id.excursiondelete) {
            excursion = new Excursion(ID, editName.getText().toString(), editDate.getText().toString(), vacationID, vacayStartDate, vacayEndDate);
            repository.delete(excursion);
            this.finish();
        }
        if (item.getItemId() == R.id.notify) {
            String dateFromScreen = editDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            intent.putExtra("key", editName.getText().toString() + " is starting!");
            PendingIntent sender=PendingIntent.getBroadcast(ExcursionDetails.this,++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger,sender);
            return true;
        }
        return true;
    }
}