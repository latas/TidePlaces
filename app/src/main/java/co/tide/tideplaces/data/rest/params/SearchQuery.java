package co.tide.tideplaces.data.rest.params;

public interface SearchQuery {
    String[] params();

    SearchQuery build();

}
