package br.com.sensedia.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
@Document(collection = "message")
public class SQSJsonServer {

    @Id
    @JsonIgnore
    private String id;

    @JsonAlias("Type")
    private String type;

    @JsonAlias("MessageId")
    private String message_id;

    @JsonAlias("TopicArn")
    private String topic_arn;

    @JsonAlias("Message")
    private String message;

    @JsonAlias("Timestamp")
    private Date timestamp;

    @JsonAlias("SignatureVersion")
    private int signature_version;

    @JsonAlias("Signature")
    private String signature;

    @JsonAlias("SigningCertURL")
    private String signing_cert_url;
}
