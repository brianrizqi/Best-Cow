package me.helloworlds.iPou.Mitra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;

public class MitraEditProfile extends AppCompatActivity {
    private EditText txtName, txtEmail, txtAlamat, txtUsername, txtPassword;
    private String name, email, alamat, username, password;
    private String id;
    private Button btnEdit;
    private String getUserUrl = BaseAPI.getUserURL;
    private String updateUserUrl = BaseAPI.updateUserURL;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra_edit_profile);
        tinyDB = new TinyDB(getApplicationContext());
        txtName = (EditText) findViewById(R.id.nameProfile);
        txtEmail = (EditText) findViewById(R.id.emailProfile);
        txtAlamat = (EditText) findViewById(R.id.alamatProfile);
        txtUsername = (EditText) findViewById(R.id.userProfile);
        txtPassword = (EditText) findViewById(R.id.passProfile);
        btnEdit = (Button) findViewById(R.id.btnUpdateProfile);
        id = tinyDB.getString("id_user");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        getUser();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void getUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getUserUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            if (!error) {
                                Toast.makeText(MitraEditProfile.this, jsonObject.getString("name"), Toast.LENGTH_SHORT).show();
                                txtName.setText(jsonObject.getString("name"));
                                txtEmail.setText(jsonObject.getString("email"));
                                txtAlamat.setText(jsonObject.getString("alamat"));
                                txtUsername.setText(jsonObject.getString("username"));
                            } else {
                                Toast.makeText(MitraEditProfile.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MitraEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MitraEditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_user", id);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void updateUser() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        alamat = txtAlamat.getText().toString().trim();
        username = txtUsername.getText().toString().trim();
        password = txtPassword.getText().toString().trim();

        if (name.equalsIgnoreCase("") || email.equalsIgnoreCase("") ||
                alamat.equalsIgnoreCase("") || username.equalsIgnoreCase("") ||
                password.equalsIgnoreCase("")) {
            Toast.makeText(this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, updateUserUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean status = jsonObject.getBoolean("status");
                                if (status) {
                                    Toast.makeText(MitraEditProfile.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                    finish();
                                } else {
                                    Toast.makeText(MitraEditProfile.this, "error", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(MitraEditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MitraEditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id_user", id);
                    map.put("name", name);
                    map.put("email", email);
                    map.put("alamat", alamat);
                    map.put("username", username);
                    map.put("password", password);
                    return map;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}
