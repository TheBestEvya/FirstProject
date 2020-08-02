package com.example.demo.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.LoginFailedException;

@Scope("singleton")
@Service
public class LoginService {
	@Autowired
	private ApplicationContext ctx;

	LoginService(ApplicationContext ctx) {
	}

	public ClientService login(String email, String password, ClientType clientType) throws LoginFailedException {
		switch (clientType.ordinal()) {
		case 0:
			AdminService af = ctx.getBean(AdminService.class);
			if (af.login(email, password)) 
				return af;
			break;
		case 1:
			CompanyService cf = ctx.getBean(CompanyService.class);
			if (cf.login(email, password)) 			
				return cf;
			break;
		case 2:
			CustomerService cf2 = ctx.getBean(CustomerService.class);
			if (cf2.login(email, password)) 			
				return cf2;
			break;

		default:
			break;
		}
		throw new LoginFailedException();
	}

}
