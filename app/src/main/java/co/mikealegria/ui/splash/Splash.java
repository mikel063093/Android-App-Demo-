package co.mikealegria.ui.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import co.mikealegria.R;
import co.mikealegria.ui.Master.MasterActivity;
import gr.net.maroulis.library.EasySplashScreen;

public class Splash extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View easySplashScreenView = new EasySplashScreen(Splash.this)
        .withTargetActivity(MasterActivity.class)
        .withSplashTimeOut(3000)
        .withBackgroundResource(R.color.white)
        .withFooterText("Copyright 2016")
        .withLogo(R.drawable.logo)
        .withAfterLogoText("Miguel Alegria")
        .create();
    setContentView(easySplashScreenView);
  }
}
