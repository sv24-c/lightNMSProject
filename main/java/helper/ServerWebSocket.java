package helper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by smit on 7/4/22.
 */

@ServerEndpoint("/endpoint")
public class ServerWebSocket
{
    private static Logger _logger = new Logger();

    private static Session session;

    @OnOpen
    public void onOpen(Session session)
    {
        try
        {
            ServerWebSocket.session = session;
        }
        catch (Exception exception)
        {
            _logger.error("ServerWebSocket OnOpen method having error. ", exception);
        }
    }

    public void sendMessage(String message)
    {
        try
        {
            if (ServerWebSocket.session.isOpen())
            {
                ServerWebSocket.session.getBasicRemote().sendText(message);
            }
            else
            {
                ServerWebSocket.session.getBasicRemote().sendText("Web Socket Session is not Open");
            }
        }
        catch (IOException exception)
        {
            _logger.error("ServerWebSocket sendMessage method having error. ", exception);
        }
    }

    @OnError
    public void onError(Throwable throwable)
    {
        throwable.printStackTrace();

        try
        {
            //ServerWebSocket.session.getBasicRemote().sendText(String.valueOf(throwable));
        }
        catch (Exception exception)
        {
            _logger.error("ServerWebSocket OnError method having error. ", exception);
        }
    }

    @OnClose
    public void onClose()
    {
        try
        {
            ServerWebSocket.session.close();
        }
        catch (Exception exception)
        {
            _logger.error("ServerWebSocket OnClose method having error. ", exception);
        }
    }
}
