package com.user.demo.repository;

import com.user.demo.domain.BookDoc;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends CouchbaseRepository<BookDoc, String> {
}
