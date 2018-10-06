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

public class PeternakEditSchedule extends AppCompatActivity {
    private EditText txtKet;
    private TextView txtKandang;
    private Button btnedit;
    private String idJadwal, ket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peternak_edit_schedule);
        btnedit = (Button) findViewById(R.id.btnEditSchedule);
        txtKet = (EditText) findViewById(R.id.editKet);
        txtKandang = (TextView) findViewById(R.id.txtKandang);
        idJadwal = getIntent().getStringExtra("id_jadwal");
        getSchedule();
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSchedule();
            }
        });
    }

    private void getSchedule() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tampilJadwalIdURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            if (!error) {
                                txtKet.setText(jsonObject.getString("ket"));
                                txtKandang.setText("Kandang " + jsonObject.getString("id_kandang"));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PeternakEditSchedule.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakEditSchedule.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_jadwal", idJadwal);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void editSchedule() {
        ket = txtKet.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.editJadwalURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                Toast.makeText(PeternakEditSchedule.this, "Data berhasil di update", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PeternakEditSchedule.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakEditSchedule.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_jadwal", idJadwal);
                map.put("ket", ket);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
