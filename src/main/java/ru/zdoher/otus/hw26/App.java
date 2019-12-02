package ru.zdoher.otus.hw26;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.zdoher.otus.hw26.domain.Bug;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class App {
    private static AtomicInteger num = new AtomicInteger(1);

    @Bean
    public QueueChannel problemChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel workChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean (name = PollerMetadata.DEFAULT_POLLER )
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get() ;
    }

    @Bean
    public IntegrationFlow workFlow() {
        return IntegrationFlows.from("problemChannel")
                .handle("programmerService", "findProblem")
                .handle("programmerService", "fixProblem")
                .handle("programmerService", "testFixedProblem")
                .handle("programmerService", "rest")
                .channel("workChannel")
                .get();
    }


    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(App.class, args);
        Work work = context.getBean(Work.class);

        while(true) {
            Thread.sleep(10000);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Bug bug = new Bug(String.valueOf(num.getAndIncrement()));
                    System.out.println("Появилась новая проблема №" + bug.getName());
                    Bug bugfixed = work.process(bug);
                    System.out.println("Закрыл сообщение о проблеме №" + bugfixed.getName());

                }
            });

            thread.start();

        }
    }
}
