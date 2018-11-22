package me.helloworlds.iPou;

import android.content.Intent;
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

import me.helloworlds.iPou.Mitra.Mitra;
import me.helloworlds.iPou.Pembeli.Pembeli;
import me.helloworlds.iPou.Peternak.Peternak;

public class Login extends AppCompatActivity {
    private Button regis, login;
    private EditText txtUsername, txtPassword;
    private String username, password, name, email, level, idUser, alamat, i_pay;
    private String loginUrl = BaseAPI.loginURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsername = (EditText) findViewById(R.id.userLogin);
        txtPassword = (EditText) findViewById(R.id.passLogin);
        regis = (Button) findViewById(R.id.btnRegis);
        login = (Button) findViewById(R.id.btnLogin);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        username = txtUsername.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        final TinyDB tinyDB = new TinyDB(getApplicationContext());
        if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            Toast.makeText(this, "Mohon di isi", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean error = jsonObject.getBoolean("error");
                                if (!error) {
                                    idUser = jsonObject.getString("id_user");
                                    name = jsonObject.getString("name");
                                    email = jsonObject.getString("username");
                                    alamat = jsonObject.getString("alamat");
                                    level = jsonObject.getString("level");
                                    i_pay = jsonObject.getString("i_pay");
                                    Toast.makeText(Login.this, name, Toast.LENGTH_SHORT).show();
                                    if (level.equalsIgnoreCase("1")) {
                                        Intent i = new Intent(Login.this, Peternak.class);
                                        tinyDB.putString("id_user", idUser);
                                        startActivity(i);
                                        finish();
                                    } else if (level.equalsIgnoreCase("2")) {
                                        Intent i = new Intent(Login.this, Mitra.class);
                                        tinyDB.putString("id_user", idUser);
                                        tinyDB.putString("name", name);
                                        i.putExtra("username", email);
                                        i.putExtra("alamat", alamat);
                                        startActivity(i);
                                        finish();
                                    } else if (level.equalsIgnoreCase("3")) {
                                        Intent i = new Intent(Login.this, Pembeli.class);
                                        tinyDB.putString("id_user", idUser);
                                        i.putExtra("id_user", idUser);
                                        i.putExtra("name", name);
                                        i.putExtra("username", email);
                                        i.putExtra("alamat", alamat);
                                        startActivity(i);
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(Login.this, jsonObject.getString("error_msg"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("username", username);
                    map.put("password", password);
                    return map;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    }
}
