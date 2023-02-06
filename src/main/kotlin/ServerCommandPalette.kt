import audio.BotAudio
import audio.PlayerManager
import command.BotCommand
import command.InfoCommand
import command.JoinCommand
import command.LeaveCommand
import command.PlayCommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.exceptions.PermissionException
import org.apache.http.client.utils.URLEncodedUtils
import youtubeApi.VideoIdException
import youtubeApi.YoutubeApi
import java.net.URI
import java.nio.charset.Charset

object ServerCommandPalette : HashMap<String, BotCommand>() {

    init {
        register(InfoCommand)
        register(JoinCommand)
        register(LeaveCommand)
        register(PlayCommand)
    }

    private fun register(command: BotCommand) {
        this[command.commandId] = command
    }
}