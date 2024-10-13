package br.com.robertomassoni.pubsubemulatorexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class PubsubSqsExampleApplication

fun main(args: Array<String>) {
    runApplication<PubsubSqsExampleApplication>(*args)
}
