package com.proyecto.pe.edu.ciberfake.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class ActivacionContentBuilder {
    private final TemplateEngine templateEngine;

    public String build() {
        Context context = new Context();
        return templateEngine.process("activacionTemplate", context);
    }
}
