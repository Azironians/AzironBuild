package heroes.devourer.skills.superSkills.regeneration.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegenerationMessageParser {

    private static final Pattern PATTERN = Pattern.compile("Regeneration: (\\d+.\\d+)");

    private RegenerationMessageParser(){}

    public static boolean isRegenerationMessage(final String message) {
        final Matcher matcher = PATTERN.matcher(message);
        return matcher.matches();
    }

    public static double parseMessageGetHealing(final String message) {
        final String[] strings = message.split(" ");
        final int healingIndex = 1;
        return Double.parseDouble(strings[healingIndex]);
    }
}
