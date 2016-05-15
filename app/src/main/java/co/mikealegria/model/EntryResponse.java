package co.mikealegria.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by miguelalegria on 13/5/16 for DemoMike.
 */
public class EntryResponse extends BaseModel {
  public Feed feed;

  @Override public String getTag() {
    return getClassName();
  }

  public static class Feed {
    @SerializedName("entry") public ArrayList<Entre> entry;
  }
}
