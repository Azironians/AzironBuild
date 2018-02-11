package security.assistants;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public final class ProfileAssistant {

    @NotNull
    @Contract(pure = true)
    public static String directoryPath(final String name) {
        return "src\\profiles\\" + name;
    }

    @NotNull
    @Contract(pure = true)
    public static String profilePath(final String name) {
        return directoryPath(name) + "\\" + name + ".hoa";
    }

    @NotNull
    @Contract(value = "_ -> !null", pure = true)
    public static String collectionsPath(final String name) {
        return directoryPath(name) + "\\collections";
    }

    @NotNull
    @Contract(pure = true)
    public static String heroCollectionPath(final String login, final String hero) {
        return collectionsPath(login) + "\\" + hero + ".hoa";
    }

    @Contract(pure = true)
    @NotNull
    public static List<String> createProfile(final String login, final String password) {
        return Arrays.asList(
                login,
                password,
                "1/0",
                "0",
                "0",
                "0",
                "0",
                "0"
        );
    }

    @NotNull
    @Contract(pure = true)
    public static String createDefaultDeck(final String name) {
        return "Start" + name + " " + 0 + " " + "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16";
    }
}
