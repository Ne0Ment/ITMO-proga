package org.main.xml;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.main.Parser;
import org.main.data.Worker;

import java.io.IOException;

/**
 * Class used to serialize Worker objects into xml.
 * @author neoment
 * @version 0.1
 */
public class WorkerSerializer extends StdSerializer<Worker> {
    public WorkerSerializer() {
        this(null);
    }

    public WorkerSerializer(Class<Worker> w) {
        super(w);
    }

    @Override
    public void serialize(Worker worker, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("id", Parser.genString(worker.getId()));
        jgen.writeStringField("name", worker.getName());
        jgen.writeStringField("x_coordinate", Parser.genString(worker.getCoordinates().getX()));
        jgen.writeStringField("y_coordinate", Parser.genString(worker.getCoordinates().getY()));
        jgen.writeStringField("salary", Parser.genString(worker.getSalary()));
        jgen.writeStringField("creation_date", Parser.genString(worker.getCreationDate()));
        jgen.writeStringField("start_date", Parser.genString(worker.getStartDate()));
        jgen.writeStringField("end_date", Parser.genString(worker.getEndDate()));
        jgen.writeStringField("position", Parser.genString(worker.getPosition()));
        jgen.writeStringField("org_name", Parser.genString(worker.getOrganization().getFullName()));
        jgen.writeStringField("org_type", Parser.genString(worker.getOrganization().getType()));
        jgen.writeEndObject();
    }
}
