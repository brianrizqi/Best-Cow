package me.helloworlds.iPou.Pembeli;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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

import me.helloworlds.iPou.Adapter.PembeliHomeAdapter;
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_pembeli_home;
import me.helloworlds.iPou.PembeliDetailProduct;
import me.helloworlds.iPou.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pembeli1Fragment extends Fragment {
    private GridView gridView;
    private PembeliHomeAdapter adapter;
    private List<m_pembeli_home> list;

    public Pembeli1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pembeli1, container, false);
        gridView = (GridView) view.findViewById(R.id.grid);
        list = new ArrayList<>();
        adapter = new PembeliHomeAdapter(getActivity(),list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_pembeli_home m = list.get(position);
                Intent i = new Intent(getActivity(),PembeliDetailProduct.class);
                i.putExtra("harga",m.getHarga());
                i.putExtra("stok",m.getStok());
                i.putExtra("gambar",m.getImg());
                i.putExtra("id_produk",m.getId());
                startActivity(i);
            }
        });
        getProduct();
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
                                final m_pembeli_home m = new m_pembeli_home();
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
