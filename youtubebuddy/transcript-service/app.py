from flask import Flask, request, jsonify
from youtube_transcript_api import YouTubeTranscriptApi
import re

app = Flask(__name__)


def extract_video_id(url):
    pattern = r"(?:v=|\/)([0-9A-Za-z_-]{11}).*"
    match = re.search(pattern, url)
    return match.group(1) if match else None


@app.route("/transcript", methods=["POST"])
def get_transcript():
    try:

        data = request.get_json()

        if not data:
            return jsonify({
                "error": "Request body is missing"
            }), 400

        youtube_url = data.get("youtubeUrl")

        if not youtube_url:
            return jsonify({
                "error": "youtubeUrl is required"
            }), 400

        video_id = extract_video_id(youtube_url)

        if not video_id:
            return jsonify({
                "error": "Invalid YouTube URL"
            }), 400

        # Fetch transcript
        try:
            transcript = YouTubeTranscriptApi().fetch(
                video_id,
                languages=["en", "hi"]
            )
        except Exception:
            transcript = YouTubeTranscriptApi().fetch(video_id)

        # Build complete transcript
        full_transcript = " ".join(
            snippet.text
            for snippet in transcript
        )

        # Build timestamped segments
        segments = []

        for index, snippet in enumerate(transcript):

            start_millis = int(snippet.start * 1000)

            end_millis = int(
                (snippet.start + snippet.duration) * 1000
            )

            segments.append({
                "segmentIndex": index,
                "text": snippet.text,
                "startMillis": start_millis,
                "endMillis": end_millis,
                "durationMillis": int(snippet.duration * 1000)
            })

        return jsonify({

            "videoId": video_id,

            "videoUrl": youtube_url,

            # Placeholder for now
            "videoTitle": "",

            "transcript": full_transcript,

            "segments": segments

        })

    except Exception as e:

        return jsonify({
            "error": str(e)
        }), 500


if __name__ == "__main__":
    app.run(port=5000)