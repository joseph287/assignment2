package edu.montclair.mobilecomputing.mymac.assignment_1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import edu.montclair.mobilecomputing.mymac.assignment_1.util.NotesContainer;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.NotesDatabase;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.SecurePreferences;

/**
 * Created by Admin on 03-11-2017.  notes activity fro user and can re-access as needed
 */
public class NotesActivity extends AppCompatActivity {
    EditText txttitle, txtdescription;
    ImageView imgsave;
    NotesDatabase notesDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notesactivity);
        txttitle = (EditText) findViewById(R.id.txttitle);
        txtdescription = (EditText) findViewById(R.id.txtdescription);
        imgsave = (ImageView) findViewById(R.id.imgsave);
        notesDatabase = new NotesDatabase(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txttitle.getText().toString().equals("") && txtdescription.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter title and description", Toast.LENGTH_SHORT).show();
                } else {
                    NotesContainer notesContainer = new NotesContainer();
                    notesContainer.setTitle(txttitle.getText().toString());
                    notesContainer.setDescription(txtdescription.getText().toString());
                    notesContainer.setUser_id(SecurePreferences.getStringPreference(getApplicationContext(), "user_id"));
                    notesDatabase.addNotes(notesContainer);
                    txttitle.setText("");
                    txtdescription.setText("");
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
