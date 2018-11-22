package me.helloworlds.iPou.Pembeli;


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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.helloworlds.iPou.Adapter.PembeliTransaksiAdapter;
import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_pembeli_transaksi;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pembeli2Fragment extends Fragment {
    private ListView listView;
    private PembeliTransaksiAdapter adapter;
    private List<m_pembeli_transaksi> list;
    private TinyDB tinyDB;
    private String idUser;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public Pembeli2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pembeli2, container, false);
        tinyDB = new TinyDB(getActivity());
        idUser = tinyDB.getString("id_user");
        Toast.makeText(getActivity(), idUser, Toast.LENGTH_SHORT).show();
        listView = (ListView) view.findViewById(R.id.listTransaksiPembeli);
        list = new ArrayList<>();
        adapter = new PembeliTransaksiAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        getOrder();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_pembeli_transaksi m = list.get(position);
                Intent i = new Intent(getActivity(), PembeliUploadBukti.class);
                i.putExtra("id_transaksi", m.getId());
                startActivity(i);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
            }
        });
        return view;
    }

    private void getOrder() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tampilOrderId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            String verif, bukti;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final m_pembeli_transaksi m = new m_pembeli_transaksi();
                                JSONObject object = jsonArray.getJSONObject(i);
                                m.setId(object.getString("id_transaksi"));
                                m.setJumlah("Jumlah : " + object.getString("jumlah"));
                                m.setImg(BaseAPI.gambarURL + object.getString("gambar"));
                                if (object.getString("verif").equalsIgnoreCase("0")) {
                                    verif = "Belum Verifikasi";
                                } else {
                                    verif = "Sudah Verifikasi";
                                }
                                if (object.getString("bukti").equalsIgnoreCase("null")) {
                                    bukti = "Belum Upload";
                                } else {
                                    bukti = "Sudah Upload";
                                }
                                m.setBukti(bukti);
                                m.setStatus(verif);
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
