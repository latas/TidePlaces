package co.tide.tideplaces.data.models;

import co.tide.tideplaces.data.models.error.Error;

public interface ResultListener<T> {

    void start();

    void success(T t);

    void failure(Error error);
}
