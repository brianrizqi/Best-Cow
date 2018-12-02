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

public class MitraKeteranganTolak extends AppCompatActivity {
    private TextView txtKet;
    private String id_investasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra_keterangan_tolak);
        txtKet = (TextView) findViewById(R.id.txtKet);
        id_investasi = getIntent().getStringExtra("id_investasi");
        ket();
    }

    private void ket() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tolakInvestasi,
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
                            Toast.makeText(MitraKeteranganTolak.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MitraKeteranganTolak.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_investasi", id_investasi);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
