package croitoru.geojson;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface GeoJsonService {

    @GET("/earthquakes/feed/v1.0/summary/all_month.geojson")
    Single<GeoJsonFeed> getSignificantEarthquakes();

}
