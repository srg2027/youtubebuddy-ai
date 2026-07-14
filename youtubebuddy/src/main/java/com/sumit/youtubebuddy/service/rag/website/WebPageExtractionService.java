package com.sumit.youtubebuddy.service.rag.website;

import com.sumit.youtubebuddy.dto.rag.ExtractedWebPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebPageExtractionService {

    public ExtractedWebPage extract(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            String title = doc.title();
            
            // doc.text() extracts all text, removing HTML tags and normalizing whitespace
            String text = doc.body() != null ? doc.body().text() : doc.text();

            return new ExtractedWebPage(title != null ? title : "", text != null ? text : "");
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract web page content from: " + url, e);
        }
    }
}
