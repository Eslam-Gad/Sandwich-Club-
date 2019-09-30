package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    /*--- temp variables to store data from json ---*/
    String desp = null;
    ArrayList<String> alsoKnownAs = null;
    ArrayList<String> ingredientsList = null;
    String placeOfRej = null;
    Sandwich sandwich ;

    /*--- Reference from UI  ---*/
    TextView description ;
    TextView alsoKnown;
    TextView ingredients;
    TextView placeOfRegion;
    ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        description = (TextView)findViewById(R.id.description_tv);
        alsoKnown = (TextView)findViewById(R.id.also_known_tv);
        ingredients = (TextView)findViewById(R.id.ingredients_tv);
        placeOfRegion = (TextView)findViewById(R.id.placeOfrigin_tv);

        ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

         desp = sandwich.getDescription();
         alsoKnownAs = sandwich.getAlsoKnownAs();
         ingredientsList = sandwich.getIngredients();
         placeOfRej = sandwich.getPlaceOfOrigin();


        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }




    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

             description.setText(desp);

             if(!alsoKnownAs.isEmpty() && alsoKnownAs !=null) {
                 for(int i = 0 ; i <alsoKnownAs.size(); i++)
                  if(i== alsoKnownAs.size()-1)  // to avoid ' , ' in the end of String
                      alsoKnown.append(alsoKnownAs.get(i));
                     else
                 alsoKnown.append(alsoKnownAs.get(i)+", ");
             }

         if(!ingredientsList.isEmpty() && ingredientsList !=null) {
            for(int i = 0 ; i < ingredientsList.size() ; i++)
                ingredients.append((i+1) +") "+ ingredientsList.get(i)+"\n");
          }

            if(placeOfRej.isEmpty()) // if placeOfRegion is empty in JSON Object
            placeOfRej="Not Available !";
            placeOfRegion.setText(placeOfRej);
    }
}
