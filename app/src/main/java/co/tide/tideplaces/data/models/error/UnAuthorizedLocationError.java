package co.tide.tideplaces.data.models.error;


import co.tide.tideplaces.R;

public class UnAuthorizedLocationError implements Error {
    @Override
    public int code() {
        return ErrorCodes.unAuthorizedLocationError;
    }

    @Override
    public int message() {
        return R.string.empty;
    }
}
