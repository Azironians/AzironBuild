package managment.profileManagement;

public final class FictionalProfiles {

    public static Profile createJoe(){
        return new Profile("Joe", 0, null, null, null
                , null, null, null, null);
    }

    public static Profile createPoul(){
        return new Profile("Poul",1,2,30,4,10,10,10
                ,null);
    }
}