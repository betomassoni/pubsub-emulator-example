#!/bin/bash

# Set configuration variables
EMULATOR_HOST="localhost:8085"
PROJECT_ID="some-project"
TOPIC_NAME="some-topic"
SUBSCRIPTION_NAME="some-subscription"

# Export the necessary variables for the script to work
export PUBSUB_EMULATOR_HOST=$EMULATOR_HOST
export PUBSUB_PROJECT_ID=$PROJECT_ID

# Start the Pub/Sub emulator
$(gcloud beta emulators pubsub env-init)

# Wait for the emulator to fully start
sleep 5

# Clone the Python Pub/Sub repository
git clone https://github.com/googleapis/python-pubsub.git

# Navigate to the samples directory
cd python-pubsub/samples/snippets

# Create the topic
python3 publisher.py $PROJECT_ID create $TOPIC_NAME
echo "Topic '$TOPIC_NAME' created!"

# Create the subscription
python3 subscriber.py $PROJECT_ID create $TOPIC_NAME $SUBSCRIPTION_NAME
echo "Subscription '$SUBSCRIPTION_NAME' created!"

# Start receiving messages on the subscription
python3 subscriber.py $PROJECT_ID receive $SUBSCRIPTION_NAME
echo "Listening for messages on '$TOPIC_NAME' with subscription '$SUBSCRIPTION_NAME'..."
