package com.lebapps.topgold.sections.functionality;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.lebapps.topgold.R;
import com.lebapps.topgold.data.history.HistoryManager;
import com.lebapps.topgold.functionality.Functionality;

import java.util.ArrayList;

/**
 *
 */
public class FunctinalityAdapter extends RecyclerView.Adapter<FunctinalityAdapter.FunctionalityViewHolder> {
    private Context mContext;
    private ArrayList<Functionality> functionalities;
    private OnFunctionalityListener listener;

    public FunctinalityAdapter(Context mContext,ArrayList<Functionality> functionalities, OnFunctionalityListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        this.functionalities = functionalities;
    }

    @Override
    public FunctionalityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_functionality, parent, false);
        // create and return view holder
        return new FunctionalityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FunctionalityViewHolder holder, int position) {
        final Functionality functionality = functionalities.get(position);
        holder.bind(functionality);
    }

    @Override
    public int getItemCount() {
        return (functionalities != null ? functionalities.size() : 0);
    }

    public class FunctionalityViewHolder extends RecyclerView.ViewHolder{

        TextView tvFunctionality;
        ImageView ivIcon;
        //View vSet;

        public FunctionalityViewHolder(View itemView) {
            super(itemView);
            tvFunctionality = itemView.findViewById(R.id.tvFunctionality);
            ivIcon = itemView.findViewById(R.id.ivIcon);
           // vSet = itemView.findViewById(R.id.vSet);
        }

        public void bind(Functionality functionality) {
            tvFunctionality.setText(functionality.getFunctionalityResource());
            ivIcon.setBackgroundResource(functionality.getImageResource());

            final String code = functionality.getFunctionalityCode();
            final HistoryManager instance = HistoryManager.getInstance();

//            if (code.equals("supplyoil")) {
//                if (instance.getOilSet() && instance.getOil()) {
//                    vSet.setVisibility(View.VISIBLE);
//                } else {
//                    vSet.setVisibility(View.INVISIBLE);
//                }
//            } else if(code.equals("stopoil")) {
//                if (instance.getOilSet() && !instance.getOil()) {
//                    vSet.setVisibility(View.VISIBLE);
//                } else {
//                    vSet.setVisibility(View.INVISIBLE);
//                }
//            } else if (code.equals("accon")) {
//                if (instance.getAccSet() && instance.getAcc()) {
//                    vSet.setVisibility(View.VISIBLE);
//                } else {
//                    vSet.setVisibility(View.INVISIBLE);
//                }
//            } else if(code.equals("accoff")) {
//                if (instance.getAccSet() && !instance.getAcc()) {
//                    vSet.setVisibility(View.VISIBLE);
//                } else {
//                    vSet.setVisibility(View.INVISIBLE);
//                }
//            } else if(code.equals("supplyelec")){
//                if (instance.getElecSet() && instance.getElec()) {
//                    vSet.setVisibility(View.VISIBLE);
//                } else {
//                    vSet.setVisibility(View.INVISIBLE);
//                }
//            } else if (code.equals("stopelec")) {
//                if (instance.getElecSet() && !instance.getElec()) {
//                    vSet.setVisibility(View.VISIBLE);
//                } else {
//                    vSet.setVisibility(View.INVISIBLE);
//                }
//            } else if(code.equals("speed")) {
//                if (instance.getSpeedSet() && instance.getSpeed()) {
//                    vSet.setVisibility(View.VISIBLE);
//                } else {
//                    vSet.setVisibility(View.INVISIBLE);
//                }
//            } else if (code.equals("nospeed")){
//                if (instance.getSpeedSet() && !instance.getSpeed()) {
//                    vSet.setVisibility(View.VISIBLE);
//                } else {
//                    vSet.setVisibility(View.INVISIBLE);
//                }
//            } else {
//                vSet.setVisibility(View.INVISIBLE);
//            }
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onClicked(functionality);
                }
            });
        }
    }

    public interface OnFunctionalityListener {
        void onClicked(Functionality functionality);
    }
}
