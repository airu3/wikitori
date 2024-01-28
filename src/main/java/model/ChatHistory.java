package model;

public class ChatHistory {
	private String speaker;
    private String message;

    public ChatHistory(String speaker, String message) {
        this.speaker = speaker;
        this.message = message;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getMessage() {
        return message;
    }
}
