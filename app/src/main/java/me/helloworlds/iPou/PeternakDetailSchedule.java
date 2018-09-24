package me.helloworlds.iPou;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.helloworlds.iPou.Adapter.PeternakDetailScheduleAdapter;
import me.helloworlds.iPou.Model.m_peternak_detail_schedule;

public class PeternakDetailSchedule extends AppCompatActivity {
    private Toolbar toolbar;
    private String ayamsakit, ayammati, kandang;
    private ListView listView;
    private FloatingActionButton fabSchedule;
    private PeternakDetailScheduleAdapter adapter;
    private List<m_peternak_detail_schedule> list = new ArrayList<>();
    private String tampilJadwalUrl = BaseAPI.tampilJadwalURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peternak_detail_schedule);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        kandang = getIntent().getStringExtra("id_kandang");
        listView = (ListView) findViewById(R.id.listDetailSchedulePeternak);
        ViewCompat.setNestedScrollingEnabled(listView, true);
        adapter = new PeternakDetailScheduleAdapter(this, list);
        listView.setAdapter(adapter);
        fabSchedule = (FloatingActionButton) findViewById(R.id.fabschedule);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kandang " + kandang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                    finish();
                }
            });
        }
        getJadwal();
        fabSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PeternakDetailSchedule.this, PeternakAddSchedule.class);
                i.putExtra("id_kandang", kandang);
                startActivity(i);
            }
        });
    }

    private void getJadwal() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tampilJadwalUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                final m_peternak_detail_schedule m = new m_peternak_detail_schedule();
                                m.setId(object.getString("id_jadwal"));
                                m.setTanggal(object.getString("tanggal"));
                                m.setKet(object.getString("ket"));
                                list.add(m);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PeternakDetailSchedule.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakDetailSchedule.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_kandang", kandang);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
