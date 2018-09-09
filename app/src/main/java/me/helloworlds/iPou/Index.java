package me.helloworlds.iPou;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.Wave;

public class Index extends AppCompatActivity {
    private static int splashInterval = 3000;
    String versionName = BuildConfig.VERSION_NAME;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        TextView version = (TextView) findViewById(R.id.version);
        version.setText("Version " + versionName);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
//        ThreeBounce threeBounce = new ThreeBounce();
        Wave foldingCube = new Wave();
        progressBar.setIndeterminateDrawable(foldingCube);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Index.this, Login.class);
                startActivity(i);
                finish();
            }
        }, splashInterval);

    }
}
