package com.user.demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.List;

@Document
public record BookDoc(
        @Id String id,
        VolumeInfo volumeInfo
) {

    public record VolumeInfo(
            String title,
            List<String> authors,
            String publisher,
            String publishedDate,
            ImageLinks imageLinks
    ) {}

    public record ImageLinks(
            String smallThumbnail,
            String thumbnail
    ) {}
}
