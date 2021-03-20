package com.example.readsms;

public class SmsModel {
    String columanName,id,threadId,address,person,date, protocol,read,status,type,read_path_parent,subject,body ,service_center,locked;

    public String getColumanName() {
        return columanName;
    }

    public void setColumanName(String columanName) {
        this.columanName = columanName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRead_path_parent() {
        return read_path_parent;
    }

    public void setRead_path_parent(String read_path_parent) {
        this.read_path_parent = read_path_parent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getService_center() {
        return service_center;
    }

    public void setService_center(String service_center) {
        this.service_center = service_center;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public SmsModel(String columanName, String id, String threadId, String address, String person, String date, String protocol, String read, String status, String type, String read_path_parent, String subject, String body, String service_center, String locked) {
        this.columanName = columanName;
        this.id = id;
        this.threadId = threadId;
        this.address = address;
        this.person = person;
        this.date = date;
        this.protocol = protocol;
        this.read = read;
        this.status = status;
        this.type = type;
        this.read_path_parent = read_path_parent;
        this.subject = subject;
        this.body = body;
        this.service_center = service_center;
        this.locked = locked;
    }
}
