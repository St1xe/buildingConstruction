package ru.sfedu.buildingconstruction.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;
import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.model.HistoryContent;
import ru.sfedu.buildingconstruction.model.Status;
import ru.sfedu.buildingconstruction.util.ConfigurationUtil;


public class MongoProvider {

    private static Logger log = Logger.getLogger(MongoProvider.class);

    private HistoryContent createHistoryContent(String id, Object object, String methodName, Status status) {
        HistoryContent historyContent = new HistoryContent();

        historyContent.setMethodName(methodName);
        historyContent.setStatus(status);
        historyContent.setClassName(object.getClass().getSimpleName());
        historyContent.setId(id);

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.registerModule(new JavaTimeModule());
            historyContent.setObject(mapper.writeValueAsString(object));
        } catch (JsonProcessingException ex) {
            log.error("createHistoryContent [1]: не удалось конвертировать объект в JSON");
            ex.getMessage();
        }

        log.info("createHistoryContent [2]: HistoryContent создан");
        return historyContent;
    }

    public void logHistory(String id, Object object, String methodName, Status status) {
        addHistoryRecordInMongoDB(createHistoryContent(id, object, methodName, status));
    }

    private void addHistoryRecordInMongoDB(HistoryContent historyContent) {

        MongoClient mongoClient
                = new MongoClient(new MongoClientURI(ConfigurationUtil.getConfigurationValue(Constants.MONGO_URI)));

        MongoDatabase db = mongoClient.getDatabase(ConfigurationUtil.getConfigurationValue(Constants.MONGO_DB_NAME));

        MongoCollection<Document> collection = db.getCollection(ConfigurationUtil.getConfigurationValue(Constants.MONGO_TABLE_NAME));

        Document newDoc = new Document(Constants.MONGO_ID, historyContent.getId())
                .append(Constants.MONGO_CLASS_NAME, historyContent.getClassName())
                .append(Constants.MONGO_CREATED_DATA, historyContent.getCreatedDate())
                .append(Constants.MONGO_ACTOR, historyContent.getActor())
                .append(Constants.MONGO_METHOD_NAME, historyContent.getMethodName())
                .append(Constants.MONGO_STATUS, historyContent.getStatus().name())
                .append(Constants.MONGO_OBJECT, historyContent.getObject());

        collection.insertOne(newDoc);
        log.debug("addHistoryRecordInMongoDB [2]: запись добавлена");

    }
}
