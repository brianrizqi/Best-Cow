package me.helloworlds.iPou.Peternak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;

public class PeternakVerifInvest extends AppCompatActivity {
    private String idInvest, gambar;
    private TextView txtName, txtJumlah, txtKandang, txtTolak;
    private Button tolak, verif, tolakk;
    private NetworkImageView imgBukti;
    private TinyDB tinyDB;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peternak_verif_invest);
        tinyDB = new TinyDB(getApplicationContext());
        idInvest = tinyDB.getString("id_investasi");
        txtName = (TextView) findViewById(R.id.txtName);
        txtJumlah = (TextView) findViewById(R.id.txtJumlah);
        txtKandang = (TextView) findViewById(R.id.txtKandang);
        imgBukti = (NetworkImageView) findViewById(R.id.imgBukti);
        tolakk = (Button) findViewById(R.id.btnTolakk);
        txtTolak = (TextView) findViewById(R.id.txtTolak);
        tampil();
        tolak = (Button) findViewById(R.id.btnTolak);
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tolakk.setVisibility(View.VISIBLE);
                txtTolak.setVisibility(View.VISIBLE);
            }
        });
        tolakk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tolak();
            }
        });
        verif = (Button) findViewById(R.id.btnVerif);
        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifInvest();
            }
        });
    }

    private void tolak() {
        final String cek = txtTolak.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tolakInvestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                Toast.makeText(PeternakVerifInvest.this, "Data telah ditolak", Toast.LENGTH_SHORT).show();
                                tinyDB.remove("id_investasi");
                                Intent i = new Intent(getApplicationContext(), Peternak.class);
                                startActivity(i);
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PeternakVerifInvest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakVerifInvest.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_investasi", idInvest);
                map.put("ket",cek);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void tampil() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tampilDetailInvestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            if (!error) {
                                txtName.setText(jsonObject.getString("name"));
                                txtJumlah.setText("Jumlah Uang : " + jsonObject.getString("jumlah_uang"));
                                txtKandang.setText("Kandang " + jsonObject.getString("id_kandang"));
                                gambar = jsonObject.getString("img_pembayaran");
                                imgBukti.setImageUrl(BaseAPI.buktiURL + jsonObject.getString("img_pembayaran"), imageLoader);

                            }
                        } catch (JSONException e) {
                            Toast.makeText(PeternakVerifInvest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakVerifInvest.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_investasi", idInvest);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void verifInvest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.verifInvestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                Toast.makeText(getApplicationContext(), "Invest telah diverifikasi", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Peternak.class);
                                tinyDB.remove("id_investasi");
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_investasi", idInvest);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
