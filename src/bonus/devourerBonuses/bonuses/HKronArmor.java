package bonus.devourerBonuses.bonuses;

import bonus.bonuses.Bonus;
import javafx.scene.image.ImageView;
import managment.actionManagement.service.components.HandleComponent;
import managment.actionManagement.service.engine.DynamicHandleService;

public final class HKronArmor extends Bonus implements DynamicHandleService{

    public HKronArmor(String name, int id, ImageView sprite) {
        super(name, id, sprite);
    }

    @Override
    public void use() {

    }

    @Override
    public HandleComponent getHandlerInstance() {
        return null;
    }
}
