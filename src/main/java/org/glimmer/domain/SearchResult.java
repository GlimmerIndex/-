package org.glimmer.domain;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SearchResult {
    private String fileName;
    private String fileID;
    private ArrayList<String> data = new ArrayList<String>();
    public void AddPara(String content) {
        data.add(content);
    }
}
