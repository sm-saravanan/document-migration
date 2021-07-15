package com.veeva.document.migration.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.veeva.document.migration.model.WonderDrugDocument;
import com.veeva.document.migration.processor.DocumentItemProcessor;

@Configuration
@EnableBatchProcessing

/**
 * Configuration class for Spring Batch that defines Reader, Writer, Processor and Listener
 * 
 * @author Saran
 *
 */
public class BatchConfiguration extends DefaultBatchConfigurer {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public FlatFileItemReader<WonderDrugDocument> reader() {
		return new FlatFileItemReaderBuilder<WonderDrugDocument>().name("documentItemReader")
				.resource(new ClassPathResource("document-metadata.csv")).delimited()
				.names(new String[] { "documentID", "documentName", "filePath", "type", "subtype", "approvedDate" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<WonderDrugDocument>() {
					{
						setTargetType(WonderDrugDocument.class);
					}
				}).build();
	}

	@Bean
	public DocumentItemProcessor processor() {
		return new DocumentItemProcessor();
	}
	
	@Bean
	public FlatFileItemWriter<WonderDrugDocument> itemWriter() {
		return  new FlatFileItemWriterBuilder<WonderDrugDocument>()
	           			.name("itemWriter")
	           			.resource(new FileSystemResource("target/test-outputs/output.txt"))
	           			.lineAggregator(new PassThroughLineAggregator<>())
	           			.build();
	}
//	@Bean
//	public JdbcBatchItemWriter<WonderDrugDocument> writer(DataSource dataSource) {
//		return new JdbcBatchItemWriterBuilder<WonderDrugDocument>()
//				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//				.sql("INSERT INTO DOCUMENTS (documentID, documentName, filePath) VALUES (:documentID, :documentName, :filePath)")
//				.dataSource(dataSource).build();
//	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importDocumentJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1).end().build();
	}

	@Bean
	public Step step1(FlatFileItemWriter<WonderDrugDocument> writer) {
		return stepBuilderFactory.get("step1").<WonderDrugDocument, WonderDrugDocument>chunk(10).reader(reader())
				.processor(processor()).writer(writer).build();
	}
	

    @Override
    public void setDataSource(DataSource dataSource) {
    	
    }
    
    

}
