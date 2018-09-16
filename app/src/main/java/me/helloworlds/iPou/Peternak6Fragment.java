package me.helloworlds.iPou;


import android.content.Intent;
import android.os.Bundle;
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

import me.helloworlds.iPou.Adapter.MitraInvestAdapter;
import me.helloworlds.iPou.Model.m_mitra_invest;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak6Fragment extends Fragment {
    private ListView listView;
    private List<m_mitra_invest> list;
    private MitraInvestAdapter adapter;
    private String get_kandangUrl = BaseAPI.tampilKandangURL;
    private String idKandang,idUser,user;

    public Peternak6Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak6, container, false);
        listView = (ListView) view.findViewById(R.id.listInvestPeternak);
        list = new ArrayList<>();
        adapter = new MitraInvestAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        getKandang();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_mitra_invest m = list.get(position);
                Intent i = new Intent(getActivity(),PeternakDetailInvest.class);
                i.putExtra("id_kandang",m.getId());
                i.putExtra("kandang",m.getKandang());
                startActivity(i);
            }
        });
        return view;
    }

    private void getKandang() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_kandangUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                final m_mitra_invest m = new m_mitra_invest();
                                m.setId(object.getString("id_kandang"));
                                m.setKandang(object.getString("kandang"));
                                m.setInvestor("0");
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
