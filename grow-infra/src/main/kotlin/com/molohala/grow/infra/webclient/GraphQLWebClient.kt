package com.molohala.grow.infra.webclient

import com.molohala.grow.infra.api.github.GithubProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.client.HttpGraphQlClient
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class GraphQLWebClient(
    val githubProperties: GithubProperties
) {
    @Bean
    fun graphqlWebClient(): HttpGraphQlClient {
        return HttpGraphQlClient.create(
            WebClient.builder()
                .baseUrl("https://api.github.com/graphql")
                .defaultHeader("Authorization", "Bearer ${githubProperties.token}")
                .build()
        )
    }
}