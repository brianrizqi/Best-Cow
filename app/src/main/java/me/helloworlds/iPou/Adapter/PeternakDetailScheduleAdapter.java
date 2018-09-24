package me.helloworlds.iPou.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.helloworlds.iPou.Model.m_peternak_detail_schedule;
import me.helloworlds.iPou.R;

public class PeternakDetailScheduleAdapter extends BaseAdapter {
    private Context context;
    private List<m_peternak_detail_schedule> list;

    public PeternakDetailScheduleAdapter(Context context, List<m_peternak_detail_schedule> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.list_peternak_detail_schedule, null);
        m_peternak_detail_schedule m = list.get(position);

        TextView txtTanggal = (TextView) view.findViewById(R.id.txtTanggal);
        TextView txtKet = (TextView) view.findViewById(R.id.txtKeterangan);

        txtTanggal.setText("Tanggal " + m.getTanggal());
        txtKet.setText("Keterangan : " + m.getKet());
        view.setTag(m.getId());
        return view;
    }
}
