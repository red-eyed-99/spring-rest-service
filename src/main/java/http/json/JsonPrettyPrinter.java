package http.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

import java.io.IOException;

public class JsonPrettyPrinter extends DefaultPrettyPrinter {

    @Override
    public DefaultPrettyPrinter createInstance() {
        var jsonPrettyPrinter = new JsonPrettyPrinter();

        jsonPrettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);

        return jsonPrettyPrinter;
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw(": ");
    }
}
