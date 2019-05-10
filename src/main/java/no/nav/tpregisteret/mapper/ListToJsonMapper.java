package no.nav.tpregisteret.mapper;

import com.google.gson.Gson;
import no.nav.tpregisteret.TPOrdning;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Responsibility: Convert a list of strings to JSON format.
 */
public class ListToJsonMapper {

    public static String convertToJson(List<TPOrdning> tpOrdningList) {
        JSONObject body = new JSONObject();
        JSONArray list = new JSONArray();

        for (TPOrdning tpOrdning : tpOrdningList) {
            Gson gson = new Gson();
            String tpOrdningsString = gson.toJson(tpOrdning);

            list.put(tpOrdningsString);
        }

        body.put("tpOrdning", list);
        return body.toString();
    }
}
