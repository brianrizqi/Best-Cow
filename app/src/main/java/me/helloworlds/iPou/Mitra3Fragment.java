package me.helloworlds.iPou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.helloworlds.iPou.Adapter.MitraScheduleAdapter;
import me.helloworlds.iPou.Model.m_mitra_schedule;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mitra3Fragment extends Fragment {
    private ListView listView;
    private MitraScheduleAdapter adapter;
    private List<m_mitra_schedule> list = new ArrayList<>();

    public Mitra3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mitra3, container, false);
        listView = (ListView) view.findViewById(R.id.listScheduleMitra);
        list.add(new m_mitra_schedule("1","Kandang 1","Ayam mati : 1","Ayam Sakit : 2"));
        adapter = new MitraScheduleAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        return view;
    }

}
