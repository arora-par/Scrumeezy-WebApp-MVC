package com.scrumeezy.app.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.scrumeezy.app.pojo.Sprint;

public class TestMain {

	public static void main(String[] args) throws EmailException {
		
		List<Sprint> sprintList = new ArrayList<Sprint>();
		Sprint sp1 = new Sprint();
		sp1.setEndDate(DateUtils.addDays(new Date(),3));
		sprintList.add(sp1);
		
		sp1 = new Sprint();
		sp1.setEndDate(DateUtils.addDays(new Date(),4));
		sprintList.add(sp1);
		
		sp1 = new Sprint();
		sp1.setEndDate(DateUtils.addDays(new Date(),5));
		sprintList.add(sp1);
		
		sp1 = new Sprint();
		sp1.setEndDate(DateUtils.addDays(new Date(),2));
		sprintList.add(sp1);
		
		Collections.sort(sprintList);
		
		System.out.println(sprintList.get(0).getEndDate());
		System.out.println(sprintList.get(3).getEndDate());
		
		
		Date currDt = DateUtils.addDays(new Date(),5);
		Date dueDate = new Date();
		System.out.println(currDt.after(dueDate));
		
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("testaro9999@gmail.com", "AkkadBakkad"));
		email.setSSL(true);
		email.setFrom("testaro9999@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-)");
		email.addTo("paritosh.arora1988@gmail.com");
		email.send();
	}
}
