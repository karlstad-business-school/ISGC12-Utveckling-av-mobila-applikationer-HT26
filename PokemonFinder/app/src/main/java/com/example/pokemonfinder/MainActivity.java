package com.example.pokemonfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements APICallback{

    //pokemon - https://pokeapi.co/api/v2/pokemon/bulbasaur
    //list - https://pokeapi.co/api/v2/pokemon?limit=15&offset=0

    private RequestQueue requestQueue;
    private int limit = 15;
    private int page = 0;

    private final int SEARCH_MODE_POKEMON = 1;
    private final int SEARCH_MODE_LIST = 0;
    private int searchMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<Pokemon> pokemonsFromFile = DataManager.instance.readFromFile(MainActivity.this);
        PokemonManager.instance.setPokemons(pokemonsFromFile);

        Log.e("MAINACITVITY", PokemonManager.instance.getPokemons().get(0).getName());


        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        getPokemons(page);

        Button loadMoreBtn = findViewById(R.id.loadMoreButton);
        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                getPokemons(page);
            }
        });


        Button searchBtn = findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText search = findViewById(R.id.search);
                String name = search.getText().toString().trim();

                searchForPokemon(name);
            }
        });


        Button viewSavedBtn = findViewById(R.id.viewSavedButton);
        viewSavedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSavedPokemon();
            }
        });

        Button viewAPIBtn = findViewById(R.id.viewApiButton);
        viewAPIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAPIPokemon();
            }
        });
    }


    private void showSavedPokemon(){
        ScrollView apiScroll = findViewById(R.id.apiScrollView);
        ScrollView saveScroll = findViewById(R.id.savedScrollView);
        LinearLayout list = findViewById(R.id.savedList);

        list.removeAllViews();

        ArrayList<Pokemon> pokemons = PokemonManager.instance.getPokemons();

        for(int i = 0; i < pokemons.size(); i++){
            Pokemon pokemon = pokemons.get(i);
            String name = pokemon.getName();

            TextView textView = new TextView(MainActivity.this);
            textView.setText(name);
            textView.setTextSize(18);
            textView.setPadding(16, 12, 16, 12);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchForPokemon(name);
                }
            });

            list.addView(textView);
        }




        apiScroll.setVisibility(View.GONE);
        saveScroll.setVisibility(View.VISIBLE);
    }

    private void showAPIPokemon(){
        ScrollView apiScroll = findViewById(R.id.apiScrollView);
        ScrollView saveScroll = findViewById(R.id.savedScrollView);

        apiScroll.setVisibility(View.VISIBLE);
        saveScroll.setVisibility(View.GONE);
    }


    private void searchForPokemon(String name){
        String url = "https://pokeapi.co/api/v2/pokemon/" + name;

        APICall api = new APICall();

        searchMode = SEARCH_MODE_POKEMON;
        api.get(requestQueue, MainActivity.this, url);
    }

    private void getPokemons(int page){
        int offset = getOffsetFromPage(page);
        String url = "https://pokeapi.co/api/v2/pokemon?limit=" + limit + "&offset=" + offset;

        APICall api = new APICall();

        searchMode = SEARCH_MODE_LIST;

        api.get(requestQueue, MainActivity.this, url);
        Log.e("sdasd", "test 1" );
    }

    private int getOffsetFromPage(int page){
        int offset = 0;
        offset = page * limit;
        return offset;
    }

    private void createPokemonList(JSONObject object){
        LinearLayout list = findViewById(R.id.list);

        try{
            JSONArray pokemons = object.getJSONArray("results");

            for(int i = 0; i < pokemons.length(); i++){
                JSONObject pokemon = pokemons.getJSONObject(i);

                String name = pokemon.getString("name");

                TextView textView = new TextView(MainActivity.this);
                textView.setText(name);
                textView.setTextSize(18);
                textView.setPadding(16, 12, 16, 12);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchForPokemon(name);
                    }
                });

                list.addView(textView);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void viewPokemon(JSONObject object){
        Intent intent = new Intent(MainActivity.this, PokemonActivity.class);

        try{
            intent.putExtra("id", object.getInt("id"));
            intent.putExtra("name", object.getString("name"));
            startActivity(intent);
        }catch (Exception e){

        }


    }

    @Override
    public void onSuccess(JSONObject object) {
        Log.e("asdassadas", object.toString());


        if(searchMode == SEARCH_MODE_LIST){
            createPokemonList(object);
        }else if(searchMode == SEARCH_MODE_POKEMON){
            viewPokemon(object);
        }

    }

    @Override
    public void onFailure(Exception e) {
        Log.e("ERROR VOLLEY", e.toString());
    }
}