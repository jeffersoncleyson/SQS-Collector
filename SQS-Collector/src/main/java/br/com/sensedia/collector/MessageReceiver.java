package br.com.sensedia.collector;

import br.com.sensedia.model.SQSJsonServer;
import br.com.sensedia.process.ProcessMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageReceiver {

    @Autowired
    private ProcessMessage processMessage;

    private ObjectMapper mapper = new ObjectMapper();

    @SqsListener(value = "${sensedia.localstack.sqs-queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(@Payload String message, @Header("SentTimestamp") Long sentTimestamp) {
        try {
            processMessage.startProcess(mapper.readValue(message, SQSJsonServer.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
