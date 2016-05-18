package co.mikealegria.ui.Master;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.AbsListView;
import butterknife.Bind;
import butterknife.ButterKnife;
import co.mikealegria.R;
import co.mikealegria.api.REST;
import co.mikealegria.model.Entre;
import co.mikealegria.model.EntryResponse;
import co.mikealegria.ui.BaseActivity;
import co.mikealegria.ui.detail.DetailActivity;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MasterActivity extends BaseActivity {

  private static final String CATEGORY = MasterActivity.class.getCanonicalName() + ".CATEGORY";
  @Bind(R.id.toolbar_master) Toolbar toolbar;
  @Bind(R.id.list_master) AbsListView list;
  private List<List<Entre>> entryArr = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_master);
    ButterKnife.bind(this);

    String data = getIntent().getStringExtra(CATEGORY);
    if (data != null) {
      Log(data);
      Type listOfTestObject = new TypeToken<List<Entre>>() {
      }.getType();
      RxParseJson(data, listOfTestObject).observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe(o -> {
            List<Entre> entreList = (List<Entre>) o;
            configToolbarChild(toolbar, entreList.get(0).category.attributes.label);
            renderList_(entreList);
          });
    } else {
      getApps();
      configToolbar(toolbar, R.string.category);
    }
  }

  private void getApps() {
    REST.getRest()
        .getAppsList()
        .subscribeOn(Schedulers.io())
        .doOnSubscribe(() -> Log("Start Request"))
        .doOnCompleted(() -> Log("End Request"))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::init, throwable -> {
          getLocalEntry().subscribe(this::init, this::Log);
        });
  }

  private void init(@NonNull EntryResponse entryResponse) {
    entryResponse.save();
    getCategories(entryResponse.feed.entry).subscribe(strings -> {
      for (String str : strings) {
        Log("Categorias "+ str);
        getAppsByCategory(str, entryResponse.feed.entry).subscribe(entries -> {
          entryArr.add(entries);
        });
        renderList(entryArr);
      }
    });
  }

  private void renderList_(@NonNull List<Entre> entryArr) {
    list.setAdapter(new AdapterMasterItem(MasterActivity.this, R.layout.row, entryArr));
    list.setOnItemClickListener((parent, view, position, id) -> {
      if (entryArr.get(position) != null) {
        Intent intent = new Intent(MasterActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL, gson.toJson(entryArr.get(position)));
        goActv(intent, false);
      }
    });
  }

  private void renderList(@NonNull List<List<Entre>> entryArr) {
    list.setAdapter(new AdapterMaster(MasterActivity.this, R.layout.row, entryArr));
    list.setOnItemClickListener((parent, view, position, id) -> {
      if (entryArr.get(position) != null) {
        Intent intent = new Intent(MasterActivity.this, MasterActivity.class);

        Type listOfTestObject = new TypeToken<List<Entre>>() {
        }.getType();
        intent.putExtra(CATEGORY, gson.toJson(entryArr.get(position), listOfTestObject));
        goActv(intent, false);
      }
    });
  }

  private Observable<List<String>> getCategories(@NonNull ArrayList<Entre> entries) {
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

  private Observable<List<Entre>> getAppsByCategory(@NonNull String category,
      @NonNull ArrayList<Entre> entries) {

    return Observable.from(entries)
        .filter(entry -> entry.category.attributes.label.equalsIgnoreCase(category))
        .toList()
        .distinct()
        // .subscribeOn(Schedulers.io())
        //.observeOn(AndroidSchedulers.mainThread())
        .asObservable();
  }
}
