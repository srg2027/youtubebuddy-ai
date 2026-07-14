package com.sumit.youtubebuddy.service.rag.chroma;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CollectionManagerService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Map<String, String> collectionCache = new ConcurrentHashMap<>();

    private static final String BASE_URL = "http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections";

    public CollectionManagerService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Returns the ChromaDB collection ID for the given type.
     * Creates the collection automatically if it does not exist.
     */
    public String getOrCreateCollectionId(CollectionType type) {
        String collectionName = type.getCollectionName();
        return collectionCache.computeIfAbsent(collectionName, this::fetchOrCreateCollection);
    }

    /**
     * Returns true if the collection for the given type already exists in ChromaDB.
     */
    public boolean exists(CollectionType type) {
        String collectionName = type.getCollectionName();
        if (collectionCache.containsKey(collectionName)) {
            return true;
        }
        try {
            ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL, List.class);
            List<Map<String, Object>> collections = response.getBody();
            if (collections != null) {
                for (Map<String, Object> col : collections) {
                    if (collectionName.equals(col.get("name"))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error checking Chroma collection: " + collectionName, e);
        }
        return false;
    }

    /**
     * Evicts the cached collection ID for the given type.
     */
    public void evict(CollectionType type) {
        collectionCache.remove(type.getCollectionName());
    }

    private String fetchOrCreateCollection(String collectionName) {
        try {
            // 1. Lookup collection by name
            ResponseEntity<List> response = restTemplate.getForEntity(BASE_URL, List.class);
            List<Map<String, Object>> collections = response.getBody();
            if (collections != null) {
                for (Map<String, Object> col : collections) {
                    if (collectionName.equals(col.get("name"))) {
                        return (String) col.get("id");
                    }
                }
            }

            // 2. If collection does not exist, create it automatically
            Map<String, Object> body = Map.of("name", collectionName);
            ResponseEntity<Map> createResponse = restTemplate.postForEntity(BASE_URL, body, Map.class);
            Map<String, Object> createdCollection = createResponse.getBody();
            if (createdCollection != null && createdCollection.get("id") != null) {
                return (String) createdCollection.get("id");
            }
            throw new RuntimeException("Failed to retrieve ID for created collection: " + collectionName);
        } catch (Exception e) {
            throw new RuntimeException("Error managing Chroma collection: " + collectionName, e);
        }
    }
}

