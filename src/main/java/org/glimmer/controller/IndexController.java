package org.glimmer.controller;

import org.glimmer.domain.ResponseResult;
import org.glimmer.service.LuceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    LuceneService luceneService;

    @PutMapping
    @PreAuthorize("hasAuthority('system:index:flush')")
    public ResponseResult FlushIndex() {
        return luceneService.FlushIndex();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('system:index:delete')")
    public ResponseResult DeleteIndex() {
        return luceneService.DeleteIndex();
    }
    @DeleteMapping("/{pdf_id}")
    @PreAuthorize("hasAuthority('system:index:delete')")
    public ResponseResult DeleteOnePdfIndex(@PathVariable String pdf_id) {
        return luceneService.DeletePdfIndex(pdf_id);
    }

    @PostMapping("/{pdf_id}")
    @PreAuthorize("hasAuthority('system:index:add')")
    public ResponseResult AddOnePdfIndex(@PathVariable String pdf_id) {
        return luceneService.AddPdfIndex(pdf_id);
    }

    @GetMapping("/{keyword}/{pageOffset}")
    @PreAuthorize("hasAuthority('system:index:search')")
    public ResponseResult SearchGroupByKeyword(@PathVariable String keyword,@PathVariable int pageOffset) throws IOException {
        return luceneService.SearchGroupByKeyword(keyword,pageOffset);
    }
}
