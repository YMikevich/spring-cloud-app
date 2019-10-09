package com.github.ymikevich.es.integration.repository;

import com.github.ymikevich.es.integration.api.model.Tweet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends ElasticsearchCrudRepository<Tweet, Long> {
    List<Tweet> findAllByTextLike(String text, Pageable pageable);
}
