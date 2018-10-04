package me.helloworlds.iPou.Peternak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import me.helloworlds.iPou.Adapter.PeternakDetailInvestAdapter;
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_peternak_detail_invest;
import me.helloworlds.iPou.R;

public class PeternakDetailInvest extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<m_peternak_detail_invest> list = new ArrayList<m_peternak_detail_invest>();
    private String tampilInvestorUrl = BaseAPI.tampilInvestorURL;
    private String idKandang,kandang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peternak_detail_invest);
        idKandang = getIntent().getStringExtra("id_kandang");
        kandang = getIntent().getStringExtra("kandang");
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PeternakDetailInvestAdapter(list, this);
        recyclerView.setAdapter(adapter);
        getInvestor();
    }

    private void getInvestor() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tampilInvestorUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0; i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                final m_peternak_detail_invest m = new m_peternak_detail_invest();

                                m.setId(object.getString("id_investasi"));
                                m.setName(object.getString("name"));
                                m.setVerif(object.getString("verif_investasi"));

                                list.add(m);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(PeternakDetailInvest.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PeternakDetailInvest.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
