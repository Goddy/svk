package be.spring.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

/**
 * Created by u0090265 on 1/26/16.
 */
@Configuration
public class WebflowConfig extends AbstractFlowConfiguration {
    @Bean
    public FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder(flowRegistry())
                .addFlowExecutionListener(securityFlowExecutionListener())
                .build();
    }

    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder()
                .setBasePath("/WEB-INF/flows")
                .addFlowLocation("editNews/editNews-flow.xml")
                .addFlowLocation("common/global-flow.xml")
                .build();
    }

    @Bean
    FlowHandlerMapping flowHandlerMapping() {
        FlowHandlerMapping flowHandlerMapping = new FlowHandlerMapping();
        flowHandlerMapping.setFlowRegistry(flowRegistry());
        flowHandlerMapping.setOrder(-1);
        return flowHandlerMapping;
    }

    @Bean
    FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter flowHandlerAdapter = new FlowHandlerAdapter();
        flowHandlerAdapter.setFlowExecutor(flowExecutor());
        flowHandlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return flowHandlerAdapter;
    }

    @Bean
    SecurityFlowExecutionListener securityFlowExecutionListener() {
        return new SecurityFlowExecutionListener();
    }
}
