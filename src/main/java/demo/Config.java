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
        MongoClientURI uri = new MongoClientURI("mongodb://cmpe:295@ds041571.mongolab.com:41571/team295");
        MongoDbFactory mongoDbFactory =  new SimpleMongoDbFactory(new MongoClient(uri),"team295");
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }
}
