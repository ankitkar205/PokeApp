package com.ankit.pokemon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class pokefinder extends AppCompatActivity {

    private EditText etSearchPokemon;
    private ImageButton btnSearch;
    private ImageView ivPokemon;
    private TextView tvPokemonName, tvPokemonType;
    private CardView cardPokemon;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokefinder);

        // Initialize UI components
        etSearchPokemon = findViewById(R.id.etSearchPokemon);
        btnSearch = findViewById(R.id.btnSearch);
        ivPokemon = findViewById(R.id.ivPokemon);
        tvPokemonName = findViewById(R.id.tvPokemonName);
        tvPokemonType = findViewById(R.id.tvPokemonType);
        cardPokemon = findViewById(R.id.cardPokemon);

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Search button click listener
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pokemonName = etSearchPokemon.getText().toString().trim().toLowerCase();
                if (!pokemonName.isEmpty()) {
                    fetchPokemonData(pokemonName);
                } else {
                    Toast.makeText(pokefinder.this, "Enter a Pokémon name or ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchPokemonData(String pokemonQuery) {
        String url = "https://pokeapi.co/api/v2/pokemon/" + pokemonQuery;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Get Pokémon name
                            String name = response.getString("name");
                            tvPokemonName.setText(name.toUpperCase());

                            // Get Pokémon sprite image URL
                            String imageUrl = response.getJSONObject("sprites").getString("front_default");

                            // Load image using Glide
                            Glide.with(pokefinder.this)
                                    .load(imageUrl)
                                    .into(ivPokemon);

                            // Get Pokémon type(s)
                            JSONArray typesArray = response.getJSONArray("types");
                            StringBuilder types = new StringBuilder();
                            for (int i = 0; i < typesArray.length(); i++) {
                                JSONObject typeObj = typesArray.getJSONObject(i).getJSONObject("type");
                                types.append(typeObj.getString("name")).append(" ");
                            }
                            tvPokemonType.setText("Type: " + types.toString().trim());

                            // Show the CardView after fetching data
                            cardPokemon.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(pokefinder.this, "Error parsing data!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(pokefinder.this, "Pokémon not found!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }
}
