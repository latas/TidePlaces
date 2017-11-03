package co.tide.tideplaces.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.tide.tideplaces.R;
import co.tide.tideplaces.data.events.PermissionsAcceptedEvent;
import co.tide.tideplaces.data.models.ListItem;
import co.tide.tideplaces.presenters.ListPresenter;
import co.tide.tideplaces.ui.adapters.ListAdapter;
import co.tide.tideplaces.ui.screens.ListScreen;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


public class PlacesListFragment extends Fragment implements ListScreen {

    @Inject
    Observable<PermissionsAcceptedEvent> permissionsObservable;

    ListAdapter listAdapter;

    @BindView(R.id.list)
    RecyclerView list;

    @Inject
    ListPresenter presenter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listAdapter = new ListAdapter(getActivity());
        list.setAdapter(listAdapter);
        presenter.data();
        permissionsObservable.subscribe(new Consumer<PermissionsAcceptedEvent>() {
            @Override
            public void accept(PermissionsAcceptedEvent permissionsAcceptedEvent) throws Exception {
                presenter.data();
            }
        });
        return view;
    }


    @Override
    public void show(List<ListItem> listItems) {
        listAdapter.addItems(listItems);
    }

    @Override
    public void openGoogleMaps(Uri uri) {

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDestroy() {
        presenter.drain();
        super.onDestroy();
    }
}
