package me.helloworlds.iPou.Peternak;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
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
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_peternak_detail_schedule;
import me.helloworlds.iPou.R;

public class PeternakDetailSchedule extends AppCompatActivity {
    private Toolbar toolbar;
    private String ayamsakit, ayammati, kandang, idJadwal;
    private ListView listView;
    private FloatingActionButton fabSchedule, fabadd, fabket;
    private Animation fabOpen, fabClose, fabClockwise, fabAnticlock;
    private PeternakDetailScheduleAdapter adapter;
    private List<m_peternak_detail_schedule> list = new ArrayList<>();
    private String tampilJadwalUrl = BaseAPI.tampilJadwalURL;
    private String hapusJadwalUrl = BaseAPI.hapusJadwalURL;
    private TextView txtFab1, txtFab2;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isOpen = false;

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
        ayammati = getIntent().getStringExtra("ayam_mati");
        ayamsakit = getIntent().getStringExtra("ayam_sakit");
        txtFab1 = (TextView) findViewById(R.id.txtFab1);
        txtFab2 = (TextView) findViewById(R.id.txtFab2);
        fabSchedule = (FloatingActionButton) findViewById(R.id.fabschedule);
        fabadd = (FloatingActionButton) findViewById(R.id.fabadd);
        fabket = (FloatingActionButton) findViewById(R.id.fabket);
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabAnticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);
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
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeDetailSchedule);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJadwal();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        fabadd.startAnimation(fabClose);
        fabket.startAnimation(fabClose);
        txtFab1.startAnimation(fabClose);
        txtFab2.startAnimation(fabClose);
        fabSchedule.startAnimation(fabAnticlock);
        fabadd.setClickable(false);
        fabket.setClickable(false);
        getJadwal();
        fabSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    fabadd.startAnimation(fabClose);
                    fabket.startAnimation(fabClose);
                    txtFab1.startAnimation(fabClose);
                    txtFab2.startAnimation(fabClose);
                    fabSchedule.startAnimation(fabAnticlock);
                    fabadd.setClickable(false);
                    fabket.setClickable(false);
                    isOpen = false;
                } else {
                    fabadd.startAnimation(fabOpen);
                    fabket.startAnimation(fabOpen);
                    txtFab1.startAnimation(fabOpen);
                    txtFab2.startAnimation(fabOpen);
                    fabSchedule.startAnimation(fabClockwise);
                    fabadd.setClickable(true);
                    fabket.setClickable(true);
                    isOpen = true;
                    fabket.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PeternakDetailSchedule.this, PeternakAddSchedule.class);
                            i.putExtra("id_kandang", kandang);
                            startActivity(i);
                        }
                    });
                    fabadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(PeternakDetailSchedule.this, PeternakAddStatusAyam.class);
                            i.putExtra("ayam_mati", ayammati);
                            i.putExtra("ayam_sakit", ayamsakit);
                            i.putExtra("id_kandang", kandang);
                            startActivity(i);
                        }
                    });
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_peternak_detail_schedule m = list.get(position);
                idJadwal = m.getId();
                dialogButton(idJadwal);
            }
        });
    }

    private void dialogButton(final String idJadwal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PeternakDetailSchedule.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_delete, null);
        Button dialogButton = (Button) view.findViewById(R.id.nextButton);
        Button editButton = (Button) view.findViewById(R.id.editButton);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSchedule(idJadwal);
                dialog.dismiss();
                getJadwal();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PeternakEditSchedule.class);
                i.putExtra("id_jadwal", idJadwal);
                startActivity(i);
                dialog.dismiss();
            }
        });
    }

    private void deleteSchedule(final String idJadwal) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, hapusJadwalUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                Toast.makeText(PeternakDetailSchedule.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PeternakDetailSchedule.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
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
                map.put("id_jadwal", idJadwal);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
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
