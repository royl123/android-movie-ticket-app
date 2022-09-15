package com.example.Luengas_Movie_Tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// This file calls MovieDB API and parses the json from the GET url
// Follows standard AsyncTask structure
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static String jsonUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=4a09c2fe3c33d3f20206e97f53a8dab5&page=1";

    List<MovieModel> moviesNowPlaying;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesNowPlaying = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        GetData getData = new GetData();
        getData.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class GetData extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... strings) {
            String current="";
            try{
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(jsonUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while(data != -1){
                        current += (char) data;
                        data = isr.read();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            return current;
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    MovieModel model = new MovieModel();
                    model.setTitle(jsonObject1.getString("original_title"));
                    model.setSynopsis(jsonObject1.getString("overview"));
                    model.setImg(jsonObject1.getString("poster_path"));
                    model.setRd(jsonObject1.getString("release_date"));

                    moviesNowPlaying.add(model);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(moviesNowPlaying);
        }
    }
    private void PutDataIntoRecyclerView(List<MovieModel> movieList) {
        Adapter adapter = new Adapter(this, movieList);
        recyclerView.setLayoutManager((new LinearLayoutManager((this))));
        recyclerView.setAdapter(adapter);
    }
}