package modules.securityModules.loadSuppliers;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import security.assistants.PropertyAssistant;
import security.loadSuppliers.bonusSupplier.BonusLoadSupplier;

public final class BonusLoadSupplierModule extends AbstractModule {

    @Override
    protected final void configure() {
        final String path = "./src/security/loadSuppliers/bonusSupplier/properties/";
        final String messagePropertiesPath = path + "messages.properties";
        final String deckPropertiesPath = path + "deck.properties";
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(messagePropertiesPath));
        Names.bindProperties(binder(), PropertyAssistant.makeProperties(deckPropertiesPath));
        requestStaticInjection(BonusLoadSupplier.class);
    }
}
