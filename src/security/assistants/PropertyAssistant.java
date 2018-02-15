package security.assistants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public final class PropertyAssistant {

    public static Properties makeProperties(final String propertiesPath) {
        final Properties properties = new Properties();
        final FileReader fileReader;
        try {
            fileReader = new FileReader(new File(propertiesPath));
            try {
                properties.load(fileReader);
            } catch (final IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileReader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (final FileNotFoundException e1) {
            e1.printStackTrace();
        }
        return properties;
    }
}
