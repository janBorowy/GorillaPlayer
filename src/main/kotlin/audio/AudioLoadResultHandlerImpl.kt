package audio

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion

class AudioLoadResultHandlerImpl(
    private val manager: GuildMusicManager,
    private val channel: MessageChannelUnion
): AudioLoadResultHandler {
    override fun trackLoaded(track: AudioTrack?) {
        manager.scheduler.queue(track!!)
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {
        TODO("Not yet implemented")
    }

    override fun noMatches() {
        TODO("Not yet implemented")
    }

    override fun loadFailed(exception: FriendlyException?) {
        TODO("Not yet implemented")
    }
}