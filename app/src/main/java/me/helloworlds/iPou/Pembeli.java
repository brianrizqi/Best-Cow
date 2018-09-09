package me.helloworlds.iPou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Pembeli extends AppCompatActivity {
    private TextView textView;
    private String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembeli);
        textView = (TextView) findViewById(R.id.textview);
        text = getIntent().getStringExtra("name");
        textView.setText(text);
    }
}
