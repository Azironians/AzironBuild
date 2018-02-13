package modules.securityModules.loadSuppliers;

import annotations.bindingAnnotations.ProfileService;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import security.assistants.PropertiesAssistant;
import security.loadSuppliers.profileSupplier.ProfileLoadSupplier;

public final class ProfileLoadSupplierModule extends AbstractModule {

    @Override
    protected final void configure() {
        final String path = "./src/security/loadSuppliers/profileSupplier/properties/";
        final String messagePropertiesPath = path + "messages.properties";
        final String profilePropertiesPath = path + "profile.properties";
        Names.bindProperties(binder(), PropertiesAssistant.makeProperties(messagePropertiesPath));
        Names.bindProperties(binder(), PropertiesAssistant.makeProperties(profilePropertiesPath));
        requestStaticInjection(ProfileLoadSupplier.class);
    }
}
