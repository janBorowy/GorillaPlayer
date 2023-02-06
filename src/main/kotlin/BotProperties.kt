import java.io.FileInputStream
import java.io.IOException
import java.util.Properties

const val BOT_PROPERTIES_FILE_LOCATION = "bot.properties"

object BotProperties {

    private val properties : Properties

    init {
        val properties = Properties()
        try {
            val input = FileInputStream(BOT_PROPERTIES_FILE_LOCATION)
            properties.load(input)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        this.properties = properties

    }

    val token = properties.getProperty("token")
    val prefix = properties.getProperty("prefix")
    val id = properties.getProperty("bot_id")
    val apiToken = properties.getProperty("api_token")
    val clientId = properties.getProperty("client_id")

}