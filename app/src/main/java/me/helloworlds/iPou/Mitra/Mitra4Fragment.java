package me.helloworlds.iPou.Mitra;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.helloworlds.iPou.Login;
import me.helloworlds.iPou.Pembeli.PembeliEditProfile;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mitra4Fragment extends Fragment {
    private Button logout, editProfile, invest;
    private TextView txtUser;
    private String user, ipay, id;
    private TinyDB tinyDB;

    public Mitra4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mitra4, container, false);
        tinyDB = new TinyDB(getActivity());
        txtUser = (TextView) view.findViewById(R.id.userProfile);
        editProfile = (Button) view.findViewById(R.id.btnEditProfile);
        user = tinyDB.getString("name");
        id = tinyDB.getString("id_user");
        txtUser.setText(user);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PembeliEditProfile.class);
                i.putExtra("id_user", id);
                startActivity(i);
            }
        });
        logout = (Button) view.findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Login.class);
                tinyDB.clear();
                startActivity(i);
                getActivity().finish();
            }
        });
        invest = (Button) view.findViewById(R.id.btnInvest);
        invest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MitraStatusInvest.class);
                startActivity(i);
            }
        });
        return view;
    }

}
