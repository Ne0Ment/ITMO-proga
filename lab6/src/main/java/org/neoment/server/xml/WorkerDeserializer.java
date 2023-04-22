package org.neoment.server.xml;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.neoment.shared.OrganizationType;
import org.neoment.shared.Parser;
import org.neoment.shared.Position;
import org.neoment.shared.Worker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Class used to deserialize xml into worker objects.
 * @author neoment
 * @version 0.1
 */
public class WorkerDeserializer extends StdDeserializer<Worker> {
    public WorkerDeserializer() {
        this(null);
    }

    public WorkerDeserializer(Class<?> w) {
        super(w);
    }

    @Override
    public Worker deserialize(JsonParser jp, DeserializationContext ctx) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        try {
            Worker worker = new Worker((Long) Parser.parseString(Long.class, node.get("id").textValue()));
            worker.setName(node.get("name").textValue());
            worker.getCoordinates().setX((Integer) Parser.parseString(Integer.class, node.get("x_coordinate").textValue()));
            worker.getCoordinates().setY((Integer) Parser.parseString(int.class, node.get("y_coordinate").textValue()));
            worker.setSalary((Float) Parser.parseString(Float.class, node.get("salary").textValue()));
            worker.setCreationDate((LocalDateTime) Parser.parseString(LocalDateTime.class, node.get("creation_date").textValue()));
            worker.setStartDate((Date) Parser.parseString(Date.class, node.get("start_date").textValue()));
            worker.setEndDate((LocalDateTime) Parser.parseString(LocalDateTime.class, node.get("end_date").textValue()));
            worker.setPosition((Position) Parser.parseString(Position.class, node.get("position").textValue()));
            worker.getOrganization().setFullName(node.get("org_name").textValue());
            worker.getOrganization().setType((OrganizationType) Parser.parseString(OrganizationType.class, node.get("org_type").textValue()));
            return worker;
        } catch (Exception e) {
            return null;
        }
    }

}
