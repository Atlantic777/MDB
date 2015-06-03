package com.ftn.krt.mdb;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MovieEdit extends Activity implements View.OnClickListener {
    protected final static String KEY_MOVIE_HASH = "HASH";
    private final int MODE_ADD = 0;
    private final int MODE_EDIT = 1;
    private int mMode;
    private MovieDB movieDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_edit);

        Button bOk = (Button)findViewById(R.id.ok_button);
        Button bCancel = (Button)findViewById(R.id.cancel_button);

        bOk.setOnClickListener(this);
        bCancel.setOnClickListener(this);

        movieDB = new MovieDB(this);

        Intent i = getIntent();
        if(i.hasExtra(KEY_MOVIE_HASH)) {
            mMode = MODE_EDIT;
            long h = i.getLongExtra(KEY_MOVIE_HASH, -2);
            Toast.makeText(this, Long.toString(h), Toast.LENGTH_SHORT).show();
        }
        else {
            mMode = MODE_ADD;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_edit, menu);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ok_button) {
            String title = getInputData(R.id.in_title);
            String editor = getInputData(R.id.in_editor);
            int year = Integer.parseInt(getInputData(R.id.in_year));

            if(mMode == MODE_ADD) {
                Movie m = new Movie(title, editor, year);
                movieDB.insert(m);
            }

            finish();
        } else if (v.getId() == R.id.cancel_button) {
            finish();
        }

    }

    private String getInputData(int editId) {
        EditText edit = (EditText) findViewById(editId);
        return edit.getText().toString();
    }
}
