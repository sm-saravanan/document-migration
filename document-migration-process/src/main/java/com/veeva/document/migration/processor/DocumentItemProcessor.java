package com.veeva.document.migration.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.veeva.document.migration.api.client.DocumentClient;
import com.veeva.document.migration.model.WonderDrugDocument;
/**
 * Processor for invoking Veeva Document API for creating document and returns the status
 * 
 * @author Saran
 *
 */
public class DocumentItemProcessor implements ItemProcessor<WonderDrugDocument, WonderDrugDocument> {

	@Autowired
		DocumentClient client;
	  private static final Logger log = LoggerFactory.getLogger(DocumentItemProcessor.class);

	  @Override
	  public WonderDrugDocument process(final WonderDrugDocument document) throws Exception {
		  // Call Veeva Import document REST API
		  String response = client.upload(document);
		  System.out.println(response);
		  // Retrieve the status from the response and update the status in the database for the tracking & retry perspective
		  
		  return document;
	  }
	
}
