package co.tide.tideplaces.data.models;

import co.tide.tideplaces.data.models.error.Error;
import co.tide.tideplaces.data.models.error.ErrorCodes;

public class RxException extends Exception {
    final Error error;


    public RxException(Error error) {
        this.error = error;
    }

    public boolean isUnAuthorizedPermissionError() {
        return this.error.code() == ErrorCodes.unAuthorizedLocationError;
    }

    public int message() {
        return error.message();
    }
}
