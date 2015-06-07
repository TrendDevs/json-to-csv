package uk.co.trenddevs.jsontocsv.parser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonFlattener {
	
    public static  Map<String, String> parse(JsonObject jsonObject) {
        Map<String, String> flatJson = new HashMap<String, String>();
        flatten(jsonObject, flatJson, "");
        return flatJson;
    }

    public static List<Map<String, String>> parse(JsonArray jsonArray) {
        List<Map<String, String>> flatJson = new ArrayList<Map<String, String>>();
        int length = jsonArray.size();
        for (int i = 0; i < length; i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            Map<String, String> stringMap = parse(jsonObject);
            flatJson.add(stringMap);
        }
        return flatJson;
    }

    private static  void flatten(JsonArray array, Map<String, String> flatJson, String prefix) {
    	if (prefix.startsWith("."))
    		prefix = prefix.substring(1);
    	int count = 0;
    	for (JsonElement jsonElement : array) {
			if (jsonElement.isJsonArray()) {
				JsonArray jsonArray = jsonElement.getAsJsonArray();
				if (jsonArray.size()>0)
					flatten(jsonArray, flatJson, prefix+ "[" + count + "]");
			} else if (jsonElement.isJsonObject()) {
				flatten(jsonElement.getAsJsonObject(), flatJson, prefix+ "[" + count + "]");
			} else if (!jsonElement.isJsonNull()) {
				flatJson.put(prefix+ "[" + count + "]", jsonElement.getAsString());
			}
			count++;
		}
    }

    private static void flatten(JsonObject obj, Map<String, String> flatJson, String prefix) {
    	if (prefix.startsWith("."))
    		prefix = prefix.substring(1);
        for (Entry<String, JsonElement> property : obj.entrySet()) {
			if (property.getValue().isJsonArray()) {
				JsonArray jsonArray = property.getValue().getAsJsonArray();
				if (jsonArray.size()>0)
					flatten(jsonArray, flatJson, prefix + "." + property.getKey());
			} else if (property.getValue().isJsonObject()) {
				flatten(property.getValue().getAsJsonObject(), flatJson, prefix + "." + property.getKey());
			} else if (!property.getValue().isJsonNull()) {
				flatJson.put(prefix+"."+property.getKey(), property.getValue().getAsString());
			}
		}
    }
}
