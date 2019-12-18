package e2eTests;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetArtistByName {

    private int successStatusCode = 200;
    private int errorStatusCode = 400;

    private String ARTIST_PARAM = "artist";
    private String API_KEY_PARAM = "api_key";
    private String FORMAT_PARAM = "format";

    private String apiKeyValue = "b9304bb9f3e9c5397188fbb0381ade7d";
    private String json = "json";

    private String artistEndPointRequestUrl = "http://ws.audioscrobbler.com/2.0/?method=artist.search";

    @Test
    public void testGetArtistByNameReturnsErrorWhenQueryParamsAreNotSpecified() {
        when().get(artistEndPointRequestUrl)
                .then()
                .statusCode(errorStatusCode);

    }

    @Test
    public void testGetArtistByNameReturnsExpectedData() {

        String artistSearchName = "Madona";

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .queryParam(API_KEY_PARAM, apiKeyValue)
                .queryParam(FORMAT_PARAM, json)
                .queryParam(ARTIST_PARAM,artistSearchName)
                .when()
                .get(artistEndPointRequestUrl)
                .then()
                .statusCode(successStatusCode)
                .body("results.@attr.for", equalTo(artistSearchName))
                .body("results.artistmatches.artist.size()", is(30));
//        Response response = get(validRequestUrl);
//        response.then().assertThat().body("results.@attr.for", equalTo("Madona"));

    }
}
