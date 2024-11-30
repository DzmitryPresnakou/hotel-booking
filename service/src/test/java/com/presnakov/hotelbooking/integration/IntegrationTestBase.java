package com.presnakov.hotelbooking.integration;

import com.presnakov.hotelbooking.integration.annotation.IT;
import org.springframework.security.test.context.support.WithMockUser;

@IT
@WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
public abstract class IntegrationTestBase {
}
