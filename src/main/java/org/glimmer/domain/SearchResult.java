package org.glimmer.domain;

import lombok.Data;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Date;

@Data
public class SearchResult {
    private String fileName;
    private String fileID;
    private Long likeNumber;
    private Long dislikeNumber;
    private Long fileSize;
    private Date uploadTime;
    private ArrayList<Docs> data = new ArrayList<Docs>();
    public void AddPara(Docs docs) {
        data.add(docs);
    }
}
