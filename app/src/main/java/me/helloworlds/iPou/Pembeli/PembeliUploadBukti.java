package me.helloworlds.iPou.Pembeli;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.CustomNetworkImageView;
import me.helloworlds.iPou.R;

public class PembeliUploadBukti extends AppCompatActivity {
    private ImageButton imgUpload;
    private CustomNetworkImageView imgBukti;
    private Bitmap bitmap;
    private String idTransaksi, bukti;
    private Button upload;
    private NetworkImageView img;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private TextView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembeli_upload_bukti);
        idTransaksi = getIntent().getStringExtra("id_transaksi");
        imgBukti = (CustomNetworkImageView) findViewById(R.id.imageBukti);
        imgUpload = (ImageButton) findViewById(R.id.imageUpload);
        foto = (TextView) findViewById(R.id.txtFoto);
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);
            }
        });
        upload = (Button) findViewById(R.id.btnUpload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBukti();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgBukti.setLocalImageBitmap(bitmap);
                foto.setText(" ");
                imgUpload.setBackground(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private void uploadBukti() {
        String cek = foto.getText().toString();
        if (cek.equalsIgnoreCase(" ")) {
            bukti = imageToString(bitmap);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseAPI.uploadBuktiOrder,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean status = jsonObject.getBoolean("status");
                                if (status) {
                                    Toast.makeText(PembeliUploadBukti.this, "Data Berhasil di upload", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                    finish();
                                } else {
                                    Toast.makeText(PembeliUploadBukti.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(PembeliUploadBukti.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PembeliUploadBukti.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id_transaksi", idTransaksi);
                    map.put("bukti", bukti);
                    return map;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        } else{
            Toast.makeText(this, "Bukti Pembayaran belum diupload", Toast.LENGTH_SHORT).show();
        }
    }
}
