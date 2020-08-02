package com.example.demo.aspects;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.demo.exceptions.LoginRequiredException;
import com.example.demo.web.Session;

@Aspect
@Component
public class ControllersAspect {
	@Autowired
	private Map<String, Session> sessions;

	@Around("execution(* com.example.demo.web.controllers.*.*(..)) ")
	public Object checkTokens(ProceedingJoinPoint pjp) throws Throwable ,LoginRequiredException {
		Session session = sessions.get(pjp.getArgs()[0]);
		if (session != null && (System.currentTimeMillis() - session.getLastAction() < 1000 * 60 * 30)) {	
			session.setLastAction(System.currentTimeMillis());
			return pjp.proceed();
		}
		else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in");
	}
}

