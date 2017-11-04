package co.tide.tideplaces.data.models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortedPlaces {


     List<Place> places;


    public SortedPlaces(List<Place> places) {
        this.places = places;

    }

    public List<Place> sort() {
        Collections.sort(places, new DistanceComparator());
        return places;
    }


    class DistanceComparator implements Comparator<Place> {
        public int compare(Place c1, Place c2) {
            return c1.distanceFromAnchor().compareTo(c2.distanceFromAnchor());
        }
    }

}
