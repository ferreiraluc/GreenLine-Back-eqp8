package app.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/api/usuario")
@RestController
@CrossOrigin(origins = "*")
public class UsuarioController {

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Autenticador data){
		HttpHeaders headers = new HttpHeaders();
		RestTemplate rt = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("client_id",data.clientId());
		formData.add("username",data.username());
		formData.add("password",data.password());
		formData.add("grant_type",data.grantType());

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String,String>>(formData,headers);

		var result = rt.postForEntity("http://192.168.56.107:8080/realms/greenline-realm/protocol/openid-connect/token",entity, String.class);

		return result;
	}
    


}
