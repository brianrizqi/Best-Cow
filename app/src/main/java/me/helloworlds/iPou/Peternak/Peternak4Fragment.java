package me.helloworlds.iPou.Peternak;


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

import me.helloworlds.iPou.Adapter.PeternakTransactionAdapter;
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_peternak_transaction;
import me.helloworlds.iPou.PeternakDetailOrder;
import me.helloworlds.iPou.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak4Fragment extends Fragment {
    private ListView listView;
    private PeternakTransactionAdapter adapter;
    private List<m_peternak_transaction> list;

    public Peternak4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak4, container, false);
        listView = (ListView) view.findViewById(R.id.listTransactionPeternak);
        list = new ArrayList<>();
        adapter = new PeternakTransactionAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        getOrder();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_peternak_transaction m = list.get(position);
                if (m.getVerif().equalsIgnoreCase("Belum Verifikasi")) {
                    Intent i = new Intent(getActivity(), PeternakDetailOrder.class);
                    i.putExtra("id_transaksi", m.getId());
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Sudah Verifikasi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void getOrder() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tampilOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final m_peternak_transaction m = new m_peternak_transaction();
                                JSONObject object = jsonArray.getJSONObject(i);
                                m.setId(object.getString("id_transaksi"));
                                m.setJumlah(object.getString("jumlah"));
                                m.setPembeli(object.getString("name"));
                                m.setVerif(object.getString("verif"));
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
