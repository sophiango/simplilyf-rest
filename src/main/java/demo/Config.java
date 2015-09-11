package demo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Config {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception
    {
        MongoClientURI uri = new MongoClientURI("mongodb://cmpe:295@ds027483.mongolab.com:27483/cmpe295");
        MongoDbFactory mongoDbFactory =  new SimpleMongoDbFactory(new MongoClient(uri),"cmpe295");
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }
}
