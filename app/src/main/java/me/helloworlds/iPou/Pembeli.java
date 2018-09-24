package me.helloworlds.iPou;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Pembeli extends AppCompatActivity {
    RelativeLayout fragment;
    android.support.v4.app.Fragment home, trans, other;
    String id,nama,email,alamat;

    private BottomNavigationView.OnNavigationItemSelectedListener listener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle data = new Bundle();
            android.support.v4.app.Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = home;
                    if (fragment == null) {
                        home = new Pembeli1Fragment();
                    }
                    break;
                case R.id.navigation_transaction:
                    fragment = trans;
                    if (fragment == null) {
                        trans = new Pembeli2Fragment();
                        fragment = trans;
                    }
                    break;
                case R.id.navigation_other:
                    data.putString("id_user",id);
                    data.putString("name",nama);
                    data.putString("username",email);
                    data.putString("alamat",alamat);
                    fragment = other;
                    if (fragment == null) {
                        other = new Pembeli3Fragment();
                        fragment = other;
                        other.setArguments(data);
                    }
                    break;
            }
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
            return fragment != null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembeli);
        fragment = (RelativeLayout) findViewById(R.id.fragment);

        Pembeli1Fragment pembeli1Fragment = new Pembeli1Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, pembeli1Fragment);
        fragmentTransaction.commit();

        id = getIntent().getStringExtra("id_user");
        nama = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("username");
        alamat = getIntent().getStringExtra("alamat");

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        navigationView.setOnNavigationItemSelectedListener(listener);
        BottomNavigationHelper.disableShiftMode(navigationView);

    }
}
