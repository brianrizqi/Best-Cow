package me.helloworlds.iPou;

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

public class MitraDetailInvest extends AppCompatActivity {
    private Button plus, minus, invest;
    private TextView txtKandang, txtInvestPrice, txtInvestTotal;
    private CheckBox check;
    private String kandang, idUser, idKandang;
    private String tambahInvestUrl = BaseAPI.tambahInvestURL;
    private int price, total, jumlah_uang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra_detail_invest);
        Locale locale = new Locale("in", "ID");
        final NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        txtKandang = (TextView) findViewById(R.id.txtKandang);
        txtInvestPrice = (TextView) findViewById(R.id.txtInvestPrice);
        txtInvestTotal = (TextView) findViewById(R.id.txtInvestTotal);
        plus = (Button) findViewById(R.id.btnPlus);
        minus = (Button) findViewById(R.id.btnMinus);
        invest = (Button) findViewById(R.id.btnInvest);
        check = (CheckBox) findViewById(R.id.checkInvest);
        kandang = getIntent().getStringExtra("kandang");
        idUser = getIntent().getStringExtra("id_user");
        idKandang = getIntent().getStringExtra("id_kandang");
        txtKandang.setText("Kandang " + kandang);
        price = Integer.parseInt(txtInvestPrice.getText().toString());
        total = Integer.parseInt(txtInvestTotal.getText().toString());
//        txtInvestPrice.setText(format.format(Double.parseDouble(String.valueOf(total))));
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total++;
                txtInvestTotal.setText(String.valueOf(total));
                jumlah_uang = total * price;
//                txtInvestPrice.setText(format.format(Double.parseDouble(String.valueOf(jumlah_uang))));
                txtInvestPrice.setText(String.valueOf(jumlah_uang));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total--;
                txtInvestTotal.setText(String.valueOf(total));
                jumlah_uang = total * price;
//                txtInvestPrice.setText(format.format(Double.parseDouble(String.valueOf(jumlah_uang))));
                txtInvestPrice.setText(String.valueOf(jumlah_uang));
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

    private void createInvest() {
        final String jml = txtInvestPrice.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tambahInvestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                Toast.makeText(MitraDetailInvest.this, "Terima Kasih telah investasi", Toast.LENGTH_SHORT).show();
                                onBackPressed();
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
                map.put("id_kandang", idKandang);
                map.put("jumlah_uang", jml);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
