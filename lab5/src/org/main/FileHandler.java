package org.main;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.main.data.Worker;
import org.main.exceptions.FileNotPassedException;
import org.main.xml.WorkerDeserializer;
import org.main.xml.WorkerSerializer;

import java.io.*;
import java.util.*;

/**
 * Handles operations with Files.
 * @author neoment
 * @version 0.1
 */
public class FileHandler {
    private final String filePath;
    private final boolean pathPassed;
    private final XmlMapper mapper;

    public FileHandler(String filePath) {
        this.filePath = filePath;
        this.pathPassed = !(filePath == null);

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
            throw new FileNotFoundException(this.FileNotFoundExceptionText());
        } catch (SecurityException e) {
            throw new SecurityException(this.SecurityExceptionText());
        }
    }

    public String readFile() throws FileNotPassedException, IOException {
        if (!this.pathPassed) throw new FileNotPassedException();
        return this.readFile(this.filePath);
    }

    public void writeFile(String s) throws IOException, SecurityException, FileNotFoundException, FileNotPassedException {
        if (!this.pathPassed) throw new FileNotPassedException();
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
        return this.mapper.writeValueAsString(collectionManager.getWorkerList());
    }

    public CollectionManager deserialize(String s) throws JsonProcessingException {
        Worker[] workers = this.mapper.readValue(s, Worker[].class);
        long currentId = -1L;
        for (Worker w : workers) { currentId = Math.max(w.getId(), currentId); }
        return new CollectionManager(new TreeSet<>(Arrays.asList(workers)), currentId);
    }

    public void saveToXml(CollectionManager collectionManager) throws IOException, SecurityException, FileNotFoundException, FileNotPassedException {
        this.writeFile(this.serialize(collectionManager));
    }

    public CollectionManager readFromXml() throws FileNotPassedException, IOException {
        return this.deserialize(this.readFile());
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
