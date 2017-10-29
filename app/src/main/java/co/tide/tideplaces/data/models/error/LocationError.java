package co.tide.tideplaces.data.models.error;


import co.tide.tideplaces.R;

public class LocationError implements Error {
    @Override
    public int code() {
        return ErrorCodes.locationError;
    }

    @Override
    public int message() {
        return R.string.location_cannot_retrieved_message;
    }
}
