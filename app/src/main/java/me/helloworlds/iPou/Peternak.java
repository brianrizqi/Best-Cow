package me.helloworlds.iPou;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Peternak extends AppCompatActivity {
    RelativeLayout fragment;
    android.support.v4.app.Fragment home, invest,trans,other,product;

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
                        home = new Peternak1Fragment();
                    }
                    break;
                case R.id.navigation_schedule:
                    fragment = invest;
                    if (fragment == null) {
                        invest = new Peternak2Fragment();
                        fragment = invest;
                    }
                    break;
                case R.id.navigation_product:
                    fragment = product;
                    if (fragment == null) {
                        product = new Peternak3Fragment();
                        fragment = product;
                    }
                    break;
                case R.id.navigation_transaction:
                    fragment = trans;
                    if (fragment == null) {
                        trans = new Peternak4Fragment();
                        fragment = trans;
                    }
                    break;
                case R.id.navigation_other:
                    fragment = other;
                    if (fragment == null) {
                        other = new Peternak5Fragment();
                        fragment = other;
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
        setContentView(R.layout.activity_peternak);
        fragment = (RelativeLayout) findViewById(R.id.fragment);

        Peternak1Fragment peternak1Fragment = new Peternak1Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, peternak1Fragment);
        fragmentTransaction.commit();

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        navigationView.setOnNavigationItemSelectedListener(listener);
        BottomNavigationHelper.disableShiftMode(navigationView);
    }
}
