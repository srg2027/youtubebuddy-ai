from flask import Flask, request, jsonify
from youtube_transcript_api import YouTubeTranscriptApi
import re

app = Flask(__name__)

def extract_video_id(url):
    pattern = r"(?:v=|\/)([0-9A-Za-z_-]{11}).*"
    match = re.search(pattern, url)
    return match.group(1) if match else None

@app.route('/transcript', methods=['POST'])
def get_transcript():
    try:
        data = request.get_json()

        if not data:
            return jsonify({
                "error": "Request body is missing"
            }), 400

        url = data.get("youtubeUrl")

        if not url:
            return jsonify({
                "error": "youtubeUrl is required"
            }), 400

        video_id = extract_video_id(url)

        if not video_id:
            return jsonify({
                "error": "Invalid YouTube URL"
            }), 400

        try:
            transcript = YouTubeTranscriptApi().fetch(
                video_id,
                languages=['en','hi']
            )
        except:
            transcript = YouTubeTranscriptApi().fetch(video_id)

        text = " ".join(
            snippet.text
            for snippet in transcript
        )

        return jsonify({
            "transcript": text
        })

    except Exception as e:
        return jsonify({
            "error": str(e)
        }), 500

if __name__ == "__main__":
    app.run(port=5000)