package com.example.demo.controllers;

import com.example.demo.models.SimpleModel;
import com.example.demo.models.SimpleModelBuilder;
import com.example.demo.repository.PostgresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private KafkaTemplate<String, SimpleModel> kafkaTemplate;
    private PostgresRepository postgresRepository;
    private Twitter twitter;

    @Autowired
    public KafkaController(KafkaTemplate<String, SimpleModel> kafkaTemplate, PostgresRepository postgresRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.postgresRepository = postgresRepository;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("EtUdxxLgPrsXhDrNoMBkqAkLA")
                .setOAuthConsumerSecret("eQxM5FnVqka19glJ0cpK5XbGBnxtU3ag2RpubXL7t4cmXha6Lr")
                .setOAuthAccessToken("1097218198502555648-awiMaUNMGokHizI2pEgA8Udrig38AN")
                .setOAuthAccessTokenSecret("zhNmHAYEKCuvmLj511dfo7vuZnwTw0BZM99iLsP7OEPsX");
        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
    }

    @PostMapping("/sendToMyTopic")
    public void sendToMyTopic(@RequestBody SimpleModel simpleModel) {
        kafkaTemplate.send("myTopic", simpleModel);
    }

    @PostMapping("/sendToPostgresTopic")
    public void sendToPostgresTopic(@RequestBody SimpleModel simpleModel) {
        kafkaTemplate.send("postgresTopic", simpleModel);
    }

    @KafkaListener(topics = "postgresTopic")
    public void getFromPostgresTopic(SimpleModel simpleModel) {
        System.out.println(simpleModel.toString());
        postgresRepository.save(simpleModel);
    }

    @GetMapping("/getAllPostgresTopic")
    public Iterable<SimpleModel> getAllPostgresTopic() {
        return postgresRepository.findAll();
    }

    @GetMapping("/deleteAllPostgres")
    public List<SimpleModel> deleteAllPostgres() {
        postgresRepository.deleteAll();
        List<SimpleModel> list = new ArrayList<>();
        postgresRepository.findAll().forEach(list::add);
        return list;
    }

    @PostMapping("/queryTwitter")
    public String queryTwitter(@RequestBody String queryCriteria) {
        try {
            Query query = new Query(queryCriteria);
            query.setCount(100);
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {

                SimpleModelBuilder modelBuilder = new SimpleModelBuilder();
                modelBuilder.setUsername((status.getUser().getScreenName()));
                modelBuilder.setText(status.getText());
                modelBuilder.setDate(status.getCreatedAt());
                modelBuilder.setFavoriteCount(status.getFavoriteCount());

                kafkaTemplate.send("postgresTopic", modelBuilder.build());
            }
        }
        catch(Exception e) {
            System.out.println(e);
            return "Failed to insert tweets containing " + queryCriteria + " into PostgreSQL";
        }
        return "Successfully inserted tweets containing " + queryCriteria + " into PostgreSQL";
    }
}
