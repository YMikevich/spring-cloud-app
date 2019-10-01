package com.github.ymikevich.es.integration.configs;

import com.github.ymikevich.es.integration.configs.mappers.CustomEntityMapper;
import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.github.ymikevich.es.integration.repository")
public class ElasticConfig {

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(final Client client) {
        return new ElasticsearchTemplate(client, new CustomEntityMapper());
    }
}
