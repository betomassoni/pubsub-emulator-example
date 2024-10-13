package br.com.robertomassoni.pubsubemulatorexample.endpoints

import br.com.robertomassoni.pubsubemulatorexample.services.PublisherService
import br.com.robertomassoni.pubsubemulatorexample.services.SubscriberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pubsub")
class MessageController(
    private val publisherService: PublisherService,
    private val subscriberService: SubscriberService
) {
    @PostMapping("/publish")
    fun publishMessage(
        @RequestBody message: String,
    ): ResponseEntity<Any> {
        publisherService.publishMessage(message)
        return ResponseEntity.ok("Message published successfully.")
    }

    @GetMapping("/receive")
    fun receiveMessages(): ResponseEntity<Any> {
        val messages = subscriberService.receiveMessages()
        return ResponseEntity.ok(messages)
    }
}
