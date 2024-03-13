package ru.sfedu.buildingconstruction.model;

import java.util.Date;
import ru.sfedu.buildingconstruction.Constants;

/**
 *
 * @author maksim
 */
public class HistoryContent {

    private String id;
    private String className;
    private String createdDate;
    private String actor;
    private String methodName;
    private String object;
    private Status status;

    public HistoryContent() {
        this.createdDate = new Date().toString();
        this.actor = Constants.ACTOR;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getActor() {
        return actor;
    }

    public String getMethodName() {
        return methodName;
    }

    public Enum getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getObject() {
        return object;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setObject(String object) {
        this.object = object;
    }

}
