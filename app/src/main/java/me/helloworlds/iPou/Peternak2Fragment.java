package me.helloworlds.iPou;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import me.helloworlds.iPou.Model.m_peternak_schedule;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak2Fragment extends Fragment {
    private ListView listView;
    private PeternakScheduleAdapter adapter;
    private List<m_peternak_schedule> list = new ArrayList<>();
    private String tampilStatusAyamUrl = BaseAPI.tampilStatusAyamURL;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_peternak_schedule m = list.get(position);
                Intent i = new Intent(getActivity(), PeternakDetailSchedule.class);
                i.putExtra("id_kandang", m.getId());
                i.putExtra("ayam_mati", m.getAyammati());
                i.putExtra("ayam_sakit", m.getAyamsakit());
                startActivity(i);
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
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                final m_peternak_schedule m = new m_peternak_schedule();
                                m.setId(object.getString("id_kandang"));
                                m.setKandang(String.valueOf(i + 1));
                                if (object.getString("ayam_mati").equalsIgnoreCase("null")) {
                                    m.setAyammati("0");
                                } else {
                                    m.setAyammati(object.getString("ayam_mati"));
                                }
                                if (object.getString("ayam_sakit").equalsIgnoreCase("null")) {
                                    m.setAyamsakit("0");
                                } else {
                                    m.setAyamsakit(object.getString("ayam_sakit"));
                                }
                                list.add(m);
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
