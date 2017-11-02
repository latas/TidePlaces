package co.tide.tideplaces.presenters;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.tide.tideplaces.data.models.ListItem;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.ui.screens.ListScreen;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ListPresenter implements Observer<List<Place>> {
    final ListScreen listScreen;


    public ListPresenter(ListScreen listScreen) {
        this.listScreen = listScreen;
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(List<Place> places) {
        List<ListItem> listItems = new ArrayList<>();
        for (final Place place : places) {
            if (!place.isMyLocation()) {
                listItems.add(new ListItem(place.name(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listScreen.openGoogleMaps(place.location());
                    }
                }));
            }
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
