package com.zmh.ai.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Description
 * @ClassName VectorStoreConfig
 * @Author zmh
 * @Date 2026/4/16 10:14
 */
@Configuration
public class VectorStoreConfig {

    @Resource
    private MarkdownDocumentLoader markdownDocumentLoader;

    @Bean
    VectorStore simpleVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel)
                .build();
        // 加载文档
        List<Document> documents = markdownDocumentLoader.loadAllMarkdown();
        simpleVectorStore.add(documents);
        return simpleVectorStore;
    }

}
