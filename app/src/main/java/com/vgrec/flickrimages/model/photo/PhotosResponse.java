package com.vgrec.flickrimages.model.photo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotosResponse {
    @JsonProperty("photos")
    private Photos photos;

    public Photos getPhotosResponse() {
        return photos;
    }
}
