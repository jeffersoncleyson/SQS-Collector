package br.com.sensedia.process;

import br.com.sensedia.model.SQSJsonServer;
import br.com.sensedia.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProcessMessage {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private KafkaTemplate<String, SQSJsonServer> kafkaTemplate;

    @Value("${sensedia.kafka-topic}")
    private String TOPIC;

    public void startProcess(SQSJsonServer sqsJsonServer) throws Exception {
        saveMessageOnMongoDB(sqsJsonServer);
        sendMessageToKafka(sqsJsonServer);
    }

    private void saveMessageOnMongoDB(SQSJsonServer sqsJsonServer) throws Exception {
        SQSJsonServer mSave = messageRepository.save(sqsJsonServer);
        if (!(mSave instanceof SQSJsonServer)) throw new Exception("Erro ao salvar mensagem no MongoDB");
    }

    private void sendMessageToKafka(SQSJsonServer sqsJsonServer) {
        this.kafkaTemplate.send(TOPIC, sqsJsonServer);
    }
}
