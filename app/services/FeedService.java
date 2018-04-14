package services;

import data.FeedResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class FeedService {
    public FeedResponse getFeedByQuery(String query){
        FeedResponse feedResponseObject = new FeedResponse();
        try{
            WSRequest queryRequest = WS.url("https://news.google.com/news");
            CompletionStage<WSResponse> responsePromise = queryRequest
                    .setQueryParameter("query", query)
                    .setQueryParameter("output", "rss")
                    .get();
            Document feedResponse = responsePromise.thenApply(WSResponse::asXml).toCompleteableFuture().get();
            Node item = feedResponse.getFirstChild().getChildNodes().item(10);
            feedResponse.title = item.getChildNodes().item(0).getFirstChild().getNodeValue();
            feedResponse.pubDate = item.getChildNodes().item(4).getFirstChild().getNodeValue();
            feedResponse.description = item.getChildNodes().item(5).getFirstChild().getNodeValue();
            //feedResponseObject.title = item.getChildNodes.item(0).toString();
            //feedResponseObject.description = item.getChildNodes.item(5).toString();
            //feedResponseObject.pubDate = item.getChildNodes.item(4).toString();
        } catch(Exception e){
            e.printStackTrace();
        }
        return feedResponseObject;
    }
}
