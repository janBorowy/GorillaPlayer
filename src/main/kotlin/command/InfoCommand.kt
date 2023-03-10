package command

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.apache.http.client.utils.URLEncodedUtils
import youtubeApi.VideoIdException
import youtubeApi.YoutubeApi
import java.net.URI
import java.nio.charset.Charset

object InfoCommand : BotCommand{

    override val commandId: String = "info"

    override fun onCommand(event: MessageReceivedEvent) {
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
                    val video = YoutubeApi.getVideoById(param.value)
                    MessageHelper.sendVideoInfoMessageEmbed(event.channel, video)
                } catch(e: VideoIdException) {
                    MessageHelper.sendGenericMessage(event.channel, "No video associated with given link")
                }
            } else {
                MessageHelper.sendGenericMessage(event.channel, "Invalid video link")
            }
        }
    }


}