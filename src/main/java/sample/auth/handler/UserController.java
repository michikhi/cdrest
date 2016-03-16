package sample.auth.handler;

import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;

@EnableResourceServer
@RestController
public class UserController {
	
	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}
	
}
