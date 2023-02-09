package audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import net.dv8tion.jda.api.entities.Guild
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class TrackScheduler(
    private val player: AudioPlayer,
    private val guild: Guild
    ) : AudioEventAdapter() {
    private val queue : BlockingQueue<AudioTrack> = LinkedBlockingQueue()

    fun queue(track: AudioTrack) {
        if(!player.startTrack(track, true)) {
            this.queue.offer(track)
        }
    }

    fun skip() =
        player.stopTrack()

    fun pause() {
        player.isPaused = true
    }

    fun resume() {
        player.isPaused = false
    }

    private fun playNextTrack() {
        player.startTrack(queue.poll(), false)
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {

        QueueManager.poll(guild)
        when(endReason.toString()) {
            "FINISHED", "STOPPED" -> {
                playNextTrack()
            }
            "LOAD_FAILED" -> handleLoadFailed()
            "REPLACED" -> handleReplaced()
            "CLEANUP" -> handleCleanup()
        }

    }

    private fun handleCleanup() {
        TODO("Not yet implemented")
    }

    private fun handleReplaced() {
        TODO("Not yet implemented")
    }

    private fun handleLoadFailed() {
        playNextTrack()
        println("[ERROR][LOAD_FAILED] Guild@${guild.id}")
    }

}