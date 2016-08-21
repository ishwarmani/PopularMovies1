package com.example.ishwar.popularmovies1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ishwar on 3/6/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie>{

    private static class ViewHolder {
        private ImageView imageView;
    }

    public MovieAdapter(Context context, List<Movie> movieList) {
        super(context,R.layout.grid_item,movieList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Movie movie = getItem(position);
        String url = movie.getBannerPath();

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.grid_item,parent,false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.movie_banner);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).
                load(url).
                placeholder(R.drawable.placeholder).
                into(viewHolder.imageView);
        return convertView;
    }
}
