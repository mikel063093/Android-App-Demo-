package co.mikealegria.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by miguelalegria on 15/5/16 for DemoMike.
 */
public class Attributes {
  @SerializedName("amount") public String amount;
  @SerializedName("currency") public String currency;
  @SerializedName("label") public String label;
}
