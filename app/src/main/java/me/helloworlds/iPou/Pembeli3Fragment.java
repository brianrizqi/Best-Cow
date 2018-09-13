package me.helloworlds.iPou;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pembeli3Fragment extends Fragment {
    private Button logout;
    private TextView txtUser,txtEmail;
    private String user,email;

    public Pembeli3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pembeli3, container, false);
        txtEmail = (TextView) view.findViewById(R.id.emailProfile);
        txtUser = (TextView) view.findViewById(R.id.userProfile);
        user = getArguments().getString("name");
        email = getArguments().getString("username");
        txtUser.setText(user);
        txtEmail.setText("@"+email);
        logout = (Button) view.findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Login.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        return view;
    }

}
