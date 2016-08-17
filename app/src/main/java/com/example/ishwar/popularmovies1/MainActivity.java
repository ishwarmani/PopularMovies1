package com.example.ishwar.popularmovies1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String items[] = {"http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                "https://image.tmdb.org/t/p/original/avbTSHsx44zjrH7US5P2X0sLJiY.jpg",
                "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                "https://image.tmdb.org/t/p/original/3rsa7uNw9uL4HMyBdFeJTSYQApO.jpg",
                "https://image.tmdb.org/t/p/original/4xsLMMxjhlpb0MecUkGTRoZsb8b.jpg",
                "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg",
                "https://image.tmdb.org/t/p/original/3rsa7uNw9uL4HMyBdFeJTSYQApO.jpg",
                "https://image.tmdb.org/t/p/original/4xsLMMxjhlpb0MecUkGTRoZsb8b.jpg",
                "https://image.tmdb.org/t/p/original/avbTSHsx44zjrH7US5P2X0sLJiY.jpg",
                "https://image.tmdb.org/t/p/original/4xsLMMxjhlpb0MecUkGTRoZsb8b.jpg",
                "https://image.tmdb.org/t/p/original/avbTSHsx44zjrH7US5P2X0sLJiY.jpg",
        };
        GridViewAdapter gridAdapter;
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item,R.id.image, Arrays.asList(items));


        GridView gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(gridAdapter);

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
}

