package org.glimmer.lucene;

import com.alibaba.fastjson.JSON;
import org.glimmer.service.LuceneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class LuceneServiceTest {
    @Autowired
    LuceneService luceneService;

    @Test
    public void FlushTest (){
        luceneService.FlushIndex();
    }
    @Test
    public void SearchTest () throws IOException {
        System.out.println(JSON.toJSONString(luceneService.SearchGroupByKeyword("萧炎",0)));

    }
}
