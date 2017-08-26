#!/usr/bin/env bash

# define variables for bucket and stack name

# required
BUCKET_NAME="${1:-}"
# optional
STACK_NAME="${2:-}"


# make sure that a bucket name is given
if [[ "${BUCKET_NAME}" == "" ]]; then
    echo "Usage: ./deploy.sh <BUCKET_NAME> [<STACK_NAME>]"
    exit 1;
fi

# use a default stack name if nothing is set
if [[ "${STACK_NAME}" == "" ]]; then
    STACK_NAME="lambda-jersey-spring-example"
fi

# build project
mvn clean package

# package the cloud formation template; this will produce a cfn.packaged.yml file with the S3 bucket URI to the packaged code
aws cloudformation package --template-file ./cfn.yml --s3-bucket ${BUCKET_NAME} --output-template-file cfn.packaged.yml

# deploy the packaged cloud formation template with a given stack name.
# --capabilities CAPABILITY_IAM is needed to successfully create a ChangeSet for cloud formation;
# more info: http://docs.aws.amazon.com/AWSCloudFormation/latest/APIReference/API_CreateChangeSet.html
aws cloudformation deploy --template-file cfn.packaged.yml --stack-name ${STACK_NAME} --capabilities CAPABILITY_IAM

# output the URL to access the provided resources
ONE_URL=$(aws cloudformation describe-stacks --stack-name ${STACK_NAME} --query "Stacks[0].Outputs[?OutputKey == 'OneUrl'].OutputValue" --output text)
echo "Test resource one at: ${ONE_URL}"
TWO_URL=$(aws cloudformation describe-stacks --stack-name ${STACK_NAME} --query "Stacks[0].Outputs[?OutputKey == 'TwoUrl'].OutputValue" --output text)
echo "Test resource two at: ${TWO_URL}"
