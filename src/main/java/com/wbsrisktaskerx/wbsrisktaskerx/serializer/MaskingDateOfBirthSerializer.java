package com.wbsrisktaskerx.wbsrisktaskerx.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class MaskingDateOfBirthSerializer extends JsonSerializer<OffsetDateTime> {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final int visibleChars;

    public MaskingDateOfBirthSerializer() {
        this.visibleChars = 0;
    }

    @Override
    public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        String formatted = value.format(FORMATTER);

        int length = formatted.length();
        String masked = (length <= visibleChars)
                ? formatted
                : formatted.substring(0, visibleChars) + "*".repeat(length - visibleChars);

        gen.writeString(masked);
    }
}
