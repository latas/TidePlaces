package co.tide.tideplaces.data.interactors;


import io.reactivex.Observable;

public interface Repository<T> {
    Observable<T> data();

}
