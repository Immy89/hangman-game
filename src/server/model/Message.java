package server.model;

import shared.Constants;
import shared.MessageException;
import shared.MsgType;
import shared.MessageException;

/**
 * Created by Kim Säther on 08-Nov-18.
 */
public class Message {
    private MsgType msgType;
    private String msgBody;
    private String receivedString;

    public Message(String receivedString) {
        parse(receivedString);
        this.receivedString = receivedString;
    }

    public MsgType getMsgType() {
        return this.msgType;
    }

    public String getMsgBody() {
        return this.msgBody;
    }

    public String getReceivedString() {
        return this.receivedString;
    }

    private void parse(String strToParse) {
        try {
            String[] msgTokens = strToParse.split(Constants.MSG_DELIMETER);
            msgType = MsgType.valueOf(msgTokens[Constants.MSG_TYPE_INDEX].toUpperCase());
            if (hasBody(msgTokens)) {
                msgBody = msgTokens[Constants.MSG_BODY_INDEX];
            }
        } catch (Throwable throwable) {
            throw new MessageException(throwable);
        }
    }

    private boolean hasBody(String[] msgTokens) {
        return msgTokens.length > 1;
    }
}
