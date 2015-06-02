package com.ftn.krt.mdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements View.OnClickListener {
    ListView mMoviesList;
    MovieAdapter mAdapter;
    MovieDB mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        mDbHelper = new MovieDB(this);
        mAdapter = new MovieAdapter(this);

        mMoviesList = (ListView)findViewById(R.id.movies_list);
        mMoviesList.setAdapter(mAdapter);

        Movie a = new Movie("first movie", "first editor", 2014, 12);
        Movie b = new Movie("second movie", "second editor", 2015, 13);

        mDbHelper.insert(a);
        mDbHelper.insert(b);

        Movie[] mList = mDbHelper.readMovies();
        mAdapter.update(mList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Movie[] movies = mDbHelper.readMovies();
        mAdapter.update(movies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        startActivity(new Intent(this, MovieEdit.class));
    }
}
