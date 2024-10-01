package com.message.structure;

import com.message.io.MessageReader;
import com.message.io.MessageWriter;

public class StringMessage implements MessageStructure {
    private String message;
    public StringMessage(String message) {
        this.message = message;
    }
    public StringMessage(MessageReader reader) {
        message = reader.nextString();
    }
    @Override
    public MessageWriter getWriter() {
        MessageWriter writer = new MessageWriter();
        writer.writeString(message);
        return writer;
    }
    public String getMessage() {
        return message;
    }
}
