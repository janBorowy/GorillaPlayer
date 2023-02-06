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

    override fun onCommand(event: MessageReceivedEvent) {
        val args = event.message.contentDisplay.split(' ')
        if(args.size < 2) {
            MessageHelper.sendGenericMessage(event.channel, "Please input video link")
        }

        val joinChannelStatus = CommandUtils.attemptToJoinAChannel(event)
        if(joinChannelStatus != CommandUtils.JOIN_STATUS.SUCCESS &&
            event.member?.voiceState?.channel?.id != PlayerManager.getMusicManager(event.guild).botChannel()?.id) {
            MessageHelper.sendJoinStatusMessage(event.channel, joinChannelStatus)
            return
        }

        // TODO: implement invalid url
        // https://www.javadoc.io/doc/org.apache.httpcomponents/httpclient/4.5.1/org/apache/http/client/utils/URLEncodedUtils.html
        val params = URLEncodedUtils.parse(URI(args[1]), Charset.forName("UTF-8"))
        for (param in params) {
            if( param.name == "v"){
                try {
                    PlayerManager.loadAndPlay(event.channel, param.value)
                    val video = YoutubeApi.getVideo(param.value)
                    MessageHelper.sendVideoInfoMessage(event.channel, video)
                } catch(e: VideoIdException) {
                    MessageHelper.sendGenericMessage(event.channel, "No video associated with given link")
                }
            } else {
                MessageHelper.sendGenericMessage(event.channel, "Invalid video link")
            }
        }
    }

}