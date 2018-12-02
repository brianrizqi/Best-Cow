package me.helloworlds.iPou.Mitra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;

public class MitraDetailInvest extends AppCompatActivity {
    private Button plus, minus, invest;
    private TextView txtKandang, txtInvestPrice, txtInvestTotal, txtROI;
    private CheckBox check;
    private String kandang, idUser, idKandang, investor,uang;
    private String tambahInvestUrl = BaseAPI.tambahInvestURL;
//    private String tampilJumlahUangUrl = BaseAPI.tampilJumlahUangURL;
    private int price, total, jumlah_uang;
    private double hargaMin, hargaMax, rasioMin, rasioMax;
    private double modal = 41300;
    private double labaMin = 11200;
    private double labaMax = 21700;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra_detail_invest);
        tinyDB = new TinyDB(getApplicationContext());
        Locale locale = new Locale("in", "ID");
        final NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        txtKandang = (TextView) findViewById(R.id.txtKandang);
        txtInvestPrice = (TextView) findViewById(R.id.txtInvestPrice);
        txtInvestTotal = (TextView) findViewById(R.id.txtInvestTotal);
        txtROI = (TextView) findViewById(R.id.txtROI);
        plus = (Button) findViewById(R.id.btnPlus);
        minus = (Button) findViewById(R.id.btnMinus);
        invest = (Button) findViewById(R.id.btnInvest);
        check = (CheckBox) findViewById(R.id.checkInvest);
        kandang = getIntent().getStringExtra("kandang");
        idUser = tinyDB.getString("id_user");
        idKandang = getIntent().getStringExtra("id_kandang");
        investor = getIntent().getStringExtra("count(*)");
        uang = getIntent().getStringExtra("uang");
        txtKandang.setText("Kandang " + kandang);
        price = Integer.parseInt(txtInvestPrice.getText().toString());
        total = Integer.parseInt(txtInvestTotal.getText().toString());
        getTotal(total);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total > 5-(Integer.parseInt(uang)/20650000)) {

                } else {
                    total++;
                    getTotal(total);
                }
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total < 2) {

                } else {
                    total--;
                    getTotal(total);
                }
            }
        });
        invest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.isChecked()) {
                    createInvest();
                } else {
                    Toast.makeText(MitraDetailInvest.this, "Mohon disetujui peraturan yang ada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getTotal(int total) {
        txtInvestTotal.setText(String.valueOf(total));
        jumlah_uang = total * price;
        txtInvestPrice.setText(String.valueOf(jumlah_uang));
        rasioMin = (labaMin / modal) * 100;
        rasioMax = (labaMax / modal) * 100;
        hargaMin = (rasioMin / 100) * jumlah_uang;
        hargaMax = (rasioMax / 100) * jumlah_uang;
        txtROI.setText("ROI : " + String.valueOf(rasioMin) + "% - " + String.valueOf(rasioMax) + "% \n" +
                "Untung : Rp." + String.valueOf(hargaMin) + " - Rp." + String.valueOf(hargaMax));
    }

    private void createInvest() {
        final String jml = txtInvestPrice.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tambahInvestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                Toast.makeText(MitraDetailInvest.this, "Terima Kasih telah investasi", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MitraDetailInvest.this, MitraRekInvest.class);
                                startActivity(i);
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MitraDetailInvest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MitraDetailInvest.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user", idUser);
                map.put("id_kandang", kandang);
                map.put("jumlah_uang", jml);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
