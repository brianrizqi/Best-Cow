package me.helloworlds.iPou;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Mitra extends AppCompatActivity {
    RelativeLayout fragment;
    android.support.v4.app.Fragment home, invest, schedule ,other;
    String id,nama,email,alamat,ipay;

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
                        home = new Mitra1Fragment();
                    }
                    break;
                case R.id.navigation_investation:
                    fragment = invest;
                    data.putString("id_user", id);
                    data.putString("name", nama);
                    if (fragment == null) {
                        invest = new Mitra2Fragment();
                        fragment = invest;
                        invest.setArguments(data);
                    }
                    break;
                case R.id.navigation_schedule:
                    fragment = schedule;
                    if (fragment == null) {
                        schedule = new Mitra3Fragment();
                        fragment = schedule;
                    }
                    break;
                case R.id.navigation_other:
                    fragment = other;
                    data.putString("id_user",id);
                    data.putString("name",nama);
                    data.putString("username",email);
                    data.putString("alamat",alamat);
                    data.putString("ipay",ipay);
                    if (fragment == null) {
                        other = new Mitra4Fragment();
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
        setContentView(R.layout.activity_mitra);
        fragment = (RelativeLayout) findViewById(R.id.fragment);

        Mitra1Fragment mitra1Fragment = new Mitra1Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, mitra1Fragment);
        fragmentTransaction.commit();

        id = getIntent().getStringExtra("id_user");
        nama = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("username");
        alamat = getIntent().getStringExtra("alamat");
        ipay = getIntent().getStringExtra("ipay");

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        navigationView.setOnNavigationItemSelectedListener(listener);
        BottomNavigationHelper.disableShiftMode(navigationView);
    }
}
