package org.wyyt.springcloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wyyt.apollo.tool.ApolloTool;
import org.wyyt.tool.rpc.RpcService;

/**
 * the configuration of bean
 * <p>
 *
 * @author Ning.Zhang(Pegasus)
 * *****************************************************************
 * Name               Action            Time          Description  *
 * Ning.Zhang       Initialize       01/01/2021       Initialize   *
 * *****************************************************************
 */
@Configuration
public class BeanConfig {
    private final PropertyConfig propertyConfig;

    public BeanConfig(PropertyConfig propertyConfig) {
        this.propertyConfig = propertyConfig;
    }

    @Bean
    public RpcService rpcService() {
        return new RpcService();
    }

    @Bean
    public ApolloTool apolloTool() {
        return new ApolloTool(
                this.propertyConfig.getApolloPortalUrl(),
                this.propertyConfig.getApolloMeta(),
                this.propertyConfig.getApolloToken(),
                this.propertyConfig.getApolloAppId(),
                this.propertyConfig.getApolloOperator());
    }
}
