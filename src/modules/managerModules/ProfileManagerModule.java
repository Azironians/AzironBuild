package modules.managerModules;

import annotations.bindingAnnotations.ProfileService;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import controllers.main.menu.ProfileRequest;
import managment.playerManagement.Player;
import managment.profileManagement.ProfileManager;

import java.util.EnumMap;

public final class ProfileManagerModule extends AbstractModule {

    @Override
    protected final void configure() {
        bind(ProfileManager.class).asEagerSingleton();
        bind(new TypeLiteral<EnumMap<ProfileRequest, Player>>(){})
                .annotatedWith(ProfileService.class).toInstance(new EnumMap<>(ProfileRequest.class));
    }
}
