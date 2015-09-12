package demo;


import javafx.scene.effect.Light;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.methods.HttpPut;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class NestController {
	@Autowired
	private MongoTemplate mongoTemplate;

	public NestController() {
	}

	public static final String BASE_URL = "https://developer-api.nest.com";
	public static final String PIN_CODE = "KN2JFH6B";
	public static final String COLLECTION_NAME = "nest_data";
	public static final String LIGHT_COLLECTION_NAME = "light_data";
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

    @RequestMapping(value="/fetchdata/thermostat/{voiceCommand}", method= RequestMethod.GET, produces = {"application/json"} )
    @ResponseStatus(HttpStatus.OK)
    public String thermostat_state(@PathVariable("voiceCommand") String voiceCommand) throws Exception
    {

		SmartDevice result=mongoTemplate.findOne(new Query(Criteria.where("serial_no").is("one")), SmartDevice.class, "smartdevices");
		List<String> downData=result.getDown();
		List<String> upData=result.getUp();
		System.out.println("serial no"+ result.getSerial_no()+ result.getDown().get(0));
		String temp="turn on the thermostat";
		int flag=tokenize_words(voiceCommand, downData, upData);
		if(flag==1){
			System.out.println("Okay!! Turning on the thermostat");
		}else if(flag==2){
			System.out.println("Okay!!Turning off the thermostat");

		}else{
			System.out.println("Sorry,Did not perform any operation");
		}
		//System.out.println("result="+result.getId());
        return "hello world";
    }

	public int tokenize_words(String temp,List<String>downData,List<String> upData){
		String[] words =temp.split(" ");
		String word="";
		int flag=0;
		for(int i=0;i<words.length;i++){
			word=words[i];
			flag=0;
			 if(word.compareToIgnoreCase("turn")==0){
				 if(words[i+1].compareToIgnoreCase("on")==0){
					 word=word.concat(" on");
					 System.out.println("concatenated word" + word);
					 i=i+1;
				 }
				 if(words[i+1].compareToIgnoreCase("off")==0){
					 word=word.concat(" off");
					 System.out.println("concatenated word" + word);
					 i=i+1;
				 }
			 }
			if(word.compareToIgnoreCase("switch")==0){
				if(words[i+1].compareToIgnoreCase("on")==0){
					word=word.concat(" on");
					System.out.println("concatenated word" + word);
					i=i+1;
				}
				if(words[i+1].compareToIgnoreCase("off")==0){
					word=word.concat(" off");
					System.out.println("concatenated word" + word);
					i=i+1;
				}
			}
			//Check if the word is there in array set down(turn off,light)
			for(int j=0;j<downData.size();j++){
				if(downData.get(j).compareToIgnoreCase(word)==0){
					System.out.println("Device switched on");
					flag=1;break;
				}
			}
			if(flag==0){
				for(int k=0;k<upData.size();k++){
					if(upData.get(k).compareToIgnoreCase(word)==0){
						System.out.print("Device switched off");
						flag=2;break;
					}
				}
			}
			if(flag==1||flag==2){
				break;
			}
		}
		return flag;
	}

	@RequestMapping(value="/fetchdata/light/voiceCommand", method= RequestMethod.GET, produces = {"application/json"} )
	@ResponseStatus(HttpStatus.OK)
	public String light_state(@PathVariable("voiceCommand") String voiceCommand) throws Exception
	{

		SmartDevice result=mongoTemplate.findOne(new Query(Criteria.where("serial_no").is("two")), SmartDevice.class, "smartdevices");
		List<String> offData=result.getDown();
		List<String> onData=result.getUp();
		System.out.println("serial no"+ result.getSerial_no()+ result.getDown().get(0));
		String temp="turn on light";
		int flag= tokenize_words(voiceCommand, onData, offData);

		if(flag==1){
			//call the turn on light api
			System.out.println("Okay!! Turning on the light");
		}else if(flag==2){
			//call the turn off light api
			System.out.println("Okay!!Turning off the light");

		}else{
			System.out.println("Sorry,Did not perform any operation");
		}
		//System.out.println("result="+result.getId());
		return "hello world";
	}


	@RequestMapping("/thermo/{thermoId}")
	public ThermoDO getThermostat(@PathVariable("thermoId") String thermoId)
			throws IOException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Config.class);
        ctx.refresh();
		if (!mongoTemplate.collectionExists(COLLECTION_NAME)) {
			mongoTemplate.createCollection(COLLECTION_NAME);
		}

        final String get_thermo_by_id = BASE_URL + "/devices/thermostats/"+ thermoId
                + "?auth=" + ACCESS_TOKEN;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<ThermoDO> response = restTemplate.exchange(
                get_thermo_by_id, HttpMethod.GET, entity, ThermoDO.class);
		mongoTemplate.insert(response, COLLECTION_NAME);
		return response.getBody();

	}

	@RequestMapping("/allThermo")
	public ResponseEntity<String> getAllThermostat() throws IOException {

        final String get_all_thermo_url = BASE_URL + "/?auth=" + ACCESS_TOKEN;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> response = restTemplate.exchange(get_all_thermo_url, HttpMethod.GET, entity, String.class);
		System.out.println(response);
		return response;

	}

	@RequestMapping("/lights/{lightId}")
	public ResponseEntity<String> getLight(@PathVariable("lightId") String lightId) throws IOException{
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(Config.class);
		ctx.refresh();
		if (!mongoTemplate.collectionExists(LIGHT_COLLECTION_NAME)) {
			mongoTemplate.createCollection(LIGHT_COLLECTION_NAME);
		}

		final String get_light_info_by_id = "http://localhost:8000/api/newdeveloper/lights/" + lightId;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> response =  restTemplate.exchange(get_light_info_by_id, HttpMethod.GET, entity, String.class);

		System.out.println(response);
		System.out.println(response);
		mongoTemplate.insert(response, LIGHT_COLLECTION_NAME);
		return response;
	}

	@RequestMapping("/allLights")
	public ResponseEntity<String> getAllLights() throws IOException{

		final String get_light_info = "http://localhost:80/api/newdeveloper/lights";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> response =  restTemplate.exchange(get_light_info, HttpMethod.GET, entity, String.class);
		System.out.println(response);
		return response;
	}
    @RequestMapping("/thermo/{thermoId}/changeTemperature/{updatedTemp}")
    public void changeTargetTemperature(@PathVariable("thermoId") String thermoId, @PathVariable("updatedTemp") String updatedTemp) throws IOException {

        final String updateTargetTemp = BASE_URL + "/devices/thermostats/"+ thermoId +
                "/target_temperature_f/?auth=" + ACCESS_TOKEN;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(updatedTemp);
        ResponseEntity<ThermoDO> response = restTemplate.exchange(
                updateTargetTemp, HttpMethod.PUT, entity, ThermoDO.class);
    }
}
