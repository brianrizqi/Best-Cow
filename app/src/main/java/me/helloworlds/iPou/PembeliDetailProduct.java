package me.helloworlds.iPou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PembeliDetailProduct extends AppCompatActivity {
    private CustomNetworkImageView imgDetail;
    private TextView txtStok, txtHarga;
    private EditText txtJumlah;
    private Button beli;
    private String img, stok, harga, idProduk, idUser;
    private TinyDB tinyDB;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembeli_detail_product);
        tinyDB = new TinyDB(getApplicationContext());
        imgDetail = (CustomNetworkImageView) findViewById(R.id.imgDetail);
        txtHarga = (TextView) findViewById(R.id.txtHarga);
        txtStok = (TextView) findViewById(R.id.txtStok);
        txtJumlah = (EditText) findViewById(R.id.jumlahBeli);
        beli = (Button) findViewById(R.id.btnBeli);
        img = getIntent().getStringExtra("gambar");
        stok = getIntent().getStringExtra("stok");
        harga = getIntent().getStringExtra("harga");
        idProduk = getIntent().getStringExtra("id_produk");
        idUser = tinyDB.getString("id_user");
        txtStok.setText("Stok : " + stok);
        txtHarga.setText("Harga : " + harga);
        imgDetail.setImageUrl(img, imageLoader);
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beli();
            }
        });

    }

    private void beli() {
        final String jumlah = txtJumlah.getText().toString().trim();
        if (Integer.parseInt(jumlah) > Integer.parseInt(stok)) {
            Toast.makeText(this, "Stok kurang", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.tambahOrder,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean status = jsonObject.getBoolean("status");
                                if (status) {
                                    Toast.makeText(PembeliDetailProduct.this, "Terima kasih telah order", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                    finish();
                                } else {
                                    Toast.makeText(PembeliDetailProduct.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(PembeliDetailProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PembeliDetailProduct.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id_user", idUser);
                    map.put("id_produk", idProduk);
                    map.put("jumlah", jumlah);
                    return map;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}
