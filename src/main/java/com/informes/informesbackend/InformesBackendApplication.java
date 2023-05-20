package com.informes.informesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootApplication
public class InformesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InformesBackendApplication.class, args);

		DateFormat formateador= new SimpleDateFormat("dd/M/yy");
		Calendar rightNow =  Calendar.getInstance();

		System.out.println(formateador.format(rightNow.getTime()));

	}

}
