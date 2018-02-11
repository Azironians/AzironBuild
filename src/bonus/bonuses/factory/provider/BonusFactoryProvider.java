package bonus.bonuses.factory.provider;

import bonus.bonuses.factory.BonusFactory;
import com.google.inject.Provider;

public final class BonusFactoryProvider implements Provider<BonusFactory> {

    private BonusFactory bonusFactory;

    @Override
    public final BonusFactory get() {
        if (bonusFactory == null){
            this.bonusFactory = new BonusFactory();
        }
        return bonusFactory;
    }
}
