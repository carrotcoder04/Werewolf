package com.serialization;

import com.io.Reader;
import com.io.Writer;

public interface Serializable {
    void deserialize(Reader reader);
    Writer serialize();
}
