package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.rslist.domain.User;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class RsEvent {
    @NotNull
    private String eventName;
    private String keyword;
    private int userId;

    public RsEvent() {
    }

    public RsEvent(String eventName, String keyword, int userId) {
        this.eventName = eventName;
        this.keyword = keyword;
        this.userId=userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
//@JsonIgnore
    public int getUserId() { return userId; }
//@JsonProperty
    public void setUserId(int userId) { this.userId = userId; }
}