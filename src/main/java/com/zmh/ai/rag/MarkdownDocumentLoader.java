package com.zmh.ai.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName MarkDownDocumentLoader
 * @Author zmh
 * @Date 2026/4/16 9:45
 */
@Component
@Slf4j
class MarkdownDocumentLoader {

    private final Resource[] resources;

    MarkdownDocumentLoader(@Value("classpath:docs/*.md") Resource[] resources) {
        this.resources = resources;
    }

    public List<Document> loadAllMarkdown() {
        List<Document> allDocuments = new ArrayList<>();

        for (Resource resource : resources) {
            String filename = resource.getFilename();
            MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                    .withHorizontalRuleCreateDocument(true)
                    .withIncludeCodeBlock(false)
                    .withIncludeBlockquote(false)
                    .withAdditionalMetadata("filename", filename)
                    .build();
            MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);
            allDocuments.addAll(reader.get());
        }

        return allDocuments;
    }

}

