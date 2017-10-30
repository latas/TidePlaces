package co.tide.tideplaces.data.models.error;

import co.tide.tideplaces.R;

public class NoClosePlacesError implements Error {
    @Override
    public int code() {
        return ErrorCodes.noClosePlacesError;
    }

    @Override
    public int message() {
        return R.string.no_close_places_error;
    }
}
