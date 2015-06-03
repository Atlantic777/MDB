package com.ftn.krt.mdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity
        implements View.OnClickListener,
                    AdapterView.OnItemClickListener,
                    AdapterView.OnItemLongClickListener {

    private final String TAG = "MAIN_ACTIVITY";
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
        mMoviesList.setOnItemClickListener(this);
        mMoviesList.setOnItemLongClickListener(this);

        refreshData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, MovieEdit.class);
        i.putExtra(MovieEdit.KEY_MOVIE_HASH, id);
        startActivity(i);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        mDbHelper.delete(id);
        refreshData();
        return true;
    }

    private void refreshData() {
        Movie[] movies = mDbHelper.readMovies();
        mAdapter.update(movies);
    }
}
