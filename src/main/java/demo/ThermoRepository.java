package demo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import demo.ThermoDO;

@Component
public interface ThermoRepository extends MongoRepository<ThermoDO, String> {
	@Query("{ 'id' : ?0 }")
	ThermoDO getThermoById(String id);
}
