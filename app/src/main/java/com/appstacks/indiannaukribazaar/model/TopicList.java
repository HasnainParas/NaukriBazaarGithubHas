package com.appstacks.indiannaukribazaar.model;

import java.util.ArrayList;
import java.util.List;

public class TopicList {
    public List<Topic> topics = new ArrayList<>();

    public TopicList() {
    }

    public TopicList(List<Topic> topics) {
        this.topics = topics;
    }
}
