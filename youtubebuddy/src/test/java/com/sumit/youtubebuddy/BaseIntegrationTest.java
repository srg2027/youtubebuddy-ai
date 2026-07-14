package com.sumit.youtubebuddy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {
    // Shared configurations, mock beans, or utilities for integration tests can go here.
}
