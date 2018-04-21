package com.lebapps.topgold.sections.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lebapps.topgold.R;
import com.lebapps.topgold.data.history.ActionHistory;
import com.lebapps.topgold.data.history.HistoryManager;

import java.util.ArrayList;

/**
 *
 */

public class HistoryFragment extends Fragment {

    private RecyclerView rvContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvContent = view.findViewById(R.id.rvContent);
        final ArrayList<ActionHistory> history = HistoryManager.getInstance().getHistory();
        HistoryAdapter adapter = new HistoryAdapter(getContext(), history);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvContent.setAdapter(adapter);
        rvContent.setLayoutManager(layoutManager);
    }
}
