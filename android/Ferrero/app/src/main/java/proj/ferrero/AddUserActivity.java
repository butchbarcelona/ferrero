package proj.ferrero;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import proj.ferrero.models.User;

public class AddUserActivity extends AppCompatActivity {

    EditText etName, etTag, etAge, etBday, etBlood
            , etAllergy, etMedCond, etContactPerson, etContactNumber;
    Button btnSave;

    SqlDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        btnSave = (Button) findViewById(R.id.btn_save);
        etName = (EditText) findViewById(R.id.et_name);
        etTag = (EditText) findViewById(R.id.et_tag);
        etBday = (EditText) findViewById(R.id.et_bday);
      etBday.setInputType(InputType.TYPE_NULL);
      etBday.setFocusableInTouchMode(false);
      etBday.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Calendar newCalendar = Calendar.getInstance();
          DatePickerDialog datePickerDialog = new DatePickerDialog(
            AddUserActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear
              , int dayOfMonth) {
              Calendar newDate = Calendar.getInstance();
              newDate.set(year, monthOfYear, dayOfMonth);
              SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
              etBday.setText(formatter.format(newDate.getTime()));
            }

          }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH)
            , newCalendar.get(Calendar.DAY_OF_MONTH));

          datePickerDialog.show();
        }
      });
        etAge = (EditText) findViewById(R.id.et_age);
        etBlood = (EditText) findViewById(R.id.et_bloodtype);
        etAllergy = (EditText) findViewById(R.id.et_allergies);
        etMedCond = (EditText) findViewById(R.id.et_med_conditions);
        etContactPerson = (EditText) findViewById(R.id.et_contact_person);
        etContactNumber = (EditText) findViewById(R.id.et_contact_number);

        dbHelper = new SqlDatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              String tag = etTag.getText().toString();
                if(dbHelper.getAllUsersWithTag(tag).size() > 0){

                  Util.showDialog(AddUserActivity.this, "There's a user with this NFC ID", "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                    }
                  });

                }else {

                  String strAge = etAge.getText().toString();
                  int age = strAge.isEmpty()?0:Integer.parseInt(strAge);
                  dbHelper.createUser(new User(-1,tag,
                    etName.getText().toString(),
                    1000,age,
                    etBday.getText().toString(),
                    etBlood.getText().toString(),
                    etAllergy.getText().toString(),
                    etMedCond.getText().toString(),
                    etContactPerson.getText().toString(),
                    etContactNumber.getText().toString()));

                  finish();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_user, menu);
        return true;
    }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      // Use the current date as the default date in the picker
      final Calendar c = Calendar.getInstance();
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH);
      int day = c.get(Calendar.DAY_OF_MONTH);

      // Create a new instance of DatePickerDialog and return it
      return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
      // Do something with the date chosen by the user
    }
  }
}
