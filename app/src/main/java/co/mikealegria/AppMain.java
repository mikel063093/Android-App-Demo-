package co.mikealegria;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import io.supercharge.rxsnappy.RxSnappy;
import io.supercharge.rxsnappy.RxSnappyClient;

/**
 * Created by miguelalegria on 7/27/15.
 */
public class AppMain extends Application {
  private static Context context;

  public static Context getContex() {
    return context;
  }

  public static AppMain getApp(@NonNull Context context) {
    return (AppMain) context.getApplicationContext();
  }

  public static AppMain getApp() {
    return getApp(getContex());
  }

  private RxSnappyClient rxSnappyClient;

  public RxSnappyClient getRxSnappyClient() {
    return rxSnappyClient;
  }

  public void initRxDb() {
    // Logger.e("Appmain InitDB");
    Log.e("AppMain", "initDB");
    if (getContex() != null) {
      RxSnappy.init(getContex());
      if (rxSnappyClient == null) rxSnappyClient = new RxSnappyClient();
    }
  }

  @Override public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    Logger.init(getString(R.string.app_name))
        .logLevel(BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE);
    initRxDb();
  }

  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    //initRxDb();
    MultiDex.install(this);
  }
}
