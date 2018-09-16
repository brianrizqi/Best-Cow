package me.helloworlds.iPou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.helloworlds.iPou.Adapter.PeternakScheduleAdapter;
import me.helloworlds.iPou.Model.m_peternak_schedule;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak2Fragment extends Fragment {
    private ListView listView;
    private PeternakScheduleAdapter adapter;
    private List<m_peternak_schedule> list = new ArrayList<>();

    public Peternak2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak2, container, false);
        listView = (ListView) view.findViewById(R.id.listSchedulePeternak);
        list.add(new m_peternak_schedule("1","Kandang 1","Ayam mati : 1","Ayam Sakit : 2"));
        adapter = new PeternakScheduleAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        return view;
    }

}
