package br.com.robertomassoni.pubsubemulatorexample.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.cloud.gcp.pubsub")
class PubSubProperties {
    lateinit var projectId: String
    lateinit var topicName: String
    lateinit var subscriptionName: String
}