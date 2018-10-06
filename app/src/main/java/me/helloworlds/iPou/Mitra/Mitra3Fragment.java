package me.helloworlds.iPou.Mitra;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import me.helloworlds.iPou.Adapter.MitraScheduleAdapter;
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_mitra_schedule;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mitra3Fragment extends Fragment {
    private ListView listView;
    private MitraScheduleAdapter adapter;
    private List<m_mitra_schedule> list;
    private String tampilJadwalMitraUrl = BaseAPI.tampilJadwalMitraURL;
    private TinyDB tinyDB;
    private String idUser;

    public Mitra3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mitra3, container, false);
        tinyDB = new TinyDB(getActivity());
        idUser = tinyDB.getString("id_user");
        listView = (ListView) view.findViewById(R.id.listScheduleMitra);
        list = new ArrayList<>();
        adapter = new MitraScheduleAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        tampilJadwal();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_mitra_schedule m = list.get(position);
                Intent i = new Intent(getActivity(),MitraDetailSchedule.class);
                i.putExtra("id_kandang",m.getKandang());
                startActivity(i);
            }
        });
        return view;
    }

    private void tampilJadwal() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tampilJadwalMitraUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final m_mitra_schedule m = new m_mitra_schedule();
                                JSONObject object = jsonArray.getJSONObject(i);
                                m.setKandang(object.getString("id_kandang"));
                                m.setAyammati(object.getString("ayam_mati"));
                                m.setAyamsakit(object.getString("ayam_sakit"));
                                m.setCount(object.getString("count(*)"));
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
