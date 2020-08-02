package com.example.demo.web.login;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.LoginFailedException;
import com.example.demo.web.Session;
import com.example.demo.web.services.ClientService;
import com.example.demo.web.services.ClientType;
import com.example.demo.web.services.LoginService;

@RestController
@RequestMapping("logginer")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
	@Autowired
	private Map<String, Session> sessions;
	@Autowired
	private LoginService LM;

	

	
	@PostMapping("login/{email}/{password}/{clientType}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password,
			@PathVariable String clientType) {
		try {
			ClientService cf = LM.login(email, password, ClientType.values()[Integer.parseInt(clientType)]);
			String token = UUID.randomUUID().toString();
			sessions.put(token, new Session(cf, System.currentTimeMillis()));
			return ResponseEntity.ok(token);
		} catch (LoginFailedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	@PostMapping("logout/{token}")
	public ResponseEntity<String> logout(@PathVariable String token) {
		sessions.remove(token);
		return ResponseEntity.ok("logged out");
	}
}
