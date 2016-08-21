package com.example.ishwar.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ishwar on 18/8/16.
 */
public class MovieFragment extends Fragment {

    private MovieAdapter movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        String sortedOrder;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortedOrder = sharedPreferences.getString(getString(R.string.sorted_order), getString(R.string.sPref_sort_most_popular));
        if (isConnected()) {
            FetchMovies fetchMovies = new FetchMovies();
            fetchMovies.execute( sortedOrder );
        }else {
            Toast.makeText(getContext(), "Please switch on your internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        movieAdapter = new MovieAdapter(
                getActivity(),
                new ArrayList<Movie>()
        );

        View rootView = inflater.inflate( R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.grid_for_banners);
        gridView.setAdapter( movieAdapter );

        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movieAdapter.getItem( position);
                Intent intent = new Intent( getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, movie);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class FetchMovies extends AsyncTask<String, Void, Movie[]> {

        private final String LOG_TAG = FetchMovies.class.getSimpleName();

        private Movie[] createObject(String jsonString) {

            final String TMDB_RESULTS = "results";
            final String TMDB_ORIGINAL_TITLE = "original_title";
            final String TMDB_BANNER_PATH = "poster_path";
            final String TMDB_RELEASE_DATE = "release_date";
            final String TMDB_USER_RATING = "vote_average";
            final String TMDB_OVERVIEW = "overview";

            try {
                JSONObject jsonObj = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObj.optJSONArray(TMDB_RESULTS);
                Movie moviesArray[] = new Movie[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String originalTitle = jsonObject.getString(TMDB_ORIGINAL_TITLE);
                    String bannerPath = jsonObject.getString(TMDB_BANNER_PATH);
                    String releaseDate = jsonObject.getString(TMDB_RELEASE_DATE);
                    float userRating = Float.parseFloat(jsonObject.getString(TMDB_USER_RATING));
                    String overView = jsonObject.getString(TMDB_OVERVIEW);

                    moviesArray[i] = new Movie(originalTitle, bannerPath, releaseDate, userRating, overView);
                }
                return moviesArray;
            } catch (JSONException j) {
                j.printStackTrace();
            }
            return null;
        }

        @Override
        protected Movie[] doInBackground(String... params) {

            HttpsURLConnection httpsURLConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;


            try {
                final String BASE_URL = "https://api.themoviedb.org/3/movie/" + params[0];
                final String APIID_PARAM = "api_key";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(APIID_PARAM, BuildConfig.TMDB_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.connect();

                StringBuffer stringBuffer = new StringBuffer();
                InputStream stream = httpsURLConnection.getInputStream();

                if (stream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(stream));

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    return null;
                }

                movieJsonStr = stringBuffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (httpsURLConnection != null) {
                    httpsURLConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error : closing stream", e);
                    }
                }
            }

            try {
                return createObject(movieJsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute (Movie[] movies){
            if (movies != null) {
                movieAdapter.clear();
                for (Movie movie : movies) {
                    movieAdapter.add(movie);
                }
            }
        }
    }
}
