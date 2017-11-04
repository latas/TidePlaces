package co.tide.tideplaces.presenters;

import android.net.Uri;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import co.tide.tideplaces.data.interactors.PlacesRepository;
import co.tide.tideplaces.data.models.ListItem;
import co.tide.tideplaces.data.models.Place;
import co.tide.tideplaces.ui.screens.ListScreen;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ListPresenter implements Observer<List<Place>> {
    final ListScreen listScreen;
    final CompositeDisposable disposables = new CompositeDisposable();
    final PlacesRepository placesRepository;

    @Inject
    public ListPresenter(ListScreen listScreen, PlacesRepository placesRepository) {
        this.listScreen = listScreen;
        this.placesRepository = placesRepository;
    }


    public void drain() {
        disposables.clear();
    }


    public void data() {
        placesRepository.data().subscribe(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposables.add(d);
    }

    @Override
    public void onNext(List<Place> places) {
        List<ListItem> listItems = new ArrayList<>();
        for (final Place place : places) {
            if (!place.isMyLocation()) {
                listItems.add(new ListItem(place.name(), place.distanceFromAnchor() + "m", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listScreen.openGoogleMaps(Uri.parse(String.format(Locale.ENGLISH, "geo:<%.7f>,<%.7f>?q=<%.7f>,<%.7f>(%s)", place.location().latitude, place.location().longitude, place.location().latitude, place.location().longitude, place.name())));
                    }
                }));
            }
        }
        listScreen.show(listItems);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
