package co.mikealegria;

import android.os.Bundle;
import android.util.Log;
import co.mikealegria.api.REST;
import co.mikealegria.model.Entre;
import co.mikealegria.ui.BaseActivity;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MasterActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_master);

    getLocalEntry().subscribe(entryResponse1 -> {
      Log(entryResponse1.toJson());
    }, this::Log);

    REST.getRest()
        .getAppsList()
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(() -> Log("Start Request"))
        .doOnCompleted(() -> Log("End Request"))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(entryResponse -> {
          entryResponse.save();
          Log(entryResponse.feed.entry.size() + "");

          getCategories(entryResponse.feed.entry).subscribe(strings -> {
            Log("categories size  " + strings.size());
            for (String str : strings) {
              Log.e("Categoerias", str);
              getAppsByCategory(str, entryResponse.feed.entry).subscribe(entries -> {
                Log(str + " size " + entries.size());
              });
            }
          });
        }, this::Log);
  }

  private Observable<List<String>> getCategories(ArrayList<Entre> entries) {
    return Observable.just(entries)
        .just(entries)
        .concatMap(Observable::from)
        .map(entry -> entry.category.attributes.label)
        .distinct()
        .toList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .asObservable();
    //.concatMap(Observable::from)
    //.filter(entry -> entry.category.attributes.label != null
    //    && entry.category.attributes.label.length() > 1)
    //.map(entry -> entry.category.attributes.label)
    //.toList()
    //.distinct()
    //.subscribeOn(Schedulers.io())
    //.observeOn(AndroidSchedulers.mainThread())
    //.asObservable();
    //Observable.just(entries).concatMap(Observable::from).distinct().subscribe(arrOut::add);
  }

  private Observable<List<Entre>> getAppsByCategory(String category, ArrayList<Entre> entries) {
    return Observable.from(entries)
        .filter(entry -> entry.category.attributes.label.equalsIgnoreCase(category))
        .toList()
        .distinct()
        // .subscribeOn(Schedulers.io())
        //.observeOn(AndroidSchedulers.mainThread())
        .asObservable();
  }
}
