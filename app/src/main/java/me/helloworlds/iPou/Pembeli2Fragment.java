package me.helloworlds.iPou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.helloworlds.iPou.Adapter.PembeliTransaksiAdapter;
import me.helloworlds.iPou.Model.m_pembeli_transaksi;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pembeli2Fragment extends Fragment {
    private ListView listView;
    private PembeliTransaksiAdapter adapter;
    private List<m_pembeli_transaksi> list = new ArrayList<>();

    public Pembeli2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pembeli2, container, false);
        listView = (ListView) view.findViewById(R.id.listTransaksiPembeli);
        list.add(new m_pembeli_transaksi("1","100","","Verifikasi"));
        list.add(new m_pembeli_transaksi("1","100","","Verifikasi"));
        adapter = new PembeliTransaksiAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        return view;
    }

}
