package co.tide.tideplaces.data.models;

import co.tide.tideplaces.data.models.error.Error;

public class RxException extends Exception {
    final Error error;


    public RxException(Error error) {
        this.error = error;
    }
}
