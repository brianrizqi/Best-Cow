package me.helloworlds.iPou.Mitra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import me.helloworlds.iPou.Adapter.MitraStatusInvestAdapter;
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.MitraKeteranganTolak;
import me.helloworlds.iPou.Model.m_mitra_status_invest;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;

public class MitraStatusInvest extends AppCompatActivity {
    private ListView listView;
    private List<m_mitra_status_invest> list = new ArrayList<>();
    private MitraStatusInvestAdapter adapter;
    private String idUser;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra_status_invest);
        tinyDB = new TinyDB(getApplicationContext());
        idUser = tinyDB.getString("id_user");
        listView = (ListView) findViewById(R.id.listStatusInvest);
        adapter = new MitraStatusInvestAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);
        tampilInvest();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_mitra_status_invest m = list.get(position);
                if (m.getImg().equalsIgnoreCase("Belum")) {
                    if (m.getVerif().equalsIgnoreCase("Ditolak")) {
                        Intent intent = new Intent(getApplicationContext(), MitraKeteranganTolak.class);
                        intent.putExtra("id_investasi", m.getId());
                        startActivity(intent);
                    } else if (m.getVerif().equalsIgnoreCase("Sudah Verifikasi")) {
                        Toast.makeText(getApplicationContext(), "Data telah diverifikasi", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(getApplicationContext(), MitraUploadBukti.class);
                        i.putExtra("id_investasi", m.getId());
                        startActivity(i);
                    }
                } else {
                    if (m.getVerif().equalsIgnoreCase("Ditolak")) {
                        Intent intent = new Intent(getApplicationContext(), MitraKeteranganTolak.class);
                        intent.putExtra("id_investasi", m.getId());
                        startActivity(intent);
                    } else if (m.getVerif().equalsIgnoreCase("Sudah Verifikasi")) {
                        Toast.makeText(getApplicationContext(), "Data telah diverifikasi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void tampilInvest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tampilInvestorIdURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final m_mitra_status_invest m = new m_mitra_status_invest();
                                JSONObject object = jsonArray.getJSONObject(i);
                                m.setKandang(object.getString("id_kandang"));
                                m.setId(object.getString("id_investasi"));
                                m.setHarga(object.getString("jumlah_uang"));
                                if (object.getString("img_pembayaran").equalsIgnoreCase("null")) {
                                    m.setImg("Belum");
                                } else {
                                    m.setImg("Sudah");
                                }
                                if (object.getString("verif_investasi").equalsIgnoreCase("0")) {
                                    m.setVerif("Belum Verifikasi");
                                } else if (object.getString("verif_investasi").equalsIgnoreCase("1")) {
                                    m.setVerif("Verifikasi");
                                } else {
                                    m.setVerif("Ditolak");
                                }
                                list.add(m);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MitraStatusInvest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MitraStatusInvest.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user", idUser);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
