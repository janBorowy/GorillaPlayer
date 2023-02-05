import listener.MessageListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

object Bot {

    private val intents = listOf<GatewayIntent>(
        GatewayIntent.MESSAGE_CONTENT
    )

    fun initialize() {
        val jda = JDABuilder.createDefault(BotProperties.token)
            .addEventListeners(GenericEventHandler)
            .addEventListeners(MessageListener)
            .enableIntents(intents)
            .build()

        jda.awaitReady()
    }

}