package co.mikealegria.utils;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by miguelalegria
 */
public class JSONUtils {
  public static Map<String, Object> jsonToMap(@NonNull String json) {
    try {
      JSONObject jsonObject = new JSONObject(json);
      return jsonToMap(jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
    Map<String, Object> retMap = new HashMap<>();

    if (json != JSONObject.NULL) {
      retMap = toMap(json);
    }
    return retMap;
  }

  public static Map<String, Object> toMap(JSONObject object) throws JSONException {
    Map<String, Object> map = new HashMap<>();

    Iterator<String> keysItr = object.keys();
    while (keysItr.hasNext()) {
      String key = keysItr.next();
      Object value = object.get(key);

      if (value instanceof JSONArray) {
        value = toList((JSONArray) value);
      } else if (value instanceof JSONObject) {
        value = toMap((JSONObject) value);
      }
      map.put(key, value);
    }
    return map;
  }

  public static List<Object> toList(JSONArray array) throws JSONException {
    List<Object> list = new ArrayList<Object>();
    for (int i = 0; i < array.length(); i++) {
      Object value = array.get(i);
      if (value instanceof JSONArray) {
        value = toList((JSONArray) value);
      } else if (value instanceof JSONObject) {
        value = toMap((JSONObject) value);
      }
      list.add(value);
    }
    return list;
  }

  public static String mapToString(Map<String, Object> map) {
    StringBuilder stringBuilder = new StringBuilder();

    for (String key : map.keySet()) {
      if (stringBuilder.length() > 0) {
        stringBuilder.append("&");
      }
      Object value = map.get(key);
      try {

        stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
        stringBuilder.append("=");
        stringBuilder.append(
            value != null ? URLEncoder.encode(String.valueOf(value), "UTF-8") : "");
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException("This method requires UTF-8 encoding support", e);
      }
    }

    return stringBuilder.toString();
  }
}
