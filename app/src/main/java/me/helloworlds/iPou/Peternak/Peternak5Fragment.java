package me.helloworlds.iPou.Peternak;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.helloworlds.iPou.Login;
import me.helloworlds.iPou.Pembeli.PembeliEditProfile;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak5Fragment extends Fragment {
    private Button logout,editProfile;
    private TinyDB tinyDB;
    public Peternak5Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak5, container, false);
        tinyDB = new TinyDB(getActivity());
        editProfile = (Button) view.findViewById(R.id.btnEditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PembeliEditProfile.class);
                i.putExtra("id_user","1");
                startActivity(i);
            }
        });
        logout = (Button) view.findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Login.class);
                startActivity(i);
                getActivity().finish();
                tinyDB.clear();
            }
        });
        return view;
    }

}
