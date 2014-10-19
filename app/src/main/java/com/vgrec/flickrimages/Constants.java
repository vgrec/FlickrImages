package com.vgrec.flickrimages;

public class Constants {
    private static final String BASE_URL = "https://api.flickr.com/services/rest/";
    private static final String API_KEY = "8c0a03e678f636644357f9ac9cf7e49f";
    public static final String PHOTO_SEARCH_URL = BASE_URL + "?method=flickr.photos.search&api_key=" + API_KEY + "&format=json&nojsoncallback=1&tags=%s";

    // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_{size}.jpg
    public static final String IMAGE_URL = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";

    public static final String TAG = "FlickrImages";


}
