package me.helloworlds.iPou.Peternak;


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
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Peternak1Fragment extends Fragment {
    private TextView txtDate, txtHargaSkrg, txtHargaKmrn, txtSatuan;
    private String satuan, hargakemarin, hargasekarang;
    private String HargaAyamUrl = BaseAPI.hargaayamURL;

    public Peternak1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peternak1, container, false);
        txtDate = (TextView) view.findViewById(R.id.dateHome);
        txtSatuan = (TextView) view.findViewById(R.id.satuan);
        txtHargaSkrg = (TextView) view.findViewById(R.id.hargasekarang);
        txtHargaKmrn = (TextView) view.findViewById(R.id.hargakemarin);
        txtDate.setText(getTanggal());
//        getHargaPasar();
        return view;
    }

    private void getHargaPasar() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HargaAyamUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            if (!error) {
                                satuan = jsonObject.getString("satuan").toString();
                                hargakemarin = jsonObject.getString("hargakemarin").toString();
                                hargasekarang = jsonObject.getString("hargasekarang").toString();

                                txtSatuan.setText("Satuan : " + satuan);
                                txtHargaKmrn.setText("Harga Kemarin : Rp." + hargakemarin);
                                txtHargaSkrg.setText("Harga Sekarang : Rp." + hargasekarang);
                            }
                        } catch (JSONException e) {
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
