package ca.concordia.risk.model;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GameSaveBuilder {
	
	/**
	 * returns a Gson instance
	 * @return GsonBuilder builder of Gson
	 */
	public static Gson getGSONInstance(){
		Type type = new TypeToken<HashMap<Country, ArrayList<String>>>(){}.getType();
		Type type1 = new TypeToken<Map<Player, List<Country>>>(){}.getType();
		return new GsonBuilder().enableComplexMapKeySerialization().
							registerTypeAdapter(type, new CountryMapAdapter()).
								registerTypeAdapter(type1, new PlayerAdapter()).create();
	}



}
