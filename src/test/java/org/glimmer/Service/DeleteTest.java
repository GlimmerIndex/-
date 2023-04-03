package org.glimmer.Service;

import org.glimmer.service.DeletePDFFiles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeleteTest {
    @Autowired
    DeletePDFFiles deletePDFFiles;
    @Test
    void delete(){
        Long fileId = 1642534465156714497L;
        try{
        System.out.println(deletePDFFiles.DeletePDF(fileId));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
