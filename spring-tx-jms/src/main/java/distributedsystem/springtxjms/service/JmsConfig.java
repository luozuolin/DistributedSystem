package distributedsystem.springtxjms.service;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfig {
    @Bean
    PlatformTransactionManager transactionManager(ConnectionFactory connectionFactory){
        return new JmsTransactionManager(connectionFactory);
    }
    @Bean
    JmsTemplate jmsTemplate(ConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate=new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }
    @Bean
    JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                               PlatformTransactionManager platformTransactionManager,
                                                               DefaultJmsListenerContainerFactoryConfigurer configurer){
        DefaultJmsListenerContainerFactory factory=new DefaultJmsListenerContainerFactory();
        configurer.configure(factory,connectionFactory);
        factory.setReceiveTimeout(10000L);
        factory.setTransactionManager(platformTransactionManager);
        return  factory;

    }
}
