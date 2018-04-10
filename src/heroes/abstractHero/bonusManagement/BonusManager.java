package heroes.abstractHero.bonusManagement;

import managment.actionManagement.service.components.providerComponent.ProviderComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class BonusManager {

    private ProviderComponent<Integer> firstProviderComponent = new ProviderComponent<>() {

        private int priority = 1;

        @Override
        public final Integer getValue() {
            final Random random = new Random();
            final int bound = 16; //16 cards;
            return random.nextInt(bound);
        }

        @Override
        public final int getPriority() {
            return priority;
        }

        @Override
        public final void setPriority(final int priority) {
            this.priority = priority;
        }
    };

    private ProviderComponent<Integer> secondProviderComponent = new ProviderComponent<>(){

        private int priority = 2;

        @Override
        public Integer getValue() {
            final Random random = new Random();
            final int bound = 16; //16 cards;
            return random.nextInt(bound);
        }

        @Override
        public int getPriority() {
            return priority;
        }

        @Override
        public final void setPriority(final int priority) {
            this.priority = priority;
        }
    };

    private ProviderComponent<Integer> thirdProviderComponent = new ProviderComponent<>(){

        private int priority = 3;

        @Override
        public final Integer getValue() {
            final Random random = new Random();
            final int bound = 16; //16 cards;
            return random.nextInt(bound);
        }

        @Override
        public final int getPriority() {
            return priority;
        }

        @Override
        public final void setPriority(final int priority) {
            this.priority = priority;
        }
    };

    private final List<ProviderComponent<Integer>> providerComponentList = new ArrayList<>(){{
        addAll(Arrays.asList(firstProviderComponent, secondProviderComponent, thirdProviderComponent));
    }};

    public final void setDefaultProviderComponent(final int index){
        final ProviderComponent<Integer> providerComponent = getDefaultProviderComponent(providerComponentList
                .get(index).getPriority());
        this.providerComponentList.set(index, providerComponent);
    }

    public final void returnPreviousProviderComponent(final int index
            , final ProviderComponent<Integer> providerComponent){
        providerComponent.setPriority(providerComponentList.get(index).getPriority());
        this.providerComponentList.set(index, providerComponent);
    }

    public final void setCustomProviderComponent(final int index, final ProviderComponent<Integer> providerComponent){
        providerComponent.setPriority(providerComponentList.get(index).getPriority() + 3);
        this.providerComponentList.set(index, providerComponent);
    }

    public final int getAvailableProviderComponent(){
        int index  = 0;
        int i = 0;
        ProviderComponent<Integer> minPriorityProviderComponent = providerComponentList.get(0);
        for (final ProviderComponent<Integer> providerComponent: providerComponentList){
            if (providerComponent.getPriority() <= minPriorityProviderComponent.getPriority()){
                minPriorityProviderComponent = providerComponent;
                index = i;
            }
            i++;
        }
        return index;
    }

    //Interesting getter:
    public final List<ProviderComponent<Integer>> getProviderComponentList() {
        for (final ProviderComponent<Integer> providerComponent :providerComponentList){
            providerComponent.setPriority(providerComponent.getPriority() + 1);
        }
        return providerComponentList;
    }

    private ProviderComponent<Integer> getDefaultProviderComponent(final int inputPriority){
        return new ProviderComponent<>() {

            private int priority = inputPriority;

            @Override
            public final Integer getValue() {
                final Random random = new Random();
                final int bound = 16; //16 cards;
                return random.nextInt(bound);
            }

            @Override
            public final int getPriority() {
                return priority;
            }

            @Override
            public final void setPriority(final int priority) {
                this.priority = priority;
            }
        };
    }
}