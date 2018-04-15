package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

import static data.Message.sender.BOT;
import static data.Message.sender.USER;

public class MessageActor extends UntypedActor {
    private final ActorRef out;

    //self reference the actor
    public MessageActor(ActorRef out) {
        this.out = out;
    }

    //PROPS
    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);
    }

    //Object of feed service
    private FeedService feedService = new FeedService();
    private FeedResponse feedResponse = new FeedResponse();
    private NewsAgentService newsAgentService = new NewsAgentService();

    @Override
    public void onReceive(Object message) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        if (message instanceof String) {
            Message messageObject = new Message();
            messageObject.text = (String) message;
            messageObject.Sender = Message.sender.USER;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
            String query = newsAgentService.getNewsAgentResponse("Find " + messageObject.text, UUID.randomUUID()).query;
            System.out.println(query);
            feedResponse = feedService.getFeedByQuery(query);
            messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + query;
            messageObject.feedResponse = feedResponse;
            messageObject.Sender = Message.sender.BOT;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
        }
    }
}