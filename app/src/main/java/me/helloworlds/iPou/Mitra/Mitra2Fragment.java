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
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_mitra_invest;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mitra2Fragment extends Fragment {
    private ListView listView;
    private List<m_mitra_invest> list;
    private MitraInvestAdapter adapter;
    private String get_kandangUrl = BaseAPI.tampilKandangURL;
    private String get_investorUrl = BaseAPI.tampilTotalInvestorURL;
    private String idKandang, idUser, user;
    private TinyDB tinyDB;

    public Mitra2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mitra2, container, false);
        tinyDB = new TinyDB(getActivity());
        listView = (ListView) view.findViewById(R.id.listInvestMitra);
        list = new ArrayList<>();
        adapter = new MitraInvestAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        user = getArguments().getString("name");
        idUser = tinyDB.getString("id_user");
        getKandang();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_mitra_invest m = list.get(position);
                Intent i = new Intent(getActivity(), MitraDetailInvest.class);
                i.putExtra("id_kandang", m.getId());
                i.putExtra("kandang", m.getKandang());
                i.putExtra("id_user", idUser);
                i.putExtra("count(*)", m.getInvestor());
                startActivity(i);
            }
        });
        return view;
    }

    private void getKandang() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_investorUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            boolean error = jsonObject.getBoolean("error");
                            if (!error) {
                                for (int i = 0; i < 12; i++) {
                                    final m_mitra_invest m = new m_mitra_invest();
                                    m.setId(String.valueOf(i + 1));
                                    m.setKandang(String.valueOf(i + 1));
                                    m.setInvestor("0");
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        JSONObject object = jsonArray.getJSONObject(j);
                                        if (object.getString("id_kandang").equalsIgnoreCase(String.valueOf(i + 1))) {
                                            m.setInvestor(object.getString("count(*)"));
                                        }
                                    }
                                    list.add(m);
                                }
                            } else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    final m_mitra_invest m = new m_mitra_invest();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    m.setId(object.getString("id_kandang"));
                                    m.setKandang(object.getString("kandang"));
                                    m.setInvestor("0");
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
