package co.mikealegria.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.mikealegria.R;
import co.mikealegria.model.Entre;
import co.mikealegria.ui.BaseActivity;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.squareup.picasso.Picasso;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends BaseActivity {

  public static final String DETAIL = DetailActivity.class.getCanonicalName() + ".DETAIL";
  @Bind(R.id.image_app) ImageView imageApp;
  @Bind(R.id.toolbar_detail) Toolbar toolbar;
  @Bind(R.id.text_title) TextView textTitle;
  @Bind(R.id.text_description) ReadMoreTextView textDescription;
  private Entre entry;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    ButterKnife.bind(this);
    String data = getIntent().getStringExtra(DETAIL);
    if (data != null) {
      RxParseJson(data, Entre.class).observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe(o -> {
            Entre entre = (Entre) o;
            rederDetail(entre);
          });
    }
  }

  private void rederDetail(Entre entry) {
    this.entry = entry;
    configToolbarChild(toolbar, entry.imName.label);
    Picasso.with(DetailActivity.this).load(entry.imImage.get(2).label).fit().into(imageApp);
    textTitle.setText(entry.title.label);
    textDescription.setText(entry.summary.label);
  }

  @OnClick(R.id.image_price) public void onClick() {
    if (entry != null && entry.id.label != null) {
      Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(entry.id.label));
      goActv(intent, false);
    }
  }
}
