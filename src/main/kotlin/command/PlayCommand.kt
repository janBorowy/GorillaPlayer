package command

import MessageHelper
import audio.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.apache.http.client.utils.URLEncodedUtils
import youtubeApi.VideoIdException
import youtubeApi.YoutubeApi
import java.net.URI
import java.nio.charset.Charset

object PlayCommand : BotCommand {

    override val commandId: String = "play"
    private const val NUMBER_OF_SEARCHES: Long = 10

    override fun onCommand(event: MessageReceivedEvent) {
        val args = event.message.contentDisplay.split(' ')
        if(args.size < 2) {
            MessageHelper.sendGenericMessage(event.channel, "Please input search phrase or video link")
        }

        // Join channel if not already in
        val joinChannelStatus = CommandUtils.attemptToJoinAChannel(event)
        if(joinChannelStatus != CommandUtils.JOIN_STATUS.SUCCESS &&
            event.member?.voiceState?.channel?.id != PlayerManager.getMusicManager(event.guild).botChannel()?.id) {
            MessageHelper.sendJoinStatusMessage(event.channel, joinChannelStatus)
            return
        }

        // TODO: implement invalid url
        // https://www.javadoc.io/doc/org.apache.httpcomponents/httpclient/4.5.1/org/apache/http/client/utils/URLEncodedUtils.html
        if(args[1].startsWith("https://")) {
            playVideoByURL(args[1], event)
        } else {
            playVideoBySearch(args.subList(1, args.lastIndex).joinToString(separator = " ") { it }, event)
        }
    }

    private fun playVideoByURL(uri: String, event: MessageReceivedEvent) {
        val params = URLEncodedUtils.parse(URI(uri), Charset.forName("UTF-8"))
        for (param in params) {
            if( param.name == "v"){
                try {
                    PlayerManager.loadAndPlay(event.channel, param.value)
                    val video = YoutubeApi.getVideoById(param.value)
                    MessageHelper.sendVideoAddedMessage(event.channel, video)
                } catch(e: VideoIdException) {
                    MessageHelper.sendGenericMessage(event.channel, "No video associated with given link")
                }
            } else {
                MessageHelper.sendGenericMessage(event.channel, "Invalid video link")
            }
        }
    }

    private fun playVideoBySearch(phrase: String, event: MessageReceivedEvent) {
        val videos = YoutubeApi.searchVideo(phrase, NUMBER_OF_SEARCHES)
        if(videos.isEmpty()) {
            MessageHelper.sendGenericMessage(event.channel, "No videos found")
            return
        }
        // TODO: song selection here
        val video = videos.first()

        PlayerManager.loadAndPlay(event.channel, video.id)
        MessageHelper.sendVideoAddedMessage(event.channel, video)
    }

}