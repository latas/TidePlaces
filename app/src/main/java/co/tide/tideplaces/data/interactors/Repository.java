package co.tide.tideplaces.data.interactors;

import co.tide.tideplaces.data.models.ResultListener;

public interface Repository<T> {
    void data(ResultListener<T> listener);

}
