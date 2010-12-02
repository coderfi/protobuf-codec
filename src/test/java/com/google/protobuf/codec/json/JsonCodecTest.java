package com.google.protobuf.codec.json;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.junit.Before;
import org.junit.Test;

import com.google.protobuf.codec.Codec;
import com.google.protobuf.codec.Codec.Feature;
import com.google.protobuf.codec.json.TypesProtoBuf.Types;


public class JsonCodecTest {

	private Types types;
	private String typesJson;
	
	@Before
	public void setupTypes() throws IOException {
		types=Types.newBuilder()
				.setIdbool(true)
				.setIddouble(Double.MAX_VALUE)
				.setIdfixed32(Integer.MIN_VALUE)
				.setIdfixed64(Long.MIN_VALUE)
				.setIdfloat(Float.MAX_VALUE)
				.setIdint32(1)
				.setIdint64(5000000000000000000l)
				.setIdsfixed32(-56)
				.setIdsfixed64(-561234561435l)
				.setIdsint32(-100)
				.setIdsint64(Long.MAX_VALUE)
				.setIdstring("Hello World")
				.setIduint32(100)
				.setIduint64(100l).build();
		
		JsonFactory factory=new JsonFactory();
		
		StringWriter writer=new StringWriter();
		JsonGenerator generator=factory.createJsonGenerator(writer);
		generator.writeStartObject();


		generator.writeStringField("idstring", types.getIdstring());
		generator.writeNumberField("idint32", types.getIdint32());
		generator.writeNumberField("iddouble", types.getIddouble());
		generator.writeNumberField("idfloat", types.getIdfloat());
		generator.writeNumberField("idint64", types.getIdint64());
		generator.writeNumberField("iduint32", types.getIduint32());
		generator.writeNumberField("iduint64", types.getIduint64());
		generator.writeNumberField("idsint32", types.getIdsint32());
		generator.writeNumberField("idsint64", types.getIdsint64());
		generator.writeNumberField("idfixed32", types.getIdfixed32());
		generator.writeNumberField("idfixed64", types.getIdfixed64());
		generator.writeNumberField("idsfixed32", types.getIdsfixed32());
		generator.writeNumberField("idsfixed64", types.getIdsfixed64());
		generator.writeBooleanField("idbool", types.getIdbool());
		generator.writeEndObject();
		generator.close();
		typesJson=writer.toString();
	}

	@Test
	public void ensureTypes() throws IOException{
		Codec codec=new JsonCodec();
		codec.setFeature(Feature.CLOSE_STREAM, true);
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		codec.fromMessage(types, bos);
		String jsonResponse=new String(bos.toByteArray());
		assertEquals(typesJson, jsonResponse);
		assertEquals(types, codec.toMessage(Types.class, new StringReader(typesJson)));
	}
}