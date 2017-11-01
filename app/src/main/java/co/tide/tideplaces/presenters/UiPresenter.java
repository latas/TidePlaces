package co.tide.tideplaces.presenters;

import java.util.List;

import co.tide.tideplaces.data.models.Place;

public interface UiPresenter {

    void presentDataToUi(List<Place> places);
}
