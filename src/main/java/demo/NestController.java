package demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class NestController {
	@Autowired
	ThermoRepository thermoRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	public NestController() {
	}

	public static final String COLLECTION_NAME = "nest_data";
    public static final String ACCESS_TOKEN = "c.CkIEtPt2WhY7F5FAOC16CesXtDIGkhBYJUdbJo1IYRUptBuSOphqBV4Y3TCe0b7Q2nZhLcetFXzul6dnCCoQn1AgaQEnZZx7hWRMiGHkoI2ICByBKRtdM9FfHfpjcmyKlVrj63QGkYq66gLZ";

	// @RequestMapping("/thermo/{thermoId}")
	// public @ResponseBody ThermoDO findThermoById(
	// @PathVariable("thermoId") String thermoId) {
	// return thermoRepository.findById(thermoId);
	// }

//	public static void main(String[] args) {

//
//		ThermoRepository employeeRepository = ctx
//				.getBean(ThermoRepository.class);
//		employeeRepository.getThermoById("TB36giw8Mpqza4S-9dphVWRJTWVz7JnV");
//	}

    @RequestMapping(value="/greeting", method= RequestMethod.GET, produces = {"application/json"} )
    @ResponseStatus(HttpStatus.OK)
    public String test() throws Exception
    {
        return "hello world";
    }

	@RequestMapping("/thermo/{thermoId}")
	public ResponseEntity<String> getThermostat(@PathVariable("thermoId") String thermoId)
			throws IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
		if (!mongoTemplate.collectionExists(COLLECTION_NAME)) {
			mongoTemplate.createCollection(COLLECTION_NAME);
		}

		final String get_thermo_by_id = "https://developer-api.nest.com/devices/thermostats/"+ thermoId
				+ "?auth=" + ACCESS_TOKEN;
		;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);
		ResponseEntity<String> response = restTemplate.exchange(
                get_thermo_by_id, HttpMethod.GET, entity, String.class);
		mongoTemplate.insert(response, COLLECTION_NAME);
		return response;

	}

	@RequestMapping("/allThermo")
	public ResponseEntity<String> getAllThermostat() throws IOException {

		final String get_all_thermo_url = "https://developer-api.nest.com/?auth=" + ACCESS_TOKEN;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters",
				headers);
		ResponseEntity<String> response = restTemplate.exchange(
                get_all_thermo_url, HttpMethod.GET, entity, String.class);
		System.out.println(response);
		return response;

	}

}
