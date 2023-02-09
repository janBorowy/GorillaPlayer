import listener.MessageListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.managers.Presence
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.internal.managers.PresenceImpl

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
        jda.presence.setPresence(Activity.streaming("music", "https://www.twitch.tv/archaa"), false)
    }

}