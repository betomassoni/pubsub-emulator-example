package br.com.robertomassoni.pubsubemulatorexample.services

import br.com.robertomassoni.pubsubemulatorexample.configuration.PubSubProperties
import com.google.cloud.pubsub.v1.MessageReceiver
import com.google.cloud.pubsub.v1.Subscriber
import com.google.pubsub.v1.ProjectSubscriptionName
import com.google.pubsub.v1.PubsubMessage
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@Service
class SubscriberService(
    private val pubSubProperties: PubSubProperties,
) {
    private val receivedMessages = mutableListOf<String>()

    fun receiveMessages(): List<String> {
        val subscriptionName = ProjectSubscriptionName.of(pubSubProperties.projectId, pubSubProperties.subscriptionName)

        // Instantiate an asynchronous message receiver.
        val receiver =
            MessageReceiver { message: PubsubMessage, consumer ->
                receivedMessages.add("Id: ${message.messageId}, Data: ${message.data.toStringUtf8()}")
                consumer.ack()
            }

        var subscriber: Subscriber? = null
        try {
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build()
            // Start the subscriber.
            subscriber.startAsync().awaitRunning()
            System.out.printf("Listening for messages on %s:\n", subscriptionName.toString())
            // Allow the subscriber to run for 30s unless an unrecoverable error occurs.
            subscriber.awaitTerminated(30, TimeUnit.SECONDS)
        } catch (timeoutException: TimeoutException) {
            // Shut down the subscriber after 30s. Stop receiving messages.
            subscriber?.stopAsync()
        }

        return receivedMessages.toList()
    }
}
