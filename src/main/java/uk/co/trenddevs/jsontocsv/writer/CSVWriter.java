package uk.co.trenddevs.jsontocsv.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVWriter {

	private static Logger LOG = LoggerFactory.getLogger(CSVWriter.class);
	
    public static List<Map<String,String>> demuxFlatJson(List<String> headers , int demuxSize, Map<String, String> flatJson) throws IOException {
    	List<Map<String,String>> demuxJson = new ArrayList<Map<String,String>>();
    	for (int i=0; i<demuxSize; i++) {
    		Map<String,String> demuxLine = new HashMap<String, String>();
    		for (String header : headers) {
				demuxLine.put(header, flatJson.get(header.replace("[i]", "["+i+"]")));
			}
    		demuxJson.add(demuxLine);
    	}
    	return demuxJson;
    }


    public static void writeAsCSV(List<String> headers, Map<String, String> flatJson, BufferedWriter writer, String csvSeparator) throws IOException {
    	StringBuilder sb = new StringBuilder();
        for (int i=0;i<headers.size();i++) {
        	String header = headers.get(i);
        	sb.append(flatJson.get(header) == null ? "" : flatJson.get(header));       
            if (i<headers.size()-1)
            	sb.append(csvSeparator);
		}
        LOG.debug("{}",sb);
        writer.write(sb.toString());
		writer.newLine();
        writer.flush();
    }

    public static void writeAsCSV(List<String> headers, List<Map<String, String>> flatJson, BufferedWriter writer, String csvSeparator) throws IOException {
    	for (Map<String, String> map : flatJson) 
    		writeAsCSV(headers, map, writer, csvSeparator);
    }

}