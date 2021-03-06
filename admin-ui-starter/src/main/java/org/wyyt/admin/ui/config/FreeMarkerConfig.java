package org.wyyt.admin.ui.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.wyyt.admin.ui.template.*;

/**
 * FreeMarker's configuration
 * <p>
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize       01/01/2021       Initialize   *
 * *****************************************************************
 */
@Configuration
public class FreeMarkerConfig implements InitializingBean {
    private final freemarker.template.Configuration freeMarkerConfiguration;

    public FreeMarkerConfig(final freemarker.template.Configuration freeMarkerConfiguration) {
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public InsertDirective insertDirective() {
        return new InsertDirective();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public NoInsertDirective noInsertDirective() {
        return new NoInsertDirective();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public DeleteDirective deleteDirective() {
        return new DeleteDirective();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public NoDeleteDirective noDeleteDirective() {
        return new NoDeleteDirective();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public UpdateDirective updateDirective() {
        return new UpdateDirective();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public NoUpdateDirective noUpdateDirective() {
        return new NoUpdateDirective();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public SelectDirective selectDirective() {
        return new SelectDirective();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public NoSelectDirective noSelectDirective() {
        return new NoSelectDirective();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public OnlySelectDirective onlySelectDirective() {
        return new OnlySelectDirective();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public NotOnlySelectDirective notOnlySelectDirective() {
        return new NotOnlySelectDirective();
    }

    @Override
    public void afterPropertiesSet() {
        this.freeMarkerConfiguration.setSharedVariable("insert", insertDirective());
        this.freeMarkerConfiguration.setSharedVariable("delete", deleteDirective());
        this.freeMarkerConfiguration.setSharedVariable("update", updateDirective());
        this.freeMarkerConfiguration.setSharedVariable("select", selectDirective());

        this.freeMarkerConfiguration.setSharedVariable("no_insert", noInsertDirective());
        this.freeMarkerConfiguration.setSharedVariable("no_delete", noDeleteDirective());
        this.freeMarkerConfiguration.setSharedVariable("no_update", noUpdateDirective());
        this.freeMarkerConfiguration.setSharedVariable("no_select", noSelectDirective());

        this.freeMarkerConfiguration.setSharedVariable("only_select", onlySelectDirective());

        this.freeMarkerConfiguration.setSharedVariable("not_only_select", notOnlySelectDirective());
    }
}