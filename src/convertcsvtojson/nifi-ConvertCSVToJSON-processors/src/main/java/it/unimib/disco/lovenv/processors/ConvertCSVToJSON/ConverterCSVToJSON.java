/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.unimib.disco.lovenv.processors.ConvertCSVToJSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.behavior.SideEffectFree;
import org.apache.nifi.annotation.behavior.SupportsBatching;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.io.InputStreamCallback;
import org.apache.nifi.processor.io.OutputStreamCallback;
import org.apache.nifi.processor.util.StandardValidators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser.Feature;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Tags({ "csv", "json", "converter" })
@CapabilityDescription("A processor converter which allows to convert CSV files into JSON")
@SeeAlso({})
@ReadsAttributes({ @ReadsAttribute(attribute = "", description = "") })
@WritesAttributes({ @WritesAttribute(attribute = "", description = "") })
@SideEffectFree
@SupportsBatching
public class ConverterCSVToJSON extends AbstractProcessor {

	public static final PropertyDescriptor MY_PROPERTY = new PropertyDescriptor.Builder().name("MY_PROPERTY")
			.displayName("My property").description("Example Property").required(true)
			.addValidator(StandardValidators.NON_EMPTY_VALIDATOR).build();

	public static final Relationship SUCCESS = new Relationship.Builder().name("success")
			.description("Success relationship").build();

	public static final Relationship FAILURE = new Relationship.Builder().name("failure")
			.description("Failure relationship").build();

	private List<PropertyDescriptor> descriptors;

	private Set<Relationship> relationships;

	@Override
	protected void init(final ProcessorInitializationContext context) {
		final List<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>();
		descriptors.add(MY_PROPERTY);
		this.descriptors = Collections.unmodifiableList(descriptors);

		final Set<Relationship> relationships = new HashSet<Relationship>();
		relationships.add(SUCCESS);
		relationships.add(FAILURE);
		this.relationships = Collections.unmodifiableSet(relationships);
	}

	@Override
	public Set<Relationship> getRelationships() {
		return this.relationships;
	}

	@Override
	public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
		return descriptors;
	}

	@OnScheduled
	public void onScheduled(final ProcessContext context) {

	}

	@Override
	public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
		FlowFile flowFile = session.get();

		if (flowFile == null) {
			return;
		}
		final AtomicReference<String> value = new AtomicReference<>();
		
		session.read(flowFile, new InputStreamCallback() {
			@Override
			public void process(InputStream in) throws IOException {
				try {
			        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).setNullValue("").build();
			        CsvMapper csvMapper = new CsvMapper();
			        csvMapper.configure(Feature.TRIM_SPACES, true);
			        
			        List<Object> readAll = csvMapper.readerFor(AirQuality.class).with(csvSchema).readValues(in).readAll();
			        ObjectMapper mapper = new ObjectMapper();
			        mapper.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.IGNORE_UNKNOWN, true);

			        String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll);

					value.set(result);
				} catch (Exception ex) {
					ex.printStackTrace();
					getLogger().error(ex.getMessage());
				}
			}
		});

		flowFile = session.write(flowFile, new OutputStreamCallback() {

			@Override
			public void process(OutputStream out) throws IOException {
				try {
					out.write(value.get().getBytes());
				} catch (Exception ex) {
					ex.printStackTrace();
					getLogger().error(ex.getMessage());
				}
			}
		});

		session.transfer(flowFile, SUCCESS);
	}
}
