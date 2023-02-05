package audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

class GuildMusicManager(
    manager: AudioPlayerManager
) {

    private val player: AudioPlayer
    val scheduler: TrackScheduler

    init {
        player = manager.createPlayer()
        scheduler = TrackScheduler(player)
        player.addListener(scheduler)
    }

    public val sendHandler: AudioPlayerSendHandler = AudioPlayerSendHandler(player)

}