package com.lebapps.topgold.sections.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lebapps.topgold.R;
import com.lebapps.topgold.data.history.ActionHistory;

import java.util.ArrayList;

/**
 *
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context mContext;
    private ArrayList<ActionHistory> functionalities;

    public HistoryAdapter(Context mContext, ArrayList<ActionHistory> functionalities) {
        this.mContext = mContext;
        this.functionalities = functionalities;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false);
        // create and return view holder
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final ActionHistory functionality = functionalities.get(position);
        holder.bind(functionality);
    }

    @Override
    public int getItemCount() {
        return (functionalities != null ? functionalities.size() : 0);
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView tvDate, tvVehicleName, tvAction;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvVehicleName = itemView.findViewById(R.id.tvVehicleName);
            tvAction = itemView.findViewById(R.id.tvAction);
        }

        public void bind(ActionHistory actionHistory) {
            tvDate.setText(actionHistory.getDate());
            tvVehicleName.setText(actionHistory.getCarName());
            tvAction.setText(actionHistory.getFunctionality());
        }
    }

}
