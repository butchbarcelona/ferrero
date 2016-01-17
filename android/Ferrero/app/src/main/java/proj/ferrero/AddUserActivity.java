package proj.ferrero;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

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

                long bday = (new Date(etBday.getText().toString())).getTime();

                dbHelper.createUser(new User(etTag.getText().toString(),
                        etName.getText().toString(),
                        1000,
                        Integer.parseInt(etAge.getText().toString()),
                        bday,
                                etBlood.getText().toString(),
                                etAllergy.getText().toString(),
                                etMedCond.getText().toString(),
                                etContactPerson.getText().toString(),
                                etContactNumber.getText().toString()));
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
