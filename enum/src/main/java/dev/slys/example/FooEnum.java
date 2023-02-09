package dev.slys.example;

public enum FooEnum {
    FOO(0), BAR(1), BAZ(2);

    FooEnum(int value) {
        this.value = value;
    }
    private final int value;
    public int toInt() {
        return value;
    }
    public static FooEnum fromInt(int value) {
        return FooEnum.values()[value];
    }
}