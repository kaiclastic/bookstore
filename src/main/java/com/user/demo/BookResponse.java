package com.user.demo;

import java.util.List;


public record BookResponse(List<Item> items) {

    public record Item(String id, VolumeInfo volumeInfo) {}

    public record VolumeInfo(String title, List<String> authors, String publisher, String publishedDate, ImageLinks imageLinks) {}

    public record ImageLinks(String smallThumbnail, String thumbnail) {}
}
