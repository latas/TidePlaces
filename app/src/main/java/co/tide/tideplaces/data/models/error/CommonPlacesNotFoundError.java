package co.tide.tideplaces.data.models.error;

import co.tide.tideplaces.R;

public class CommonPlacesNotFoundError implements Error {
    @Override
    public int code() {
        return ErrorCodes.coomonPlacesError;
    }

    @Override
    public int message() {
        return R.string.general_error_retrieving_places;
    }
}
