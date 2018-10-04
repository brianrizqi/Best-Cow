package me.helloworlds.iPou.Pembeli;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.helloworlds.iPou.Login;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pembeli3Fragment extends Fragment {
    private Button logout,editProfile;
    private TextView txtUser,txtEmail;
    private String user,email,id;
    private TinyDB tinyDB;

    public Pembeli3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pembeli3, container, false);
        tinyDB = new TinyDB(getActivity());
        txtEmail = (TextView) view.findViewById(R.id.emailProfile);
        txtUser = (TextView) view.findViewById(R.id.userProfile);
        editProfile = (Button) view.findViewById(R.id.btnEditProfile);
        user = getArguments().getString("name");
        email = getArguments().getString("username");
        id = getArguments().getString("id_user");
        txtUser.setText(user);
        txtEmail.setText("@"+email);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),PembeliEditProfile.class);
                i.putExtra("id_user",id);
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
