package me.helloworlds.iPou.Peternak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.R;

public class PeternakAddStatusAyam extends AppCompatActivity {
    private String updateAyamUrl = BaseAPI.updateAyamURL;
    private TextView txtKandang;
    private EditText txtAyamSakit, txtAyamMati;
    private String ayammati, ayamsakit, kandang;
    private String ayamMati, ayamSakit;
    private String totalMati, totalSakit;
    private Button btnUpdateAyam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peternak_add_status_ayam);
        txtKandang = (TextView) findViewById(R.id.txtKandang);
        txtAyamMati = (EditText) findViewById(R.id.txtAyamMati);
        txtAyamSakit = (EditText) findViewById(R.id.txtAyamSakit);
        ayammati = getIntent().getStringExtra("ayam_mati");
        ayamsakit = getIntent().getStringExtra("ayam_sakit");
        kandang = getIntent().getStringExtra("id_kandang");
        txtKandang.setText("Kandang " + kandang);
        btnUpdateAyam = (Button) findViewById(R.id.btnUpdateAyam);
        btnUpdateAyam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAyam();
            }
        });
    }

    private void updateAyam() {
        ayamMati = txtAyamMati.getText().toString().trim();
        ayamSakit = txtAyamSakit.getText().toString().trim();
        if (ayamMati.equalsIgnoreCase("") ||
                ayamSakit.equalsIgnoreCase("")) {
            Toast.makeText(this, "yang kosong mohon diisi angka 0", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, updateAyamUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean status = jsonObject.getBoolean("status");
                                if (status) {
                                    Toast.makeText(PeternakAddStatusAyam.this, "Data berhasil di update", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                } else {
                                    Toast.makeText(PeternakAddStatusAyam.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(PeternakAddStatusAyam.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PeternakAddStatusAyam.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id_kandang", kandang);
                    map.put("ayam_mati", ayamMati);
                    map.put("ayam_sakit", ayamSakit);
                    return map;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}
