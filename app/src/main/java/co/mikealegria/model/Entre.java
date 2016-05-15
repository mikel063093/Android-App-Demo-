package co.mikealegria.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by miguelalegria on 15/5/16 for DemoMike.
 */
public class Entre extends BaseModel {

  @SerializedName("im:name") public ImName imName;
  @SerializedName("summary") public ImName summary;
  @SerializedName("im:price") public ImPrice imPrice;
  @SerializedName("im:contentType") public ImPrice imContentType;
  @SerializedName("rights") public ImName rights;
  @SerializedName("title") public ImName title;
  @SerializedName("link") public ImPrice link;
  @SerializedName("id") public ImPrice id;
  @SerializedName("im:artist") public ImPrice imArtist;
  @SerializedName("category") public ImPrice category;
  @SerializedName("im:releaseDate") public ImPrice imReleaseDate;
  @SerializedName("im:image") public List<ImPrice> imImage;

  @Override public String getTag() {
    return getClassName();
  }
}
