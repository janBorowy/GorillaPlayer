package audio

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import net.dv8tion.jda.api.entities.Guild
import java.util.Queue
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

    private fun playNextTrack() {
        player.startTrack(queue.poll(), false)
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {

        if(endReason!!.name == "FINISHED") {
            QueueManager.poll(guild)
            playNextTrack()
        }

    }

}