package co.mikealegria.api;

import co.mikealegria.model.EntryResponse;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by miguelalegria on 13/5/16 for DemoMike.
 */
public interface API {
  @GET("us/rss/topfreeapplications/limit=20/json") Observable<EntryResponse> getAppsList();
}
