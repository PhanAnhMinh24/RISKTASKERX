package com.wbsrisktaskerx.wbsrisktaskerx.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class MaskingSerializer extends JsonSerializer<String> {
    private final int visibleChars;

    public MaskingSerializer(int visibleChars) {
        this.visibleChars = visibleChars;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        int length = value.length();
        String masked = (length <= visibleChars)
                ? value
                : value.substring(0, visibleChars) + "*".repeat(length - visibleChars);
        gen.writeString(masked);
    }
}
