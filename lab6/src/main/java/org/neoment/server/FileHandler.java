package org.neoment.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.neoment.server.xml.WorkerDeserializer;
import org.neoment.server.xml.SerializableManager;
import org.neoment.server.xml.WorkerSerializer;
import org.neoment.shared.Parser;
import org.neoment.shared.Worker;


import java.io.*;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Handles operations with Files.
 * @author neoment
 * @version 0.1
 */
public class FileHandler {
    private final String filePath;
    private final XmlMapper mapper;
    private final PrintWriter writer;

    public FileHandler(String filePath, PrintWriter writer) {
        this.writer = writer;
        if (filePath==null) {
            filePath = Paths.get(System.getProperty("user.dir"), "default.xml").toString();
            writer.println("Will use default XML file: " + filePath);
        }
        this.filePath = filePath;

        this.mapper = new XmlMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Worker.class, new WorkerSerializer());
        module.addDeserializer(Worker.class, new WorkerDeserializer());
        this.mapper.registerModule(module);
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public String readFile(String filePath) throws IOException {
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath));
            int b; StringBuilder out = new StringBuilder();
            while ((b = reader.read()) != -1) {
                out.append(Character.toChars(b));
            }
            return out.toString();
        } catch (FileNotFoundException e) {
            this.createFile(filePath);
            return this.readFile(filePath);
        } catch (SecurityException e) {
            throw new SecurityException(this.SecurityExceptionText());
        }
    }

    public String readFile() throws IOException {
        return this.readFile(this.getFilePath());
    }

    public void writeFile(String s) throws IOException, SecurityException{
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.filePath));
            writer.write(s);
            writer.flush();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(this.FileNotFoundExceptionText());
        } catch (SecurityException e) {
            throw new SecurityException(this.SecurityExceptionText());
        } catch (IOException e) {
            throw new IOException(this.IOExceptionText());
        }
    }
    public String serialize(CollectionManager collectionManager) throws JsonProcessingException {
        var toSerialize = new SerializableManager(collectionManager.getWorkerList(), Parser.genString(collectionManager.getCollectionCreationDate()));
        return this.mapper.writeValueAsString(toSerialize);
    }

    public CollectionManager deserialize(String s) throws JsonProcessingException, ParseException {
        if (s.trim().equals("")) {
            return new CollectionManager();
        }

        var data = this.mapper.readValue(s, SerializableManager.class);
        LocalDateTime collectionCreationDate = (LocalDateTime) Parser.parseString(LocalDateTime.class, data.creationDate);
        var workers = data.workerList;

        long currentId = 1L;
        for (Worker w : workers) { currentId = Math.max(w.getId(), currentId); }
        Set<Long> workerIds = workers.stream().map(Worker::getId).collect(Collectors.toSet());
        if (workerIds.size() != workers.size()) throw new IllegalArgumentException("Id's should be unique.");
        return new CollectionManager(new TreeSet<>(workers), currentId, collectionCreationDate);
    }
    public void saveToXml(CollectionManager collectionManager) throws IOException, SecurityException {
        this.writeFile(this.serialize(collectionManager));
    }

    public CollectionManager readFromXml() throws IOException, ParseException {
        return this.deserialize(this.readFile());
    }

    public CollectionManager loadFromXml() {
        CollectionManager m;
        try {
            this.writer.println("Loaded XML.");
            m = this.readFromXml();
        } catch (Exception e) {
            this.writer.println("Unable to parse XML." + e.getMessage());
            m = new CollectionManager();
        }
        return m;
    }

    private void createFile(String filePath) throws IOException {
        (new File(filePath)).createNewFile();
    }

    private String IOExceptionText() {
        return "Failed to write to file " + this.getFilePath() + ".";
    }

    private String SecurityExceptionText() {
        return "Failed to access file " + this.getFilePath() + ".";
    }

    private String FileNotFoundExceptionText() {
        return "File " + this.getFilePath() + " not found.";
    }

    public String getFilePath() {
        return filePath;
    }
}
