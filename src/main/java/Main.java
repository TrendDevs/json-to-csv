import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import uk.co.trenddevs.jsontocsv.parser.JsonFlattener;
import uk.co.trenddevs.jsontocsv.writer.CSVWriter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class Main {
	
    public static void main(String[] args) throws Exception {
        List<Map<String, String>> flatJson = JsonFlattener.parse(jsonValue());
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        CSVWriter.writeAsCSV(getHeaders(), flatJson, bw, ";");
        System.out.println(sw.toString());
    }

    private static JsonArray jsonValue() {
        String json = "[\n" +
	                "    {\n" +
	                "        \"studentName\": \"Foo\",\n" +
	                "        \"Age\": \"12\",\n" +
	                "        \"subjects\": [\n" +
	                "            {\n" +
	                "                \"name\": \"English\",\n" +
	                "                \"marks\": \"40\"\n" +
	                "            },\n" +
	                "            {\n" +
	                "                \"name\": \"History\",\n" +
	                "                \"marks\": \"50\"\n" +
	                "            }\n" +
	                "        ]\n" +
	                "    },\n" +
	                "    {\n" +
	                "        \"studentName\": \"Bar\",\n" +
	                "        \"Age\": \"12\",\n" +
	                "        \"subjects\": [\n" +
	                "            {\n" +
	                "                \"name\": \"English\",\n" +
	                "                \"marks\": \"40\"\n" +
	                "            },\n" +
	                "            {\n" +
	                "                \"name\": \"History\",\n" +
	                "                \"marks\": \"50\"\n" +
	                "            },\n" +
	                "            {\n" +
	                "                \"name\": \"Science\",\n" +
	                "                \"marks\": \"40\"\n" +
	                "            }\n" +
	                "        ]\n" +
	                "    },\n" +
	                "    {\n" +
	                "        \"studentName\": \"Baz\",\n" +
	                "        \"Age\": \"12\",\n" +
	                "        \"subjects\": []\n" +
	                "    }\n" +
	                "]";
    	return new Gson().fromJson(json, JsonArray.class);
    }
    
    private static List<String> getHeaders() {
    	return Arrays.asList(".studentName",".Age","subjects[i].name","subjects[i].marks");
    }
    
}
