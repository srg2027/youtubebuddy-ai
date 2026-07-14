package com.sumit.youtubebuddy.dto.transcript;

public class TranscriptSegment {

    private int segmentIndex;
    private String text;
    private long startMillis;
    private long endMillis;
    private long durationMillis;

    public TranscriptSegment() {
    }

    public TranscriptSegment(
            int segmentIndex,
            String text,
            long startMillis,
            long endMillis,
            long durationMillis
    ) {
        this.segmentIndex = segmentIndex;
        this.text = text;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.durationMillis = durationMillis;
    }

    public int getSegmentIndex() {
        return segmentIndex;
    }

    public void setSegmentIndex(int segmentIndex) {
        this.segmentIndex = segmentIndex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(long durationMillis) {
        this.durationMillis = durationMillis;
    }
}