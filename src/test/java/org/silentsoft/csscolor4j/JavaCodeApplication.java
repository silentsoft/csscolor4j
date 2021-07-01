package org.silentsoft.csscolor4j;

public class JavaCodeApplication {

    public static void main(String[] args) throws Exception {
        for (NamedColor namedColor : NamedColor.values()) {
            System.out.println(String.format("    public static final Color %s = hex(NamedColor.%s.getHex());", namedColor.name(), namedColor.name()));
        }
    }

}
