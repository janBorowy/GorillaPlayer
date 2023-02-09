import command.CommandUtils
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder
import net.dv8tion.jda.api.utils.messages.MessageCreateData
import youtubeModel.VideoData

object MessageHelper {

    fun sendGenericMessage(channel: MessageChannel, content: String) {
        val message = with(MessageCreateBuilder()) {
            addContent(content)
        }.build()
        sendMessage(channel, message)
    }

    fun sendVideoAddedMessage(channel: MessageChannel, video: VideoData) =
        sendMessageEmbed(channel, buildVideoAddedEmbed(video))

    fun sendVideoInfoMessageEmbed(channel: MessageChannel, video: VideoData) =
        sendMessageEmbed(channel, buildVideoInfoEmbed(video))

    fun sendQueueInfoMessage(channel: MessageChannel, songs: List<VideoData>) =
        sendMessageEmbed(channel, buildQueueEmbed(songs))

    fun sendJoinStatusMessage(channel: MessageChannel, status: CommandUtils.JOIN_STATUS) {
        val message = when (status) {
            CommandUtils.JOIN_STATUS.ON_ANOTHER_CHANNEL -> "I'm already on a channel"
            CommandUtils.JOIN_STATUS.MEMBER_NOT_ON_CHANNEL ->"You aren't in any voice channel"
            CommandUtils.JOIN_STATUS.PERMISSION_ISSUE -> "I don't have permission to join this channel"
            CommandUtils.JOIN_STATUS.SUCCESS -> ""
        }

        sendGenericMessage(channel, message)
    }

    private fun sendMessage(channel: MessageChannel, message: MessageCreateData) =
        channel.sendMessage(message).queue()

    private fun sendMessageEmbed(channel: MessageChannel, embed: MessageEmbed) {
        val message = with(MessageCreateBuilder()) {
            addEmbeds(embed)
        }.build()

        sendMessage(channel, message)
    }

}