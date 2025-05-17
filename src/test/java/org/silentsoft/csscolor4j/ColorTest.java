package org.silentsoft.csscolor4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class ColorTest {

    @Test
    public void rgbSyntaxTest() {
        // https://developer.mozilla.org/en-US/docs/Web/CSS/color_value#rgb_syntax_variations

        /* Hexadecimal syntax */
        assertRGB(Color.valueOf("#f09"), 255, 0, 153);
        assertRGB(Color.valueOf("#F09"), 255, 0, 153);
        assertRGB(Color.valueOf("#ff0099"), 255, 0, 153);
        assertRGB(Color.valueOf("#FF0099"), 255, 0, 153);

        /* Functional syntax */
        assertRGB(Color.valueOf("rgb(255,0,153)"), 255, 0, 153);
        assertRGB(Color.valueOf("rgb(255, 0, 153)"), 255, 0, 153);
        assertRGB(Color.valueOf("rgb(255, 0, 153.0)"), 255, 0, 153);
        assertRGB(Color.valueOf("rgb(100%,0%,60%)"), 255, 0, 153);
        assertRGB(Color.valueOf("rgb(100%, 0%, 60%)"), 255, 0, 153);
        /* ERROR! Don't mix numbers and percentages. */
        Assertions.assertThrows(IllegalArgumentException.class, () -> { Color.valueOf("rgb(100%, 0, 60%)"); });
        assertRGB(Color.valueOf("rgb(255 0 153)"), 255, 0, 153);

        /* Hexadecimal syntax with alpha value */
        assertRGB(Color.valueOf("#f09f"), 255, 0, 153, 1.0);
        assertRGB(Color.valueOf("#F09F"), 255, 0, 153, 1.0);
        assertRGB(Color.valueOf("#ff0099ff"), 255, 0, 153, 1.0);
        assertRGB(Color.valueOf("#FF0099FF"), 255, 0, 153, 1.0);

        /* Functional syntax with alpha value */
        assertRGB(Color.valueOf("rgb(255, 0, 153, 1)"), 255, 0, 153, 1.0);
        assertRGB(Color.valueOf("rgb(255, 0, 153, 100%)"), 255, 0, 153, 1.0);

        /* Whitespace syntax */
        assertRGB(Color.valueOf("rgb(255 0 153 / 1)"), 255, 0, 153, 1.0);
        assertRGB(Color.valueOf("rgb(255 0 153 / 100%)"), 255, 0, 153, 1.0);

        /* Functional syntax with floats value */
        assertRGB(Color.valueOf("rgb(255, 0, 153.6, 1)"), 255, 0, 154, 1.0);
        assertRGB(Color.valueOf("rgb(1e2, .5e1, .5e0, +.25e2%)"), 100, 5, 1, 0.25);
    }

    @Test
    public void rgbTransparencyTest() {
        // https://developer.mozilla.org/en-US/docs/Web/CSS/color_value#rgb_transparency_variations

        /* Hexadecimal syntax */
        assertRGB(Color.valueOf("#3a30"), 51, 170, 51, 0);
        assertRGB(Color.valueOf("#3A3F"), 51, 170, 51, 1);
        assertRGB(Color.valueOf("#33aa3300"), 51, 170, 51, 0);
        assertRGB(Color.valueOf("#33AA3380"), 51, 170, 51, 128.0/255.0/*0.5*/);

        /* Functional syntax */
        assertRGB(Color.valueOf("rgba(51, 170, 51, .1)"), 51, 170, 51, 0.1);
        assertRGB(Color.valueOf("rgba(51, 170, 51, .4)"), 51, 170, 51, 0.4);
        assertRGB(Color.valueOf("rgba(51, 170, 51, .7)"), 51, 170, 51, 0.7);
        assertRGB(Color.valueOf("rgba(51, 170, 51, 1)"), 51, 170, 51, 1);

        /* Whitespace syntax */
        assertRGB(Color.valueOf("rgba(51 170 51 / 0.4)"), 51, 170, 51, 0.4);
        assertRGB(Color.valueOf("rgba(51 170 51 / 40%)"), 51, 170, 51, 0.4);

        /* Functional syntax with floats value */
        assertRGB(Color.valueOf("rgba(255, 0, 153.6, 1)"), 255, 0, 154, 1.0);
        assertRGB(Color.valueOf("rgba(1e2, .5e1, .5e0, +.25e2%)"), 100, 5, 1, 0.25);
    }

    @Test
    public void hslSyntaxTest() {
        // https://developer.mozilla.org/en-US/docs/Web/CSS/color_value#hsl_syntax_variations

        /* These examples all specify the same color: a lavender. */
        assertHSL(Color.valueOf("hsl(270,60%,70%)"), 270, 0.6, 0.7);
        assertHSL(Color.valueOf("hsl(270, 60%, 70%)"), 270, 0.6, 0.7);
        assertHSL(Color.valueOf("hsl(270 60% 70%)"), 270, 0.6, 0.7);
        assertHSL(Color.valueOf("hsl(270deg, 60%, 70%)"), 270, 0.6, 0.7);
        assertHSL(Color.valueOf("hsl(4.71239rad, 60%, 70%)"), 270.000058419654, 0.6, 0.7);
        assertHSL(Color.valueOf("hsl(300grad, 60%, 70%)"), 270, 0.6, 0.7);
        assertHSL(Color.valueOf("hsl(.75turn, 60%, 70%)"), 270, 0.6, 0.7);

        /* These examples all specify the same color: a lavender that is 15% opaque. */
        assertHSL(Color.valueOf("hsl(270, 60%, 50%, .15)"), 270, 0.6, 0.5, 0.15);
        assertHSL(Color.valueOf("hsl(270, 60%, 50%, 15%)"), 270, 0.6, 0.5, 0.15);
        assertHSL(Color.valueOf("hsl(270 60% 50% / .15)"), 270, 0.6, 0.5, 0.15);
        assertHSL(Color.valueOf("hsl(270 60% 50% / 15%)"), 270, 0.6, 0.5, 0.15);
    }

    @Test
    public void hslRoundingTest() {
        Assertions.assertEquals("#c678dd", Color.valueOf("hsl(286, 60%, 67%)").getHex());
    }

    @Test
    public void cmykTest() {
        Color cyan = Color.valueOf("cmyk(1, 0, 0, 0)");
        assertCMYK(cyan, 1, 0, 0, 0);
        assertRGB(cyan, 0, 255, 255);
        assertHSL(cyan, 180, 1, 0.5);
        assertHex(cyan, "#00ffff");

        Color magenta = Color.valueOf("cmyk(0, 1, 0, 0)");
        assertCMYK(magenta, 0, 1, 0, 0);
        assertRGB(magenta, 255, 0, 255);
        assertHSL(magenta, 300, 1, 0.5);
        assertHex(magenta, "#ff00ff");

        Color yellow = Color.valueOf("cmyk(0, 0, 1, 0)");
        assertCMYK(yellow, 0, 0, 1, 0);
        assertRGB(yellow, 255, 255, 0);
        assertHSL(yellow, 60, 1, 0.5);
        assertHex(yellow, "#ffff00");

        Color black = Color.valueOf("cmyk(0, 0, 0, 1)");
        assertCMYK(black, 0, 0, 0, 1);
        assertRGB(black, 0, 0, 0);
        assertHSL(black, 0, 0, 0);
        assertHex(black, "#000000");
    }

    @Test
    public void hexTest() {
        Assertions.assertEquals("#ffffff", Color.valueOf("rgb(255, 255, 255)").getHex());
        Assertions.assertEquals("#ffffff", Color.valueOf("rgba(255, 255, 255, 1.0)").getHex());
        Assertions.assertEquals("#ffffff80", Color.valueOf("rgba(255, 255, 255, 0.5)").getHex());

        Assertions.assertEquals("#9cf", Color.valueOf("9cf").getHex());
        Assertions.assertEquals("#9cf", Color.valueOf("#9cf").getHex());

        Assertions.assertEquals("#ff69b4", Color.valueOf("ff69b4").getHex());
        Assertions.assertEquals("#ff69b4", Color.valueOf("#ff69b4").getHex());
    }

    @Test
    public void transparentTest() {
        Assertions.assertEquals(Color.valueOf("transparent"), Color.rgb(0, 0, 0, 0));
    }

    @Test
    public void equalityTest() {
        Assertions.assertEquals(Color.valueOf("white"), Color.valueOf("rgb(255, 255, 255)"));

        Assertions.assertEquals(Color.valueOf("#9cf"), Color.valueOf("9cf"));
        Assertions.assertEquals(Color.valueOf("#9cf"), Color.valueOf("#99ccff"));

        Assertions.assertEquals(Color.valueOf("#ff69b4"), Color.valueOf("ff69b4"));

        Assertions.assertEquals(Color.hex("#010203"), Color.valueOf("rgb(1, 2, 3)"));
        Assertions.assertEquals(Color.rgb(1, 2, 3), Color.valueOf("rgb(1, 2, 3)"));
        Assertions.assertEquals(Color.rgb(1, 2, 3, 0.4), Color.valueOf("rgba(1, 2, 3, 0.4)"));
        Assertions.assertEquals(Color.hsl(1, 0.2, 0.3), Color.valueOf("hsl(1, .2, .3)"));
        Assertions.assertEquals(Color.hsl(1, 0.2, 0.3, 0.4), Color.valueOf("hsla(1, .2, .3, .4)"));
    }

    @Test
    public void identityTest() {
        HashMap<Color, Integer> map = new HashMap<Color, Integer>();
        map.put(Color.valueOf("white"), 1);
        map.put(Color.valueOf("white"), 2);
        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals(2, map.get(Color.valueOf("white")));
    }

    @Test
    public void namedColorTest() {
        Assertions.assertEquals(Color.BLACK, Color.valueOf("black"));
        Assertions.assertEquals(Color.SILVER, Color.valueOf("silver"));
        Assertions.assertEquals(Color.GRAY, Color.valueOf("gray"));
        Assertions.assertEquals(Color.WHITE, Color.valueOf("white"));
        Assertions.assertEquals(Color.MAROON, Color.valueOf("maroon"));
        Assertions.assertEquals(Color.RED, Color.valueOf("red"));
        Assertions.assertEquals(Color.PURPLE, Color.valueOf("purple"));
        Assertions.assertEquals(Color.FUCHSIA, Color.valueOf("fuchsia"));
        Assertions.assertEquals(Color.GREEN, Color.valueOf("green"));
        Assertions.assertEquals(Color.LIME, Color.valueOf("lime"));
        Assertions.assertEquals(Color.OLIVE, Color.valueOf("olive"));
        Assertions.assertEquals(Color.YELLOW, Color.valueOf("yellow"));
        Assertions.assertEquals(Color.NAVY, Color.valueOf("navy"));
        Assertions.assertEquals(Color.BLUE, Color.valueOf("blue"));
        Assertions.assertEquals(Color.TEAL, Color.valueOf("teal"));
        Assertions.assertEquals(Color.AQUA, Color.valueOf("aqua"));
        Assertions.assertEquals(Color.ORANGE, Color.valueOf("orange"));
        Assertions.assertEquals(Color.ALICEBLUE, Color.valueOf("aliceblue"));
        Assertions.assertEquals(Color.ANTIQUEWHITE, Color.valueOf("antiquewhite"));
        Assertions.assertEquals(Color.AQUAMARINE, Color.valueOf("aquamarine"));
        Assertions.assertEquals(Color.AZURE, Color.valueOf("azure"));
        Assertions.assertEquals(Color.BEIGE, Color.valueOf("beige"));
        Assertions.assertEquals(Color.BISQUE, Color.valueOf("bisque"));
        Assertions.assertEquals(Color.BLANCHEDALMOND, Color.valueOf("blanchedalmond"));
        Assertions.assertEquals(Color.BLUEVIOLET, Color.valueOf("blueviolet"));
        Assertions.assertEquals(Color.BROWN, Color.valueOf("brown"));
        Assertions.assertEquals(Color.BURLYWOOD, Color.valueOf("burlywood"));
        Assertions.assertEquals(Color.CADETBLUE, Color.valueOf("cadetblue"));
        Assertions.assertEquals(Color.CHARTREUSE, Color.valueOf("chartreuse"));
        Assertions.assertEquals(Color.CHOCOLATE, Color.valueOf("chocolate"));
        Assertions.assertEquals(Color.CORAL, Color.valueOf("coral"));
        Assertions.assertEquals(Color.CORNFLOWERBLUE, Color.valueOf("cornflowerblue"));
        Assertions.assertEquals(Color.CORNSILK, Color.valueOf("cornsilk"));
        Assertions.assertEquals(Color.CRIMSON, Color.valueOf("crimson"));
        Assertions.assertEquals(Color.CYAN, Color.valueOf("cyan"));
        Assertions.assertEquals(Color.DARKBLUE, Color.valueOf("darkblue"));
        Assertions.assertEquals(Color.DARKCYAN, Color.valueOf("darkcyan"));
        Assertions.assertEquals(Color.DARKGOLDENROD, Color.valueOf("darkgoldenrod"));
        Assertions.assertEquals(Color.DARKGRAY, Color.valueOf("darkgray"));
        Assertions.assertEquals(Color.DARKGREEN, Color.valueOf("darkgreen"));
        Assertions.assertEquals(Color.DARKGREY, Color.valueOf("darkgrey"));
        Assertions.assertEquals(Color.DARKKHAKI, Color.valueOf("darkkhaki"));
        Assertions.assertEquals(Color.DARKMAGENTA, Color.valueOf("darkmagenta"));
        Assertions.assertEquals(Color.DARKOLIVEGREEN, Color.valueOf("darkolivegreen"));
        Assertions.assertEquals(Color.DARKORANGE, Color.valueOf("darkorange"));
        Assertions.assertEquals(Color.DARKORCHID, Color.valueOf("darkorchid"));
        Assertions.assertEquals(Color.DARKRED, Color.valueOf("darkred"));
        Assertions.assertEquals(Color.DARKSALMON, Color.valueOf("darksalmon"));
        Assertions.assertEquals(Color.DARKSEAGREEN, Color.valueOf("darkseagreen"));
        Assertions.assertEquals(Color.DARKSLATEBLUE, Color.valueOf("darkslateblue"));
        Assertions.assertEquals(Color.DARKSLATEGRAY, Color.valueOf("darkslategray"));
        Assertions.assertEquals(Color.DARKSLATEGREY, Color.valueOf("darkslategrey"));
        Assertions.assertEquals(Color.DARKTURQUOISE, Color.valueOf("darkturquoise"));
        Assertions.assertEquals(Color.DARKVIOLET, Color.valueOf("darkviolet"));
        Assertions.assertEquals(Color.DEEPPINK, Color.valueOf("deeppink"));
        Assertions.assertEquals(Color.DEEPSKYBLUE, Color.valueOf("deepskyblue"));
        Assertions.assertEquals(Color.DIMGRAY, Color.valueOf("dimgray"));
        Assertions.assertEquals(Color.DIMGREY, Color.valueOf("dimgrey"));
        Assertions.assertEquals(Color.DODGERBLUE, Color.valueOf("dodgerblue"));
        Assertions.assertEquals(Color.FIREBRICK, Color.valueOf("firebrick"));
        Assertions.assertEquals(Color.FLORALWHITE, Color.valueOf("floralwhite"));
        Assertions.assertEquals(Color.FORESTGREEN, Color.valueOf("forestgreen"));
        Assertions.assertEquals(Color.GAINSBORO, Color.valueOf("gainsboro"));
        Assertions.assertEquals(Color.GHOSTWHITE, Color.valueOf("ghostwhite"));
        Assertions.assertEquals(Color.GOLD, Color.valueOf("gold"));
        Assertions.assertEquals(Color.GOLDENROD, Color.valueOf("goldenrod"));
        Assertions.assertEquals(Color.GREENYELLOW, Color.valueOf("greenyellow"));
        Assertions.assertEquals(Color.GREY, Color.valueOf("grey"));
        Assertions.assertEquals(Color.HONEYDEW, Color.valueOf("honeydew"));
        Assertions.assertEquals(Color.HOTPINK, Color.valueOf("hotpink"));
        Assertions.assertEquals(Color.INDIANRED, Color.valueOf("indianred"));
        Assertions.assertEquals(Color.INDIGO, Color.valueOf("indigo"));
        Assertions.assertEquals(Color.IVORY, Color.valueOf("ivory"));
        Assertions.assertEquals(Color.KHAKI, Color.valueOf("khaki"));
        Assertions.assertEquals(Color.LAVENDER, Color.valueOf("lavender"));
        Assertions.assertEquals(Color.LAVENDERBLUSH, Color.valueOf("lavenderblush"));
        Assertions.assertEquals(Color.LAWNGREEN, Color.valueOf("lawngreen"));
        Assertions.assertEquals(Color.LEMONCHIFFON, Color.valueOf("lemonchiffon"));
        Assertions.assertEquals(Color.LIGHTBLUE, Color.valueOf("lightblue"));
        Assertions.assertEquals(Color.LIGHTCORAL, Color.valueOf("lightcoral"));
        Assertions.assertEquals(Color.LIGHTCYAN, Color.valueOf("lightcyan"));
        Assertions.assertEquals(Color.LIGHTGOLDENRODYELLOW, Color.valueOf("lightgoldenrodyellow"));
        Assertions.assertEquals(Color.LIGHTGRAY, Color.valueOf("lightgray"));
        Assertions.assertEquals(Color.LIGHTGREEN, Color.valueOf("lightgreen"));
        Assertions.assertEquals(Color.LIGHTGREY, Color.valueOf("lightgrey"));
        Assertions.assertEquals(Color.LIGHTPINK, Color.valueOf("lightpink"));
        Assertions.assertEquals(Color.LIGHTSALMON, Color.valueOf("lightsalmon"));
        Assertions.assertEquals(Color.LIGHTSEAGREEN, Color.valueOf("lightseagreen"));
        Assertions.assertEquals(Color.LIGHTSKYBLUE, Color.valueOf("lightskyblue"));
        Assertions.assertEquals(Color.LIGHTSLATEGRAY, Color.valueOf("lightslategray"));
        Assertions.assertEquals(Color.LIGHTSLATEGREY, Color.valueOf("lightslategrey"));
        Assertions.assertEquals(Color.LIGHTSTEELBLUE, Color.valueOf("lightsteelblue"));
        Assertions.assertEquals(Color.LIGHTYELLOW, Color.valueOf("lightyellow"));
        Assertions.assertEquals(Color.LIMEGREEN, Color.valueOf("limegreen"));
        Assertions.assertEquals(Color.LINEN, Color.valueOf("linen"));
        Assertions.assertEquals(Color.MAGENTA, Color.valueOf("magenta"));
        Assertions.assertEquals(Color.MEDIUMAQUAMARINE, Color.valueOf("mediumaquamarine"));
        Assertions.assertEquals(Color.MEDIUMBLUE, Color.valueOf("mediumblue"));
        Assertions.assertEquals(Color.MEDIUMORCHID, Color.valueOf("mediumorchid"));
        Assertions.assertEquals(Color.MEDIUMPURPLE, Color.valueOf("mediumpurple"));
        Assertions.assertEquals(Color.MEDIUMSEAGREEN, Color.valueOf("mediumseagreen"));
        Assertions.assertEquals(Color.MEDIUMSLATEBLUE, Color.valueOf("mediumslateblue"));
        Assertions.assertEquals(Color.MEDIUMSPRINGGREEN, Color.valueOf("mediumspringgreen"));
        Assertions.assertEquals(Color.MEDIUMTURQUOISE, Color.valueOf("mediumturquoise"));
        Assertions.assertEquals(Color.MEDIUMVIOLETRED, Color.valueOf("mediumvioletred"));
        Assertions.assertEquals(Color.MIDNIGHTBLUE, Color.valueOf("midnightblue"));
        Assertions.assertEquals(Color.MINTCREAM, Color.valueOf("mintcream"));
        Assertions.assertEquals(Color.MISTYROSE, Color.valueOf("mistyrose"));
        Assertions.assertEquals(Color.MOCCASIN, Color.valueOf("moccasin"));
        Assertions.assertEquals(Color.NAVAJOWHITE, Color.valueOf("navajowhite"));
        Assertions.assertEquals(Color.OLDLACE, Color.valueOf("oldlace"));
        Assertions.assertEquals(Color.OLIVEDRAB, Color.valueOf("olivedrab"));
        Assertions.assertEquals(Color.ORANGERED, Color.valueOf("orangered"));
        Assertions.assertEquals(Color.ORCHID, Color.valueOf("orchid"));
        Assertions.assertEquals(Color.PALEGOLDENROD, Color.valueOf("palegoldenrod"));
        Assertions.assertEquals(Color.PALEGREEN, Color.valueOf("palegreen"));
        Assertions.assertEquals(Color.PALETURQUOISE, Color.valueOf("paleturquoise"));
        Assertions.assertEquals(Color.PALEVIOLETRED, Color.valueOf("palevioletred"));
        Assertions.assertEquals(Color.PAPAYAWHIP, Color.valueOf("papayawhip"));
        Assertions.assertEquals(Color.PEACHPUFF, Color.valueOf("peachpuff"));
        Assertions.assertEquals(Color.PERU, Color.valueOf("peru"));
        Assertions.assertEquals(Color.PINK, Color.valueOf("pink"));
        Assertions.assertEquals(Color.PLUM, Color.valueOf("plum"));
        Assertions.assertEquals(Color.POWDERBLUE, Color.valueOf("powderblue"));
        Assertions.assertEquals(Color.ROSYBROWN, Color.valueOf("rosybrown"));
        Assertions.assertEquals(Color.ROYALBLUE, Color.valueOf("royalblue"));
        Assertions.assertEquals(Color.SADDLEBROWN, Color.valueOf("saddlebrown"));
        Assertions.assertEquals(Color.SALMON, Color.valueOf("salmon"));
        Assertions.assertEquals(Color.SANDYBROWN, Color.valueOf("sandybrown"));
        Assertions.assertEquals(Color.SEAGREEN, Color.valueOf("seagreen"));
        Assertions.assertEquals(Color.SEASHELL, Color.valueOf("seashell"));
        Assertions.assertEquals(Color.SIENNA, Color.valueOf("sienna"));
        Assertions.assertEquals(Color.SKYBLUE, Color.valueOf("skyblue"));
        Assertions.assertEquals(Color.SLATEBLUE, Color.valueOf("slateblue"));
        Assertions.assertEquals(Color.SLATEGRAY, Color.valueOf("slategray"));
        Assertions.assertEquals(Color.SLATEGREY, Color.valueOf("slategrey"));
        Assertions.assertEquals(Color.SNOW, Color.valueOf("snow"));
        Assertions.assertEquals(Color.SPRINGGREEN, Color.valueOf("springgreen"));
        Assertions.assertEquals(Color.STEELBLUE, Color.valueOf("steelblue"));
        Assertions.assertEquals(Color.TAN, Color.valueOf("tan"));
        Assertions.assertEquals(Color.THISTLE, Color.valueOf("thistle"));
        Assertions.assertEquals(Color.TOMATO, Color.valueOf("tomato"));
        Assertions.assertEquals(Color.TURQUOISE, Color.valueOf("turquoise"));
        Assertions.assertEquals(Color.VIOLET, Color.valueOf("violet"));
        Assertions.assertEquals(Color.WHEAT, Color.valueOf("wheat"));
        Assertions.assertEquals(Color.WHITESMOKE, Color.valueOf("whitesmoke"));
        Assertions.assertEquals(Color.YELLOWGREEN, Color.valueOf("yellowgreen"));
        Assertions.assertEquals(Color.REBECCAPURPLE, Color.valueOf("rebeccapurple"));
    }

    private void assertRGB(Color color, int red, int green, int blue) {
        assertRGB(color, red, green, blue, 1.0);
    }

    private void assertRGB(Color color, int red, int green, int blue, double opacity) {
        Assertions.assertEquals(color, Color.rgb(red, green, blue, opacity));

        Assertions.assertEquals(red, color.getRed());
        Assertions.assertEquals(green, color.getGreen());
        Assertions.assertEquals(blue, color.getBlue());
        Assertions.assertEquals(opacity, color.getOpacity());
    }

    private void assertCMYK(Color color, double cyan, double magenta, double yellow, double black) {
        assertCMYK(color, cyan, magenta, yellow, black, 1.0);
    }

    private void assertCMYK(Color color, double cyan, double magenta, double yellow, double black, double opacity) {
        Assertions.assertEquals(color, Color.cmyk(cyan, magenta, yellow, black, opacity));

        Assertions.assertEquals(cyan, color.getCyan());
        Assertions.assertEquals(magenta, color.getMagenta());
        Assertions.assertEquals(yellow, color.getYellow());
        Assertions.assertEquals(black, color.getBlack());
        Assertions.assertEquals(opacity, color.getOpacity());
    }

    private void assertHSL(Color color, double hue, double saturation, double lightness) {
        assertHSL(color, hue, saturation, lightness, 1.0);
    }

    private void assertHSL(Color color, double hue, double saturation, double lightness, double opacity) {
        Assertions.assertEquals(color, Color.hsl(hue, saturation, lightness, opacity));

        Assertions.assertEquals(hue, color.getHue());
        Assertions.assertEquals(saturation, color.getSaturation());
        Assertions.assertEquals(lightness, color.getLightness());
        Assertions.assertEquals(opacity, color.getOpacity());
    }

    private void assertHex(Color color, String hex) {
        Assertions.assertEquals(color, Color.hex(hex));

        Assertions.assertEquals(hex, color.getHex());
    }

}
