package com.veeva.document.migration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.veeva.document.migration.api.client.AuthClient;

/**
 * Startup class for booting up Batch application
 * 
 * @author Saran
 *
 */
@SpringBootApplication
public class DocumentMigrationProcessApplication {
	
	public static void main(String[] args) throws Exception {
	    SpringApplication.run(DocumentMigrationProcessApplication.class, args);
	    
	}

}
