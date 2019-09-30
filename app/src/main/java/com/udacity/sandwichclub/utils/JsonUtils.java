package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.*;
import java.util.ArrayList;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mainName = null;
        ArrayList<String> alsoKnownAs = new ArrayList<>();
        String image = null;
        String placeOfOrigin = null;
        String description =null;
        ArrayList<String> ingredients = new ArrayList<>();

        JSONObject sandwishJSONObject;


        try {

            /* initialize JSONObject from JSON String */
            sandwishJSONObject = new JSONObject(json);

            /*.... get mainName && alsoKnownAs from name Attribute in JSONObject ...*/
            JSONObject name = sandwishJSONObject.getJSONObject("name");
            mainName = name.getString("mainName");

            JSONArray bridgeArrayAlsoKnowAs = name.getJSONArray("alsoKnownAs");
            if (bridgeArrayAlsoKnowAs.length()>=1) {
                for (int i = 0; i < bridgeArrayAlsoKnowAs.length(); i++) {
                    alsoKnownAs.add(bridgeArrayAlsoKnowAs.getString(i));
                }
            }else{
                alsoKnownAs.add("Not Available !"); // if alsoKnownAs is Empty in JSON Object
            }

            /* get placeOfOrigin && description && image  attribute from JSONObject */
            placeOfOrigin = sandwishJSONObject.getString("placeOfOrigin");
            description = sandwishJSONObject.getString("description");
            image = sandwishJSONObject.getString("image");

            /*... get ingredients from JSONObject ...*/
            JSONArray bridgeArrayingredients = sandwishJSONObject.getJSONArray("ingredients");
            if (bridgeArrayingredients != null) {
                for (int i = 0; i < bridgeArrayingredients.length(); i++) {
                    ingredients.add(bridgeArrayingredients.getString(i));
                }
            }

        }catch (JSONException ex){
            ex.printStackTrace();
        }

        Sandwich sandwichDetails = new Sandwich();

        sandwichDetails.setMainName(mainName);
        sandwichDetails.setAlsoKnownAs(alsoKnownAs);
        sandwichDetails.setPlaceOfOrigin(placeOfOrigin);
        sandwichDetails.setDescription(description);
        sandwichDetails.setImage(image);
        sandwichDetails.setIngredients(ingredients);

        return sandwichDetails;
    }
}
