package org.drpl.telebe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/llm")
public class LLMController {

    @PostMapping("/query")
    public ResponseEntity<String> queryLLM(@RequestBody String llmQueryRequest) {
        System.out.println("LLM query request received: " + llmQueryRequest);
        return ResponseEntity.ok("LLM response data (placeholder)");
    }

}