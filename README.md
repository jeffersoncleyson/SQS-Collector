# Informações do Projeto

#
## Introdução
#

Este projeto é uma prova de conceito da utilização de produtos da AWS SQS - *Simple Queue Service* como serviço de troca de mensagens. Também foi utilizado o Apache Kafka como produtor de mensagens extraidas da fila do SQS e o MongoDB como forma de armazenamento de mensagens.</br>
Para testes as mensagens devem ser produzidas através do comandos representados na sessão **Sequência de comandos e respostas esperadas.** no tópico **Enviar uma mensagem no topico do SNS**, assim que a mensagem é produzida no SNS - *Simple Notification Service* ela é enviada ao SQS no qual o código **SQS-Collector** consume está mensagem.</br></br>


#
## Técnologias utilizadas neste projeto
#

* #### Spring Boot

* #### Apache Kafka

* #### Lombok

* #### Localstack - AWS Local

Os arquivos README de cada projeto contém conteúdos conceituais e links para outros sites mostrando mais informações. Abaixo os projetos:

* Projeto **[Kafka-API](https://github.com/jeffersoncleyson/kafka-api)**
* Projeto **[Mongo-API](https://github.com/jeffersoncleyson/Security-API)**
* Projeto **[Env-DEV](https://github.com/jeffersoncleyson/env_dev)**

</br></br>

#
# Preparando o ambiente
#

## Instalação do AWS CLI

```
sudo snap install aws-cli --classic
```

## Configuração do CLI

```
aws configure --profile default
```
```
access_key_aws_local: access_key_aws_local
```
```
AWS Secret Access Key: secret_key_aws_local
```
```
Default region name: us-east-1
```

#
## Iniciar o ambiente do SNS, SQS, Kafka e MongoDB
#

O docker-compose do ambiente se encontra disponível no pasta **Environment**

* Para verificar se o ambiente do LocalStack está correto verificar se o estado é running através do link abaixo:

```
http://localhost:4566/health
```

```
{
    "services": {
        "sqs": "running",
        "sns": "running"
    },
    "features": {
        "persistence": "disabled",
        "initScripts": "initialized"
    }
}
```

#
## Sequência de comandos e respostas esperadas.
#

Para testes e verificar se o ambiente está funcionando como o esperado.</br>

* Criar uma fila no SQS

```
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name onexlab
```

```
{
    "QueueUrl": "http://localhost:4566/000000000000/onexlab"
}
```

* Listar filas do SQS

```
aws --endpoint-url=http://localhost:4566 sqs list-queues
```
```
{
    "QueueUrls": [
        "http://localhost:4566/000000000000/onexlab"
    ]
}
```

* Receber mensagem da fila do SQS

```
aws --endpoint-url=http://localhost:4566 sqs receive-message --queue-url http://localhost:4566/000000000000/onexlab
```

* Criar uma Topico no SNS

```
aws --endpoint-url=http://localhost:4566 sns create-topic --name onexlab-sns
```
```
{
    "TopicArn": "arn:aws:sns:us-east-1:000000000000:onexlab-sns"
}
```
* Listar os Topicos do SNS

```
aws --endpoint-url=http://localhost:4566 sns list-subscriptions
{
    "Subscriptions": []
}
```

* Subscrever o Topico do SNS na fila do SQS

```
aws --endpoint-url=http://localhost:4566 sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:onexlab-sns --protocol sqs --notification-endpoint http://localhost:4566/000000000000/onexlab
```
```
{
    "SubscriptionArn": "arn:aws:sns:us-east-1:000000000000:onexlab-sns:5b8d4015-8ba1-41a9-9cb2-0d903a05a6da"
}
```

* Enviar uma mensagem no topico do SNS

```
aws --endpoint-url=http://localhost:4566 sns publish  --topic-arn arn:aws:sns:us-east-1:000000000000:onexlab-sns --message 'Welcome to Onexlab!'
```
```
{
    "MessageId": "6d8d0ec3-5b1f-4824-a4ec-b1e6ecd821f2"
}
```

* Ler a mensagem criada no SQS

```
aws --endpoint-url=http://localhost:4566 sqs receive-message --queue-url http://localhost:4566/000000000000/onexlab
```
```
{
    "Messages": [
        {
            "MD5OfBody": "6c989b820f4f02dc6e9913615d77f019",
            "MessageId": "01c41da2-a548-775e-fcba-6587dae0ac06",
            "ReceiptHandle": "chpurzlhdcnseymshvzgvmslstnamfkxciwqsxpsirdftmvnzmblhhgnhxjtaqbzkodpvpsiowtkprqbkoveveebuotkjzfyhgpqnkvtzvmyzvhdcfvshlsvmfgrqqsujciefkwbatvemqbvlewhkcwyfvkpwkqqalrcffwiuqkuewswxlvqussny",
            "Body": "{\"Type\": \"Notification\", \"MessageId\": \"6d8d0ec3-5b1f-4824-a4ec-b1e6ecd821f2\", \"TopicArn\": \"arn:aws:sns:us-east-1:000000000000:onexlab-sns\", \"Message\": \"Welcome to Onexlab!\", \"Timestamp\": \"2021-06-14T18:47:45.044Z\", \"SignatureVersion\": \"1\", \"Signature\": \"EXAMPLEpH+..\", \"SigningCertURL\": \"https://sns.us-east-1.amazonaws.com/SimpleNotificationService-0000000000000000000000.pem\"}"
        }
    ]
}
```