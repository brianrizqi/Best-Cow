package me.helloworlds.iPou.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.helloworlds.iPou.Model.m_peternak_schedule;
import me.helloworlds.iPou.R;

public class PeternakScheduleAdapter extends BaseAdapter {
    private Activity activity;
    private List<m_peternak_schedule>list;

    public PeternakScheduleAdapter(Activity activity, List<m_peternak_schedule> list) {
        this.activity = activity;
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
        View view = View.inflate(activity, R.layout.list_peternak_schedule,null);
        m_peternak_schedule m = list.get(position);
        TextView txtKandang = (TextView) view.findViewById(R.id.txtKandang);
        TextView txtAyamMati = (TextView) view.findViewById(R.id.txtAyamMati);
        TextView txtAyamSakit = (TextView) view.findViewById(R.id.txtAyamSakit);

        txtKandang.setText("Kandang : "+m.getKandang());
        txtAyamMati.setText("Ayam Mati : "+m.getAyammati());
        txtAyamSakit.setText("Ayam Sakit : "+m.getAyamsakit());
        view.setTag(m.getId());
        return view;
    }
}
