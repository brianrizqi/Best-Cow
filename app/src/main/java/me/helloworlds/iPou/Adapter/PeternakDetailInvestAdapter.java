package me.helloworlds.iPou.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import me.helloworlds.iPou.BaseAPI;
import me.helloworlds.iPou.Model.m_peternak_detail_invest;
import me.helloworlds.iPou.Peternak.PeternakVerifInvest;
import me.helloworlds.iPou.R;
import me.helloworlds.iPou.TinyDB;

public class PeternakDetailInvestAdapter extends RecyclerView.Adapter<PeternakDetailInvestAdapter.ViewHolder> {
    private List<m_peternak_detail_invest> list;
    private Context context;
    private String verifInvestUrl = BaseAPI.verifInvestURL;
    private TinyDB tinyDB;

    public PeternakDetailInvestAdapter(List<m_peternak_detail_invest> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_peternak_detail_invest, parent, false);
        tinyDB = new TinyDB(context);
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
                Intent i = new Intent(context, PeternakVerifInvest.class);
//                i.putExtra("id_investasi", idInvest);
                tinyDB.putString("id_investasi", idInvest);
                context.startActivity(i);
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


}
