package me.helloworlds.iPou.Peternak;

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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.CustomNetworkImageView;
import me.helloworlds.iPou.R;

public class PeternakDetailOrder extends AppCompatActivity {
    private TextView txtTotal, txtJumlah, txtPembeli;
    private Button verif,tolak;
    private CustomNetworkImageView imgBukti;
    private String idTransaksi,jumlah;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peternak_detail_order);
        txtJumlah = (TextView) findViewById(R.id.txtJumlah);
        txtPembeli = (TextView) findViewById(R.id.txtPembeli);
        txtTotal = (TextView) findViewById(R.id.txtHarga);
        imgBukti = (CustomNetworkImageView) findViewById(R.id.imgBukti);
        tolak = (Button) findViewById(R.id.btnTolak);
        verif = (Button) findViewById(R.id.btnVerif);
        idTransaksi = getIntent().getStringExtra("id_transaksi");
        getDetail();
        tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tolak();
            }
        });
        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verif();
            }
        });
    }

    private void tolak() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.hapusOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                Toast.makeText(PeternakDetailOrder.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            } else {
                                Toast.makeText(PeternakDetailOrder.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            Toast.makeText(PeternakDetailOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakDetailOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> map = new HashMap<String, String>();
                map.put("id_transaksi",idTransaksi);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void getDetail() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tampilDetailOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                txtJumlah.setText("Jumlah : " + object.getString("jumlah"));
                                total = Integer.parseInt(object.getString("jumlah")) * Integer.parseInt(object.getString("harga"));
                                txtTotal.setText("Total Harga : " + String.valueOf(total));
                                txtPembeli.setText("Nama : " + object.getString("name"));
                                imgBukti.setImageUrl(BaseAPI.buktiURL + object.getString("bukti"), imageLoader);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PeternakDetailOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakDetailOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_transaksi", idTransaksi);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void verif() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.verifOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status){
                                Toast.makeText(PeternakDetailOrder.this, "Data berhasil diverif", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            } else {
                                Toast.makeText(PeternakDetailOrder.this, "Stok Kurang", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            Toast.makeText(PeternakDetailOrder.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakDetailOrder.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String, String>();
                map.put("id_transaksi",idTransaksi);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
