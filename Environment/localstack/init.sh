#!/bin/sh

aws configure set aws_access_key_id access_key_aws_local --profile default
aws configure set aws_secret_access_key secret_key_aws_local --profile default
aws configure set default.region us-east-1 --profile default

awslocal sqs create-queue --queue-name onexlab
awslocal sns create-topic --name onexlab-sns
awslocal sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:onexlab-sns --protocol sqs --notification-endpoint http://localhost:4566/000000000000/onexlab
