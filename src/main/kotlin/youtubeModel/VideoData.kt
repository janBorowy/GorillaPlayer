package youtubeModel

import com.google.api.services.youtube.model.Video
import java.time.Duration

data class VideoData(
    val title: String? = null,
    val channel: String? = null,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val duration: Duration? = null
)

object YoutubeModel {

    fun buildVideoData(video: Video): VideoData {
        val title = video.snippet?.title
        val channel = video.snippet?.channelTitle
        val description = video.snippet?.description
        val thumbnailUrl = video.snippet?.thumbnails?.standard?.url
        val duration = Duration.parse(video.contentDetails?.duration ?: "PT0S")

        return VideoData(title, channel, description, thumbnailUrl, duration)
    }

}