package com.lebapps.topgold.sections.functionality;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lebapps.topgold.R;
import com.lebapps.topgold.data.history.HistoryManager;
import com.lebapps.topgold.data.messages.MessageReceiver;
import com.lebapps.topgold.data.vehicle.Vehicle;
import com.lebapps.topgold.data.vehicle.VehiclesManager;
import com.lebapps.topgold.functionality.Functionality;
import com.lebapps.topgold.functionality.FunctionalityFactory;
import com.lebapps.topgold.sections.functionality.actions.PasswordFunctionalityActivity;
import com.lebapps.topgold.sections.functionality.actions.SpeedFunctionalityActivity;
import com.lebapps.topgold.sections.functionality.actions.TimezoneFunctionalityActivity;
import com.lebapps.topgold.sections.functionality.actions.VehicleFunctionalityActivity;

import java.util.ArrayList;

import rx.Subscriber;

/**
 *
 */
public class FunctionalityFragment extends Fragment {
    private RecyclerView rvContent;
    private MaterialSpinner spinner;
    private Vehicle selectedVehicle;
    private FunctinalityAdapter adapter;
    Subscriber<Boolean> subscriber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_functionality, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        configureSpinner(view);
        configureFunctionalitiesListing();
        selectedVehicle = getSelectedVehicle(0);
        subscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Boolean o) {
                Toast.makeText(getContext(), R.string.device_replied, Toast.LENGTH_LONG).show();
            }
        };
        MessageReceiver.subscribeToSPublish(subscriber);
        HistoryManager.getInstance().subscribeToSubject(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(Boolean aBoolean) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscriber != null) {
            subscriber.unsubscribe();
        }
    }

    private void initViews(View view) {
        rvContent = view.findViewById(R.id.rvContent);
    }

    private void configureSpinner(View view) {
        spinner = view.findViewById(R.id.spinner);
        setVehicles();
    }

    private void setVehicles() {
        final ArrayList<Vehicle> vehicles = VehiclesManager.getInstance().getVehicles();
        spinner.setItems(getVehiclesNames(vehicles));
        spinner.setOnItemSelectedListener((view1, position, id, item) -> handleVehicleSelected(position));
    }

    private void configureFunctionalitiesListing() {
        final ArrayList<Functionality> allFunctionalities = FunctionalityFactory.getAllFunctionalities(getContext());
        adapter = new FunctinalityAdapter(getContext(), allFunctionalities, new FunctinalityAdapter.OnFunctionalityListener() {
            @Override
            public void onClicked(Functionality functionality) {
                openVehicleFunctionality(functionality);
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvContent.setLayoutManager(layoutManager);
        rvContent.setAdapter(adapter);
    }

    private void openVehicleFunctionality(Functionality functionality) {
        // todo refactor
        Intent intent = null;
        switch (functionality.getFunctionalityCode()) {
            case "speed":
                intent = new Intent(getActivity(), SpeedFunctionalityActivity.class);
                break;
            case "password":
                intent = new Intent(getActivity(), PasswordFunctionalityActivity.class);
                break;
            case "timezone":
                intent = new Intent(getActivity(), TimezoneFunctionalityActivity.class);
                break;
            default:
                intent = new Intent(getActivity(), VehicleFunctionalityActivity.class);
                break;
        }
        intent.putExtra("functionality", functionality);
        intent.putExtra("vehicle", selectedVehicle);
        startActivity(intent);
    }

    private void handleVehicleSelected(int position) {
        selectedVehicle = getSelectedVehicle(position);
    }

    private Vehicle getSelectedVehicle(int position) {
        final ArrayList<Vehicle> vehicles = VehiclesManager.getInstance().getVehicles();
        if (vehicles.size() > position) {
            return vehicles.get(position);
        }
        return null;
    }

    private ArrayList<String> getVehiclesNames(ArrayList<Vehicle> vehicles) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            arrayList.add(vehicle.getName());
        }
        return arrayList;
    }

    public void refreshVehicles() {
        if (getView() != null) setVehicles();
    }
}
