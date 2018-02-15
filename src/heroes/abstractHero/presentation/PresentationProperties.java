package heroes.abstractHero.presentation;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public final class PresentationProperties {

    @Inject
    @Named("WIDTH")
    static int WIDTH;

    @Inject
    @Named("HEIGHT")
    static int HEIGHT;

    @Inject
    @Named("START_OPACITY")
    static int START_OPACITY;

    @Inject
    @Named("ANIMATION_TIME")
    static int ANIMATION_TIME;
}