package ca.concordia.risk.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerAdapter implements JsonSerializer<Map<Player, List<Country>>>,JsonDeserializer<Map<Player, List<Country>>> {
	
	static final String CLASSNAME = "CLASSNAME";
    static final String DATA = "DATA";
    
    /**
     * gets the object of className
     * @param className name of class
     * @return className name of class
     * @throws JsonParseException Exception in Json Parsing
     */
    public Class getObjectClass(String className) {
        try {
            return Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e.getMessage());
            }
    }

	@Override
	public Map<Player, List<Country>> deserialize(JsonElement json, Type typeofT, JsonDeserializationContext context)
			throws JsonParseException {
	
		Gson gson = GameSaveBuilder.getGSONInstance();
		
		JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        HashMap<LinkedTreeMap,List<Country>> map = context.deserialize(jsonObject.get(DATA), klass);
        Map<Player, List<Country>> returnMap = new HashMap<>();
        Type listType = new TypeToken<List<Country>>(){}.getType();
        for(LinkedTreeMap k : map.keySet()){
        	Player p = gson.fromJson(gson.toJsonTree(k).getAsJsonObject(), Player.class);
        	List<Country> c = gson.fromJson(gson.toJsonTree(map.get(k)).getAsJsonArray(), listType);
        	returnMap.put(p, c);
        }
        return returnMap;
	}

	@Override
	public JsonElement serialize(Map<Player, List<Country>> src, Type typeOfSrc, JsonSerializationContext context) {
	
		JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, src.getClass().getName());
        jsonObject.add(DATA, context.serialize(src));
        return jsonObject;
	}
    
    

}
