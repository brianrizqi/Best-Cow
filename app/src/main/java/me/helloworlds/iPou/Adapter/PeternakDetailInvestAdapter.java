package me.helloworlds.iPou.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_peternak_detail_invest;
import me.helloworlds.iPou.R;

public class PeternakDetailInvestAdapter extends RecyclerView.Adapter<PeternakDetailInvestAdapter.ViewHolder> {
    private List<m_peternak_detail_invest> list;
    private Context context;
    private String verifInvestUrl = BaseAPI.verifInvestURL;

    public PeternakDetailInvestAdapter(List<m_peternak_detail_invest> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_peternak_detail_invest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final m_peternak_detail_invest m = list.get(position);
        holder.txtId.setText(m.getId());
        holder.txtName.setText(m.getName());
        holder.btnVerif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idInvest = holder.txtId.getText().toString();
                Toast.makeText(context, holder.txtId.getText().toString(), Toast.LENGTH_SHORT).show();
                verifInvest(idInvest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtId;
        Button btnVerif;

        public ViewHolder(View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.txtIdInvest);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            btnVerif = (Button) itemView.findViewById(R.id.btnVerif);
        }
    }

    private void verifInvest(final String idInvest) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, verifInvestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                Toast.makeText(context, "Invest telah diverifikasi", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_investasi", idInvest);
                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
