package me.helloworlds.iPou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PembeliKeteranganTolak extends AppCompatActivity {
    private TextView txtKet;
    private String id_transaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembeli_keterangan_tolak);
        txtKet = (TextView) findViewById(R.id.txtKet);
        id_transaksi = getIntent().getStringExtra("id_transaksi");
        ket();
    }
    private void ket(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tolakTransaksi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                txtKet.setText("Alasan : "+object.getString("keterangan"));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PembeliKeteranganTolak.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PembeliKeteranganTolak.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_transaksi", id_transaksi);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
