package com.ftn.krt.mdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.HashMap;

/**
 * Created by nikola on 6/2/15.
 */
public class MovieAdapter extends BaseAdapter {

    private HashMap<Long, Movie> mMovies;
    private Context mContext;
    private Long[] mKeyset;

    public MovieAdapter(Context context) {
        mContext = context;
        mMovies = new HashMap<>();
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int pos) {
        Movie m =  mMovies.get(mKeyset[pos]);
        return m;
    }

    @Override
    public long getItemId(int position) {
        return ((Movie)getItem(position)).hash;
    }

    public void update(Movie[] movies) {
        if(movies == null) {
            return;
        }

        mMovies.clear();
        for(Movie movie : movies) {
            mMovies.put(movie.hash, movie);
        }

        mKeyset = mMovies.keySet().toArray(new Long[mMovies.size()]);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.movie_row, null);

            ViewHolder holder = new ViewHolder();
            holder.mTitle = (TextView)  view.findViewById(R.id.title);
            holder.mEditor = (TextView) view.findViewById(R.id.editor);
            holder.mYear = (TextView) view.findViewById(R.id.year);
            view.setTag(holder);
        }

        Movie movie = (Movie) getItem(position);

        if(movie == null) return view;

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.mTitle.setText(movie.title);
        holder.mEditor.setText(movie.editor);
        holder.mYear.setText(Integer.toString(movie.year));

        return view;
    }

    private class ViewHolder {
        public TextView mTitle = null;
        public TextView mEditor = null;
        public TextView mYear = null;
    }
}
