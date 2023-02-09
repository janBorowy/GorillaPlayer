package audio

import com.google.api.services.youtube.model.VideoSnippet
import net.dv8tion.jda.api.entities.Guild
import youtubeModel.VideoData
import java.util.concurrent.LinkedBlockingQueue

class GuildQueueManager(
    val guild: Guild
) : LinkedBlockingQueue<VideoData>()