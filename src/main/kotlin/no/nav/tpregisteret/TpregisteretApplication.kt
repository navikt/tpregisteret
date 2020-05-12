package no.nav.tpregisteret

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["no.nav.tpregisteret"])
class TpregisteretApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<TpregisteretApplication>(*args)
        }
    }
}