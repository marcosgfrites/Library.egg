package com.gyl.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.persistence.EntityListeners;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class LibraryGyLApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryGyLApplication.class, args);
	}

}
