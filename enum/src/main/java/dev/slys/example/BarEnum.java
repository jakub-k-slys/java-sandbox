package dev.slys.example;

public enum BarEnum {
    FOO(0), BAR(1), BAZ(2);

    BarEnum(int value) {
        this.value = value;
    }
    private final int value;
    private static final BarEnum[] LOOKUP = BarEnum.values();
    public int toInt() {
        return value;
    }
    public static BarEnum fromInt(int value) {
        return LOOKUP[value];
    }
}
