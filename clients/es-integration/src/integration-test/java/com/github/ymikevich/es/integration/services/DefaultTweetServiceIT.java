package com.github.ymikevich.es.integration.services;

import com.github.ymikevich.es.integration.EsIntegrationApplicationContextIT;
import com.github.ymikevich.es.integration.api.model.Tweet;
import com.github.ymikevich.es.integration.api.model.TwitterUser;
import com.github.ymikevich.es.integration.controllers.TweetController;
import com.github.ymikevich.es.integration.repository.TweetRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsIntegrationApplicationContextIT.class)
public class DefaultTweetServiceIT {

    @Autowired
    WebApplicationContext context;

    @Autowired
    TweetRepository repository;

    @Autowired
    TweetService service;

    private MockMvc mockMvc;

    //language=JSON
    private static final String JSON_STATISTICS_REQUEST = "{\n"
            + "\t\"username\": \"humortranslate\",\n"
            + "\t\"sinceDays\": \"3\"\n"
            + "}";

    //language=JSON
    private static final String JSON_SEARCH_REQUEST = "{\n"
            + "  \"searchString\": \"Hey\",\n"
            + "  \"pagination\": {\n"
            + "  \t\"page\": 0,\n"
            + "  \t\"count\": 10\n"
            + "  },\n"
            + "  \"sorting\":{\n"
            + "    \"fieldName\": \"retweetCount\",\n"
            + "    \"direction\": \"ASC\"\n"
            + "  }\n"
            + "}";

    //language=JSON
    private static final String EXPECTED_JSON_SEARCH_RESPONSE = "{\n"
            + "  \"responseEntities\": [\n"
            + "    {\n"
            + "      \"tweetId\": 265473423,\n"
            + "      \"text\": \"Hello Hey\",\n"
            + "      \"source\": \"http://somesource.com\",\n"
            + "      \"favoriteCount\": 100,\n"
            + "      \"retweetCount\": 150,\n"
            + "      \"user\": {\n"
            + "        \"id\": \"10\",\n"
            + "        \"email\": \"bred@mail.com\",\n"
            + "        \"name\": \"humortranslate\"\n"
            + "      }\n"
            + "    },\n"
            + "    {\n"
            + "      \"tweetId\": 125324235,\n"
            + "      \"text\": \"Hey\",\n"
            + "      \"source\": \"http://somesource.com\",\n"
            + "      \"favoriteCount\": 250,\n"
            + "      \"retweetCount\": 300,\n"
            + "      \"user\": {\n"
            + "        \"id\": \"10\",\n"
            + "        \"email\": \"bred@mail.com\",\n"
            + "        \"name\": \"humortranslate\"\n"
            + "      }\n"
            + "    }\n"
            + "  ],\n"
            + "  \"pagination\": {\n"
            + "    \"page\": 0,\n"
            + "    \"count\": 10\n"
            + "  }\n"
            + "}";

    //language=JSON
    private static final String JSON_SEARCH_REQUEST_WITH_PAGINATION = "{\n"
            + "  \"searchString\": \"Hey\",\n"
            + "  \"pagination\": {\n"
            + "  \t\"page\": 0,\n"
            + "  \t\"count\": 1\n"
            + "  },\n"
            + "  \"sorting\":{\n"
            + "    \"fieldName\": \"retweetCount\",\n"
            + "    \"direction\": \"ASC\"\n"
            + "  }\n"
            + "}";

    //language=JSON
    private static final String EXPECTED_JSON_SEARCH_RESPONSE_WITH_PAGINATION = "{\n" +
            "  \"responseEntities\": [\n"
            + "    {\n"
            + "      \"tweetId\": 265473423,\n"
            + "      \"text\": \"Hello Hey\",\n"
            + "      \"source\": \"http://somesource.com\",\n"
            + "      \"favoriteCount\": 100,\n"
            + "      \"retweetCount\": 150,\n"
            + "      \"user\": {\n"
            + "        \"id\": \"10\",\n"
            + "        \"email\": \"bred@mail.com\",\n"
            + "        \"name\": \"humortranslate\"\n"
            + "      }\n"
            + "    }\n"
            + "  ],\n"
            + "  \"pagination\": {\n"
            + "    \"page\": 0,\n"
            + "    \"count\": 1\n"
            + "  }\n"
            + "}";

    private static final String EXPECTED_JSON_STATISTICS_RESPONSE = "{\n"
            + "  \"averageLikeValue\": 283.3333333333333,\n"
            + "  \"mostRetweetedUserTweet\": {\n"
            + "    \"retweetValue\": 500,\n"
            + "    \"tweetId\": \"375685667\",\n"
            + "    \"source\": \"http://somesource.com\"\n"
            + "  }\n"
            + "}";


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void statisticsTest() throws Exception {
        var user = new TwitterUser();
        user.setId("10");
        user.setEmail("bred@mail.com");
        user.setName("humortranslate");
        var tweet1 = generateTweet(user, 125324235L, 250, 300, "Hey");
        var tweet2 = generateTweet(user, 265473423L, 100, 150, "Hello");
        var tweet3 = generateTweet(user, 375685667L, 500, 1000, "Bye");
        var tweets = new ArrayList<Tweet>();
        tweets.add(tweet1);
        tweets.add(tweet2);
        tweets.add(tweet3);
        service.persistTweets(tweets);

        var result = mockMvc.perform(post("/tweets/statistics").contentType(MediaType.APPLICATION_JSON)
                .content(JSON_STATISTICS_REQUEST))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        JSONAssert.assertEquals(EXPECTED_JSON_STATISTICS_RESPONSE, result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    public void searchTest() throws Exception {
        var user = new TwitterUser();
        user.setId("10");
        user.setEmail("bred@mail.com");
        user.setName("humortranslate");
        var tweet1 = generateTweet(user, 125324235L, 250, 300, "Hey");
        var tweet2 = generateTweet(user, 265473423L, 100, 150, "Hello Hey");
        var tweet3 = generateTweet(user, 375685667L, 500, 1000, "Bye");
        var tweets = new ArrayList<Tweet>();
        tweets.add(tweet1);
        tweets.add(tweet2);
        tweets.add(tweet3);
        service.persistTweets(tweets);

        var result1 = mockMvc.perform(post("/tweets/search").contentType(MediaType.APPLICATION_JSON)
                .content(JSON_SEARCH_REQUEST))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        var result2 = mockMvc.perform(post("/tweets/search").contentType(MediaType.APPLICATION_JSON)
                .content(JSON_SEARCH_REQUEST_WITH_PAGINATION))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        JSONAssert.assertEquals(EXPECTED_JSON_SEARCH_RESPONSE, result1.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
        JSONAssert.assertEquals(EXPECTED_JSON_SEARCH_RESPONSE_WITH_PAGINATION, result2.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
    }

    private Tweet generateTweet(final TwitterUser user, final Long id, final int favoriteCount,
                                final int retweetCount, final String text) {
        var tweet = new Tweet();
        tweet.setTweetId(id);
        tweet.setCreatedAt(LocalDateTime.now().minusDays(1));
        tweet.setFavoriteCount(favoriteCount);
        tweet.setRetweetCount(retweetCount);
        tweet.setSource("http://somesource.com");
        tweet.setText(text);
        tweet.setUser(user);
        return tweet;
    }
}
