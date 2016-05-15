package co.mikealegria.model;

import android.support.annotation.NonNull;
import co.mikealegria.AppMain;
import co.mikealegria.BuildConfig;
import co.mikealegria.utils.JSONUtils;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import io.supercharge.rxsnappy.RxSnappyClient;
import io.supercharge.rxsnappy.exception.RxSnappyException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by miguelalegria on 1/22/16 for epass.
 */
public abstract class BaseModel {

  protected static RxSnappyClient rxSnappy;

  static {
    rxSnappy = AppMain.getApp().getRxSnappyClient();
  }

  public static RxSnappyClient getRxSnappy() {
    return rxSnappy;
  }

  public abstract String getTag();

  public void save() {
    rxSnappy.setObject(getTag(), this)
        .subscribeOn(Schedulers.io())
        .debounce(150, TimeUnit.MILLISECONDS)
        .subscribe(item -> {
          Log(item.toJson());
        }, throwable -> {
          Log("save fail" + throwable.getMessage());
        });
  }

  //public void Log(String txt) {
  //  if (BuildConfig.DEBUG) Logger.e(txt);
  //}

  private static void Log(String txt) {
    if (BuildConfig.DEBUG) Logger.e(txt);
  }

  public Map<String, Object> jsonToMap(@NonNull String json) {
    try {
      JSONObject jsonObject = new JSONObject(json);
      return jsonToMap(jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Map<String, Object> jsonToMap() {
    try {
      JSONObject jsonObject = new JSONObject(this.toJson());
      return jsonToMap(jsonObject);
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
    Map<String, Object> retMap = new HashMap<>();
    if (json != JSONObject.NULL) {
      retMap = JSONUtils.toMap(json);
    }
    return retMap;
  }

  public static Object getIsntace(@NonNull String TAG, Class T) {

    try {
      return getRxSnappy().getObject(TAG, T).toBlocking().first();
    } catch (RxSnappyException ex) {
      Log(ex.getMessage());
      return null;
    }
  }

  public <T> Observable<T> getObject(Class<T> selectedClass) {
    return getRxSnappy().getObject(getTag(), selectedClass)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .asObservable();

  }

  public <T> Observable<T> getObject() {
    Class<T> selectedClass = (Class<T>) this.getClass();
    return getRxSnappy().getObject(getTag(), selectedClass);
  }

  public String toJson() {
    return new Gson().toJson(this);
  }

  public String getClassName() {
    return this.getClass().getSimpleName();
  }
}
