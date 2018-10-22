package me.helloworlds.iPou.Peternak;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import me.helloworlds.iPou.Adapter.PeternakProductAdapter;
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_peternak_product;
import me.helloworlds.iPou.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak3Fragment extends Fragment {
    private ListView listView;
    private PeternakProductAdapter adapter;
    private List<m_peternak_product> list;
    private FloatingActionButton fab;

    public Peternak3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak3, container, false);
        listView = (ListView) view.findViewById(R.id.listProductPeternak);
        list = new ArrayList<>();
        adapter = new PeternakProductAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        getProduct();
        fab = (FloatingActionButton) view.findViewById(R.id.addProduct);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PeternakAddProduct.class);
                startActivity(i);
            }
        });
        return view;
    }

    private void getProduct() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tampilProduk,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final m_peternak_product m = new m_peternak_product();
                                JSONObject object = jsonArray.getJSONObject(i);
                                m.setHarga(object.getString("harga"));
                                m.setStok(object.getString("jumlah"));
                                m.setImg(BaseAPI.gambarURL + object.getString("gambar"));
                                m.setId(object.getString("id_produk"));
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
