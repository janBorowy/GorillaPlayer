import audio.BotAudio
import audio.PlayerManager
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.exceptions.PermissionException
import org.apache.http.client.utils.URLEncodedUtils
import youtubeApi.VideoIdException
import youtubeApi.YoutubeApi
import java.net.URI
import java.nio.charset.Charset

object ServerCommandPalette : HashMap<String, (event: MessageReceivedEvent) -> Unit>() {

    init {
        this["info"] = { event: MessageReceivedEvent -> onInfo(event) }
        this["join"] = { event: MessageReceivedEvent -> onJoin(event) }
        this["leave"] = { event : MessageReceivedEvent -> onLeave(event) }
        this["play"] = { event : MessageReceivedEvent -> onPlay(event) }
    }

    private fun onInfo(event: MessageReceivedEvent) {
        val args = event.message.contentDisplay.split(' ')
        if(args.size < 2) {
            MessageHelper.sendGenericMessage(event.channel, "Please input video link")
        }

        // TODO: implement invalid url
        // https://www.javadoc.io/doc/org.apache.httpcomponents/httpclient/4.5.1/org/apache/http/client/utils/URLEncodedUtils.html
        val params = URLEncodedUtils.parse(URI(args[1]), Charset.forName("UTF-8"))
        for (param in params) {
            if( param.name == "v"){
                try {
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

    private fun onJoin(event: MessageReceivedEvent) {
        // Attempt to join a channel of sender
        // 1. Fail if bot is already on some channel
        // 2. Fail if sender is not on any channel
        // 3. Fail if no permission to join a channel

        // 1
        if(BotAudio.state != BotAudio.STATE.IDLE) {
            MessageHelper.sendGenericMessage(event.channel, "I'm already on a channel")
            return
        }

        // 2
        val channel = event.member?.voiceState?.channel
        if (channel == null) {
            MessageHelper.sendGenericMessage(event.channel, "You aren't in any voice channel")
            return
        }

        // 3
        try {
            BotAudio.joinChannel(event.guild, channel)
        } catch (e: PermissionException) {
            MessageHelper.sendGenericMessage(event.channel, "I don't have permission to join this channel")
        }
    }

    private fun onLeave(event: MessageReceivedEvent) {
        // Attempt to leave a channel
        // 1. Fail if bot is idle
        // 2. Fail if sender is not on your channel

        if(BotAudio.state == BotAudio.STATE.IDLE) {
            MessageHelper.sendGenericMessage(event.channel, "I'm not on any channel")
            return
        }

        val channel = BotAudio.botChannel(event.guild)
        if(event.member?.voiceState?.channel == null || event.member?.voiceState?.channel?.id != channel?.id) {
            MessageHelper.sendGenericMessage(event.channel, "You're not on my channel")
            return
        }

        BotAudio.leaveChannel(event.guild)
    }

    private fun onPlay(event: MessageReceivedEvent) {
        val args = event.message.contentDisplay.split(' ')
        if(args.size < 2) {
            MessageHelper.sendGenericMessage(event.channel, "Please input video link")
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