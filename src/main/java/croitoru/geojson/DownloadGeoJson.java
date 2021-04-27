package croitoru.geojson;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class DownloadGeoJson {
    public static void main(String[] args) throws IOException {
//        URL url = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson");
//        URLConnection connection = url.openConnection();
//        InputStream inputStream = connection.getInputStream();
//        Gson gson = new Gson();
//
//        InputStreamReader reader = new InputStreamReader(inputStream);
//        GeoJsonFeed feed = gson.fromJson(reader, GeoJsonFeed.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GeoJsonService service = retrofit.create(GeoJsonService.class);

        Single<GeoJsonFeed> single = service.getSignificantEarthquakes();

        //do not use blocking get
        GeoJsonFeed feed = single.blockingGet();

        Feature largest = feed.features.get(0);
        for (Feature feauture : feed.features){
            if (feauture.properties.mag > largest.properties.mag){
                largest = feauture;
            }
        }

        System.out.printf("%s %f %d",
                largest.properties.place,
                largest.properties.mag,
                largest.properties.time);

        System.exit(0);
    }
}
