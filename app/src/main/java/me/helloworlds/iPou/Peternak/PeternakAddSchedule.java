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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.R;

public class PeternakAddSchedule extends AppCompatActivity {
    private TextView txtKandang;
    private EditText editKet;
    private Button btnAddSchedule;
    private String tambahScheduleUrl = BaseAPI.tambahScheduleURL;
    private String kandang, ket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peternak_add_schedule);
        txtKandang = (TextView) findViewById(R.id.txtKandang);
        editKet = (EditText) findViewById(R.id.editKet);
        btnAddSchedule = (Button) findViewById(R.id.btnAddSchedule);
        kandang = getIntent().getStringExtra("id_kandang");
        txtKandang.setText("Kandang " + kandang);
        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSchedule();
                Toast.makeText(PeternakAddSchedule.this, getTanggal(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createSchedule() {
        ket = editKet.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tambahScheduleUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status){
                                Toast.makeText(PeternakAddSchedule.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                                finish();
                            } else {
                                Toast.makeText(PeternakAddSchedule.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            Toast.makeText(PeternakAddSchedule.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakAddSchedule.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_kandang", kandang);
                map.put("tanggal", getTanggal());
                map.put("ket", ket);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
        Date date = new Date();
        return dateFormat.format(date);

    }
}
