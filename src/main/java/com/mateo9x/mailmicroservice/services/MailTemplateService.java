package com.mateo9x.mailmicroservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailTemplateService {
    private static final String TEMPLATE_RESOURCE_PREFIX = "classpath:templates/";
    private final ResourceLoader resourceLoader;

    public String getTemplate(@NotNull String templatePath, Map<String, String> replacementStrings) {
        try {
            File file = resourceLoader.getResource(TEMPLATE_RESOURCE_PREFIX + templatePath + "/template.html").getFile();
            byte[] encoded = Files.readAllBytes(Path.of(file.getPath()));
            String htmlString = new String(encoded, StandardCharsets.UTF_8);
            StrSubstitutor sub = new StrSubstitutor(replacementStrings , "{", "}");
            return sub.replace(htmlString);
        }
        catch (Exception e) {
            log.error("Template not found: {}", templatePath);
            throw new RuntimeException("Template not found", e);
        }
    }
}
