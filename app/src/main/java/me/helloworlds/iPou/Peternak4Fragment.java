package me.helloworlds.iPou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.helloworlds.iPou.Adapter.PeternakTransactionAdapter;
import me.helloworlds.iPou.Model.m_peternak_transaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak4Fragment extends Fragment {
    private ListView listView;
    private PeternakTransactionAdapter adapter;
    private List<m_peternak_transaction> list = new ArrayList<m_peternak_transaction>();

    public Peternak4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak4, container, false);
        listView = (ListView) view.findViewById(R.id.listTransactionPeternak);
        list.add(new m_peternak_transaction("1","Brian","100","Verifikasi"));
        list.add(new m_peternak_transaction("1","Rizqi","10","Belum Verifikasi"));
        adapter = new PeternakTransactionAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        return view;
    }

}
