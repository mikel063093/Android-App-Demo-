package co.mikealegria.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by miguelalegria on 15/5/16 for DemoMike.
 */
public class Entry extends BaseModel {

  @SerializedName("im:name") public ImNameBean imName;

  @SerializedName("summary") public SummaryBean summary;

  @SerializedName("im:price") public ImPriceBean imPrice;

  @SerializedName("im:contentType") public ImContentTypeBean imContentType;

  @SerializedName("rights") public RightsBean rights;

  @SerializedName("title") public TitleBean title;

  @SerializedName("link") public LinkBean link;

  @SerializedName("id") public IdBean id;

  @SerializedName("im:artist") public ImArtistBean imArtist;

  @SerializedName("category") public CategoryBean category;

  @SerializedName("im:releaseDate") public ImReleaseDateBean imReleaseDate;

  @SerializedName("im:image") public List<ImImageBean> imImage;

  @Override public String getTag()

  {
    return getClassName();
  }

  public class ImNameBean {

    @SerializedName("label") public String label;
  }

  public static class ImPriceBean {
    @SerializedName("label") public String label;

    @SerializedName("attributes") public AttributesBean attributes;

    public static class AttributesBean {
      @SerializedName("amount") public String amount;

      @SerializedName("currency") public String currency;
    }
  }

  public static class ImContentTypeBean {
    @SerializedName("attributes") public AttributesBean attributes;

    public static class AttributesBean {
      @SerializedName("term") public String term;

      @SerializedName("label") public String label;
    }
  }

  public static class SummaryBean {
    @SerializedName("label") public String label;
  }

  public static class RightsBean {
    @SerializedName("label") public String label;
  }

  public static class TitleBean {
    @SerializedName("label") public String label;
  }

  public static class LinkBean {
    @SerializedName("attributes") public AttributesBean attributes;

    public static class AttributesBean {
      @SerializedName("rel") public String rel;

      @SerializedName("type") public String type;

      @SerializedName("href") public String href;
    }
  }

  public static class IdBean {
    @SerializedName("label") public String label;

    @SerializedName("attributes") public AttributesBean attributes;

    public static class AttributesBean {
      @SerializedName("im:id") public String imId;

      @SerializedName("im:bundleId") public String imBundleId;
    }
  }

  public static class ImArtistBean {
    @SerializedName("label") public String label;

    @SerializedName("attributes") public AttributesBean attributes;

    public static class AttributesBean {
      @SerializedName("href") public String href;
    }
  }

  public static class CategoryBean {
    @SerializedName("attributes") public AttributesBean attributes;

    public static class AttributesBean {
      @SerializedName("im:id") public String imId;

      @SerializedName("term") public String term;

      @SerializedName("scheme") public String scheme;

      @SerializedName("label") public String label;
    }
  }

  public static class ImReleaseDateBean {
    @SerializedName("label") public String label;

    @SerializedName("attributes") public AttributesBean attributes;

    public static class AttributesBean {
      @SerializedName("label") public String label;
    }
  }

  public static class ImImageBean {
    @SerializedName("label") public String label;

    @SerializedName("attributes") public AttributesBean attributes;

    public static class AttributesBean {
      @SerializedName("height") public String height;
    }
  }
}
