package no.nav.tpregisteret.mapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Responsibility: Convert a list of strings to JSON format.
 */
public class ListToJsonMapper {

    public static String convertToJson(List<String> tpNrList) {
        JSONObject body = new JSONObject();
        JSONArray list = new JSONArray();

        for (String tpNr : tpNrList) {
            list.put(tpNr);
        }

        body.put("tpNr", list);
        return body.toString();
    }
}
