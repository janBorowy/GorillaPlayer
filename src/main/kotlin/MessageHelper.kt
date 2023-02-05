import com.google.api.services.youtube.model.Video
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import net.dv8tion.jda.api.utils.messages.MessageCreateData

object MessageHelper {

    fun sendGenericMessage(channel: MessageChannel, content: String) {
        val message = MessageCreateBuilder()
            .addContent(content)
            .build()
        sendMessage(channel, message)
    }

    fun sendVideoInfoMessage(channel: MessageChannel, video: Video) {
        val embed = buildVideoEmbed(video)
        val message = MessageCreateBuilder()
            .addEmbeds(embed)
            .build()

        sendMessage(channel, message)
    }

    private fun sendMessage(channel: MessageChannel, message: MessageCreateData) =
        channel.sendMessage(message).queue()

}