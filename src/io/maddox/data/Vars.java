package io.maddox.data;

public class Vars {


    private static Vars vars;

    public static Vars get() {
        return vars == null? vars = new Vars() : vars;
    }

    public static void reset() {
        vars = new Vars();
    }

    public boolean checkedFragments = false;
    public boolean chargedFragments = false;
}
