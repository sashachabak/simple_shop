package com.tdoft.shop.config.swagger;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.time.LocalDateTime;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class SwaggerPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext modelPropertyContext) {
        if (modelPropertyContext.getBeanPropertyDefinition().isPresent()) {
            var beanPropertyDefinition = modelPropertyContext.getBeanPropertyDefinition().get();
            if (beanPropertyDefinition.getRawPrimaryType() == LocalDateTime.class) {
                modelPropertyContext
                        .getBuilder()
                        .description("ISO 8601 date time format")
                        .example("2015-01-30T10:30:20");
            }
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}

