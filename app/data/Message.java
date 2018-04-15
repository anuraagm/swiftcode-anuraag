package data;

public class Message {
    public String text;
    public enum sender{BOT,USER};
    public sender Sender;
    public FeedResponse feedResponse = new FeedResponse();
}
