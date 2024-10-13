package br.com.robertomassoni.pubsubemulatorexample.services

import br.com.robertomassoni.pubsubemulatorexample.configuration.PubSubProperties
import com.google.api.core.ApiFuture
import com.google.cloud.pubsub.v1.Publisher
import com.google.protobuf.ByteString
import com.google.pubsub.v1.PubsubMessage
import com.google.pubsub.v1.TopicName
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class PublisherService(private val pubSubProperties: PubSubProperties) {

    fun publishMessage(message: String) {
        val topicName = TopicName.of(pubSubProperties.projectId, pubSubProperties.topicName)

        var publisher: Publisher? = null
        try {
            // Create a publisher instance with default settings bound to the topic
            publisher = Publisher.newBuilder(topicName).build()

            val data = ByteString.copyFromUtf8(message)
            val pubsubMessage = PubsubMessage.newBuilder().setData(data).build()

            // Once published, returns a server-assigned message id (unique within the topic)
            val messageIdFuture: ApiFuture<String> = publisher.publish(pubsubMessage)
            val messageId = messageIdFuture.get()
            println("Published message ID: $messageId")
        } finally {
            if (publisher != null) {
                // When finished with the publisher, shutdown to free up resources.
                publisher.shutdown()
                publisher.awaitTermination(1, TimeUnit.MINUTES)
            }
        }
    }
}
