package heroes.devourer.skills.superSkills.consuming.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ConsumingMessageParser {

    private static final Pattern PATTERN = Pattern.compile("Consuming: (\\d+.\\d+)");

    private ConsumingMessageParser (){}

    public static boolean isConsumingMessage(final String message) {
        final Matcher matcher = PATTERN.matcher(message);
        return matcher.matches();
    }

    public static double parseMessageGetHealing(final String message) {
        final String[] strings = message.split(" ");
        final int healingIndex = 1;
        return Double.parseDouble(strings[healingIndex]);
    }
}
