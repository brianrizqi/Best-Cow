package me.helloworlds.iPou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Register extends AppCompatActivity {
    private EditText txtName, txtEmail, txtUsername, txtPassword;
    private Button regis;
    private String name, email, username, password,level;
    private RadioGroup radioGroup;
    private RadioButton radioMitra,radioPembeli;
    private String regisUrl = BaseAPI.registerURL;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtName = (EditText) findViewById(R.id.nameRegis);
        txtEmail = (EditText) findViewById(R.id.emailRegis);
        txtUsername = (EditText) findViewById(R.id.userRegis);
        txtPassword = (EditText) findViewById(R.id.passRegis);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioMitra = (RadioButton) findViewById(R.id.radioMitra);
        radioPembeli = (RadioButton) findViewById(R.id.radioPembeli);
        regis = (Button) findViewById(R.id.btnRegis);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegis();
            }
        });
    }

    private void userRegis() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        username = txtUsername.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == radioMitra.getId()){
            level = "2";
        } else if (selectedId == radioPembeli.getId()){
            level = "3";
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage("Tunggu ya :)");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, regisUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                Toast.makeText(Register.this, "Register Success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Register.this,Login.class);
                                startActivity(i);
                                finish();
                            } else {
                                String errorMsg = jsonObject.getString("error_msg");
                                Toast.makeText(Register.this, errorMsg, Toast.LENGTH_SHORT).show();
                                hideDialog();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            hideDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> map = new HashMap<String, String>();
                map.put("name",name);
                map.put("email",email);
                map.put("username",username);
                map.put("password",password);
                map.put("level",level);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideDialog();
    }

    private void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
