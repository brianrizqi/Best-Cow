package me.helloworlds.iPou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MitraRekInvest extends AppCompatActivity {
    private Button selesai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra_rek_invest);
        selesai = (Button) findViewById(R.id.btnSelesai);
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MitraRekInvest.this, Mitra.class);
                startActivity(i);
                finish();
            }
        });
    }
}
