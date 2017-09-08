package com.simercom.motohedge.modules.utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simercom.motohedge.R;
import com.simercom.motohedge.modules.utils.models.Service;

import java.util.ArrayList;

/**
 * Created by wmora on 1/08/17.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private ArrayList<Service> mServiceList;

    public HistoryListAdapter(ArrayList<Service> serviceList){
        mServiceList = serviceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Service service = mServiceList.get(position);
        holder.bindView(service);
    }

    @Override
    public int getItemCount() {
        return mServiceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvConsecutive, tvService, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvConsecutive = (TextView) itemView.findViewById(R.id.tv_consecutive);
            tvService = (TextView) itemView.findViewById(R.id.tv_service);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }

        public void bindView(Service service){
            tvConsecutive.setText(service.getConsecutive());
            tvService.setText(Constants.getKindOfService(Integer.valueOf(service.getServiceType())));
            tvDate.setText(Util.formatDateForLocal(service.getDate()));
        }

    }

}
