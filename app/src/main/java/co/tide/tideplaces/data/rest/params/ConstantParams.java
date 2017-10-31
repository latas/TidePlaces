package co.tide.tideplaces.data.rest.params;

import javax.inject.Inject;
import javax.inject.Named;

import co.tide.tideplaces.di.scopes.AppScope;

@AppScope
public class ConstantParams {

    final String apiKey;
    final String type;
    final long radius;

    @Inject
    public ConstantParams(@Named("apiKey") String apiKey, @Named("places_type") String type, @Named("radius") long radius) {
        this.apiKey = apiKey;
        this.type = type;
        this.radius = radius;
    }

    public String radiusParam() {
        return String.valueOf(radius);
    }

    public String typeParam() {
        return type;
    }

    public String apiKey() {
        return apiKey;
    }
}