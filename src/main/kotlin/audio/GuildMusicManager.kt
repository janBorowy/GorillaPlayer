package audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion

class GuildMusicManager(
    manager: AudioPlayerManager,
    private val guild: Guild
) {

    enum class STATE {
        IDLE,
        ON_CHANNEL
    }
    var state = STATE.IDLE

    private val player: AudioPlayer
    val scheduler: TrackScheduler

    init {
        player = manager.createPlayer()
        scheduler = TrackScheduler(player, guild)
        player.addListener(scheduler)
    }

    val sendHandler: AudioPlayerSendHandler = AudioPlayerSendHandler(player)

    fun joinChannel(channel: AudioChannelUnion) {
        guild.audioManager.openAudioConnection(channel)
        state = STATE.ON_CHANNEL
    }

    fun leaveChannel() {
        guild.audioManager.closeAudioConnection()
        state = STATE.IDLE
    }

    fun botChannel() : AudioChannelUnion? = guild.audioManager.connectedChannel

}