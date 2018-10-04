package me.helloworlds.iPou.Peternak;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.helloworlds.iPou.Adapter.PeternakProductAdapter;
import me.helloworlds.iPou.Model.m_peternak_product;
import me.helloworlds.iPou.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak3Fragment extends Fragment {
    private ListView listView;
    private PeternakProductAdapter adapter;
    private List<m_peternak_product> list = new ArrayList<m_peternak_product>();

    public Peternak3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak3, container, false);
        listView = (ListView) view.findViewById(R.id.listProductPeternak);
        list.add(new m_peternak_product("1","","10000","10"));
        list.add(new m_peternak_product("1","","10000","10"));
        adapter = new PeternakProductAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        return view;
    }

}
