package me.helloworlds.iPou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import me.helloworlds.iPou.Adapter.PembeliHomeAdapter;
import me.helloworlds.iPou.Model.m_pembeli_home;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pembeli1Fragment extends Fragment {
    private GridView gridView;
    private PembeliHomeAdapter adapter;
    private List<m_pembeli_home> list = new ArrayList<m_pembeli_home>();

    public Pembeli1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pembeli1, container, false);
        gridView = (GridView) view.findViewById(R.id.grid);
        list.add(new m_pembeli_home("1","","10000","10"));
        list.add(new m_pembeli_home("1","","10000","10"));
        adapter = new PembeliHomeAdapter(getActivity(),list);
        gridView.setAdapter(adapter);
        return view;
    }

}
