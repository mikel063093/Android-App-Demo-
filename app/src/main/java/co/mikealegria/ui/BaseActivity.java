package co.mikealegria.ui;

import android.support.v7.app.AppCompatActivity;
import co.mikealegria.BuildConfig;
import co.mikealegria.model.EntryResponse;
import com.orhanobut.logger.Logger;
import rx.Observable;

/**
 * Created by miguelalegria on 15/5/16 for DemoMike.
 */
public class BaseActivity extends AppCompatActivity {
  public void Log(String msg) {
    if (BuildConfig.DEBUG) Logger.e(msg);
  }

  public void Log(Throwable throwable) {
    if (BuildConfig.DEBUG) Logger.e(throwable.getMessage());
  }

  public Observable<EntryResponse> getLocalEntry() {
    EntryResponse entryResponse = new EntryResponse();
    return entryResponse.getObject(EntryResponse.class).asObservable();
  }
}
