package bonus.devourerBonuses.bonuses.health;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import managment.actionManagement.service.components.handleComponet.HandleComponent;
import managment.actionManagement.service.engine.services.DynamicHandleService;

public final class HKronArmor extends Bonus implements DynamicHandleService{

    public HKronArmor(final String name, final int id, final ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public final void use() {

    }

    @Override
    public final HandleComponent getHandlerInstance() {
        return null;
    }
}
