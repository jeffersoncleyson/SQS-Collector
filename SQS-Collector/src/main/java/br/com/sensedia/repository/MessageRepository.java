package br.com.sensedia.repository;

import br.com.sensedia.model.SQSJsonServer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<SQSJsonServer, String> {
}