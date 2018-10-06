package me.helloworlds.iPou.Peternak;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.helloworlds.iPou.Adapter.PeternakScheduleAdapter;
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_peternak_schedule;
import me.helloworlds.iPou.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak2Fragment extends Fragment {
    private ListView listView;
    private PeternakScheduleAdapter adapter;
    private List<m_peternak_schedule> list = new ArrayList<>();
    private String tampilStatusAyamUrl = BaseAPI.tampilStatusAyamURL;
    private SwipeRefreshLayout swipeRefreshLayout;

    public Peternak2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak2, container, false);
        listView = (ListView) view.findViewById(R.id.listSchedulePeternak);
        adapter = new PeternakScheduleAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        getStatusAyam();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeSchedulePeternak);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStatusAyam();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_peternak_schedule m = list.get(position);
                if (m.getCount().equalsIgnoreCase("0")) {
                    Toast.makeText(getActivity(), "Belum ada investor", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(getActivity(), PeternakDetailSchedule.class);
                    i.putExtra("id_kandang", m.getId());
                    i.putExtra("ayam_mati", m.getAyammati());
                    i.putExtra("ayam_sakit", m.getAyamsakit());
                    startActivity(i);
                }
            }
        });
        return view;
    }

    private void getStatusAyam() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tampilStatusAyamUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            boolean error = jsonObject.getBoolean("error");
                            if (!error) {
                                for (int i = 0; i < 12; i++) {
                                    final m_peternak_schedule m = new m_peternak_schedule();
                                    m.setId(String.valueOf(i + 1));
                                    m.setKandang(String.valueOf(i + 1));
                                    m.setAyammati("0");
                                    m.setAyamsakit("0");
                                    m.setCount("0");
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        JSONObject object = jsonArray.getJSONObject(j);
                                        if (object.getString("id_kandang").equalsIgnoreCase(String.valueOf(i + 1))) {
                                            m.setAyammati(object.getString("ayam_mati"));
                                            m.setAyamsakit(object.getString("ayam_sakit"));
                                            m.setCount(object.getString("count(*)"));
                                        }
                                    }
                                    list.add(m);
                                }
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    final m_peternak_schedule m = new m_peternak_schedule();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    m.setId(object.getString("id_kandang"));
                                    m.setKandang(object.getString("kandang"));
                                    m.setAyamsakit("0");
                                    m.setAyammati("0");
                                    m.setCount("0");
                                    list.add(m);
                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
