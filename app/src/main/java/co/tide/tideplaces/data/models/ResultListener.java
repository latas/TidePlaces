package co.tide.tideplaces.data.models;

public interface ResultListener<T> {

    void start();

    void success(T t);

    void failure();
}
