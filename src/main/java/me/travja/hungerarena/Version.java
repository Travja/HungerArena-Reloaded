package me.travja.hungerarena;

public enum Version {
    LEGACY(15d),
    V2BETA(20.0),
    V2(20.01);

    private double age;

    Version(Double age) {
        this.age = age;
    }

    public double getAge() {
        return this.age;
    }

    public static Version getVersion(String s) {
        switch (s) {
            case "2.0-Beta":
                return V2BETA;
            case "2.0":
                return V2;
            default:
                return LEGACY;
        }
    }
}
