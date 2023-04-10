package org.glimmer.domain;

import lombok.Data;

import javax.print.Doc;
import java.util.ArrayList;

@Data
public class SearchResult {
    private String fileName;
    private String fileID;
    private ArrayList<Docs> data = new ArrayList<Docs>();
    public void AddPara(Docs docs) {
        data.add(docs);
    }
}
