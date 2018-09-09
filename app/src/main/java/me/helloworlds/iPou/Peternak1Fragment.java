package me.helloworlds.iPou;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak1Fragment extends Fragment {
    private TextView txtDate, txtKota,txtUkuran1, txtUkuran2, txtHarga2, txtHarga1;
    private String date,kota,ukuran1,ukuran2,harga1,harga2;
    private String HargaUrl = BaseAPI.hargaURL;

    public Peternak1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak1, container, false);
        txtDate = (TextView) view.findViewById(R.id.dateHome);
        txtKota = (TextView) view.findViewById(R.id.kotaHome);
        txtUkuran1 = (TextView) view.findViewById(R.id.ukuran1);
        txtUkuran2 = (TextView) view.findViewById(R.id.ukuran2);
        txtHarga1 = (TextView) view.findViewById(R.id.harga1);
        txtHarga2 = (TextView) view.findViewById(R.id.harga2);
        getHarga();
        return view;
    }

    private void getHarga(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HargaUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            if (!error){
                                kota = jsonObject.getString("kota").toString();
                                ukuran1 = jsonObject.getString("ukuran1").toString();
                                ukuran2 = jsonObject.getString("ukuran2").toString();
                                harga1 = jsonObject.getString("harga1").toString();
                                harga2 = jsonObject.getString("harga2").toString();

                                txtDate.setText(getTanggal());
                                txtKota.setText(kota);
                                txtHarga1.setText(harga1);
                                txtHarga2.setText(harga2);
                                txtUkuran1.setText(ukuran1);
                                txtUkuran2.setText(ukuran2);
                            }
                        } catch (JSONException e){
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        Date date = new Date();
        return dateFormat.format(date);

    }
}