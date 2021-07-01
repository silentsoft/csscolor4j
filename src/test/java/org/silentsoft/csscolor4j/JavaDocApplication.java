package org.silentsoft.csscolor4j;

public class JavaDocApplication {

    public static void main(String[] args) {
        System.out.println("This enum defines the named colors from CSS level 1 to CSS level 4.");
        System.out.println();
        System.out.println("<table summary=\"Supported colors\">");
        System.out.println("  <tr>");
        System.out.println("    <th></th>");
        System.out.println("    <th>Name</th>");
        System.out.println("    <th>Hex value</th>");
        System.out.println("  </tr>");
        for (NamedColor namedColor : NamedColor.values()) {
            System.out.println("  <tr>");
            System.out.println(String.format("    <td><div style=\"width:10px;height:10px;background-color:%s;\"></div></td>", fill(namedColor.getHex())));
            System.out.println(String.format("    <td>%s</td>", namedColor.name().toLowerCase()));
            System.out.println(String.format("    <td>%s</td>", namedColor.getHex()));
            System.out.println("  </tr>");
        }
        System.out.println("</table>");
    }

    private static String fill(String hex) {
        hex = hex.replaceAll("#", "").trim();
        if (hex.length() == 3 || hex.length() == 4) {
            String value = "";
            for (char letter : hex.toCharArray()) {
                value = value.concat(String.valueOf(new char[] { letter, letter }));
            }
            return "#".concat(value);
        }
        return "#".concat(hex);
    }

}
