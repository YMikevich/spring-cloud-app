package com.github.ymikevich.es.integration.configs;

import com.github.ymikevich.es.integration.configs.mappers.CustomEntityMapper;
import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
public class ElasticConfig {

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(final Client client) {
        return new ElasticsearchTemplate(client, new CustomEntityMapper());
    }
}
