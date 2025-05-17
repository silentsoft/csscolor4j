package org.silentsoft.csscolor4j;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

public class Color {

    private int red;
    private int green;
    private int blue;

    private double cyan;
    private double magenta;
    private double yellow;
    private double black;

    private double hue;
    private double saturation;
    private double lightness;

    private double opacity;

    private String hex;

    private Color() {

    }

    /**
     * @param red 0 to 255.
     * @param green 0 to 255.
     * @param blue 0 to 255.
     * @return a <code>Color</code> object that contains color information such as red, green, blue, cyan, magenta, yellow, black, hue, saturation, lightness, opacity, and hexadecimal numbers
     * @see #valueOf(String)
     */
    public static Color rgb(int red, int green, int blue) {
        return rgb(red, green, blue, 1.0);
    }

    /**
     * @param red 0 to 255.
     * @param green 0 to 255.
     * @param blue 0 to 255.
     * @param opacity 0.0 to 1.0.
     * @return a <code>Color</code> object that contains color information such as red, green, blue, cyan, magenta, yellow, black, hue, saturation, lightness, opacity, and hexadecimal numbers
     * @see #valueOf(String)
     */
    public static Color rgb(int red, int green, int blue, double opacity) {
        Color color = new Color();

        color.red = red;
        color.green = green;
        color.blue = blue;
        color.opacity = opacity;

        {
            double r = red / 255.0;
            double g = green / 255.0;
            double b = blue / 255.0;

            double max = Math.max(Math.max(r, g), b);
            double min = Math.min(Math.min(r, g), b);
            double delta = max - min;

            {
                color.lightness = (max + min) / 2;
                color.saturation = (delta == 0) ? 0 : delta / (1 - Math.abs((2 * color.lightness) - 1));
                if (delta == 0) {
                    color.hue = 0;
                } else if (max == r) {
                    color.hue = 60 * (((g - b) / delta) + 0);
                } else if (max == g) {
                    color.hue = 60 * (((b - r) / delta) + 2);
                } else if (max == b) {
                    color.hue = 60 * (((r - g) / delta) + 4);
                }

                color.hue = color.hue < 0 ? color.hue + 360 : color.hue > 360 ? 360 : color.hue;
            }
            {
                color.black = 1 - max;
                if (color.black == 1 && max == min) {
                    color.cyan = 0;
                    color.magenta = 0;
                    color.yellow = 0;
                } else {
                    color.cyan = (1 - r - color.black) / (1 - color.black);
                    color.magenta = (1 - g - color.black) / (1 - color.black);
                    color.yellow = (1 - b - color.black) / (1 - color.black);
                }
            }
        }

        if (opacity == 1) {
            color.hex = String.format(Locale.ROOT, "#%02x%02x%02x", red, green, blue);
        } else {
            color.hex = String.format(Locale.ROOT, "#%02x%02x%02x%02x", red, green, blue,
                    (int) Math.round(opacity * 255));
        }

        return color;
    }

    /**
     *
     * @param cyan 0.0 to 1.0.
     * @param magenta 0.0 to 1.0.
     * @param yellow 0.0 to 1.0.
     * @param black 0.0 to 1.0.
     * @return a <code>Color</code> object that contains color information such as red, green, blue, cyan, magenta, yellow, black, hue, saturation, lightness, opacity, and hexadecimal numbers
     * @see #valueOf(String)
     */
    public static Color cmyk(double cyan, double magenta, double yellow, double black) {
        return cmyk(cyan, magenta, yellow, black, 1.0);
    }

    /**
     * @param cyan 0.0 to 1.0.
     * @param magenta 0.0 to 1.0.
     * @param yellow 0.0 to 1.0.
     * @param black 0.0 to 1.0.
     * @param opacity 0.0 to 1.0.
     * @return a <code>Color</code> object that contains color information such as red, green, blue, cyan, magenta, yellow, black, hue, saturation, lightness, opacity, and hexadecimal numbers
     * @see #valueOf(String)
     */
    public static Color cmyk(double cyan, double magenta, double yellow, double black, double opacity) {
        int red = (int)Math.round(255 * (1 - cyan) * (1 - black));
        int green = (int)Math.round(255 * (1 - magenta) * (1 - black));
        int blue = (int)Math.round(255 * (1 - yellow) * (1 - black));

        Color color = rgb(red, green, blue, opacity);
        color.cyan = cyan;
        color.magenta = magenta;
        color.yellow = yellow;
        color.black = black;

        return color;
    }

    /**
     * @param hue 0 to 360.
     * @param saturation 0.0 to 1.0.
     * @param lightness 0.0 to 1.0.
     * @return a <code>Color</code> object that contains color information such as red, green, blue, cyan, magenta, yellow, black, hue, saturation, lightness, opacity, and hexadecimal numbers
     * @see #valueOf(String)
     */
    public static Color hsl(double hue, double saturation, double lightness) {
        return hsl(hue, saturation, lightness, 1.0);
    }

    /**
     * @param hue 0 to 360.
     * @param saturation 0.0 to 1.0.
     * @param lightness 0.0 to 1.0.
     * @param opacity 0.0 to 1.0.
     * @return a <code>Color</code> object that contains color information such as red, green, blue, cyan, magenta, yellow, black, hue, saturation, lightness, opacity, and hexadecimal numbers
     * @see #valueOf(String)
     */
    public static Color hsl(double hue, double saturation, double lightness, double opacity) {
        double _c = (1 - Math.abs((2 * lightness) - 1)) * saturation;
        double _h = hue / 60;
        double _x = _c * (1 - Math.abs((_h % 2) - 1));
        double[] _rgb = new double[]{ 0, 0, 0 };
        if (_h >= 0 && _h < 1) {
            _rgb = new double[]{ _c, _x, 0 };
        } else if (_h >= 1 && _h < 2) {
            _rgb = new double[]{ _x, _c, 0 };
        } else if (_h >= 2 && _h < 3) {
            _rgb = new double[]{ 0, _c, _x };
        } else if (_h >= 3 && _h < 4) {
            _rgb = new double[]{ 0, _x, _c };
        } else if (_h >= 4 && _h < 5) {
            _rgb = new double[]{ _x, 0, _c };
        } else if (_h >= 5 && _h < 6) {
            _rgb = new double[]{ _c, 0, _x };
        }
        double _m = lightness - (_c / 2);

        int red = (int) Math.round((_rgb[0] + _m) * 255);
        int green = (int) Math.round((_rgb[1] + _m) * 255);
        int blue = (int) Math.round((_rgb[2] + _m) * 255);

        Color color = rgb(red, green, blue, opacity);
        color.hue = hue;
        color.saturation = saturation;
        color.lightness = lightness;

        return color;
    }

    /**
     * @param value the string to convert
     * @return a <code>Color</code> object that contains color information such as red, green, blue, cyan, magenta, yellow, black, hue, saturation, lightness, opacity, and hexadecimal numbers
     * @see #valueOf(String)
     */
    public static Color hex(String value) {
        value = value.trim();
        String hex = value.startsWith("#") ? value.substring(1) : value;
        String filledHex = fill(hex);

        int red = Integer.parseInt(filledHex.substring(0, 2), 16);
        int green = Integer.parseInt(filledHex.substring(2, 4), 16);
        int blue = Integer.parseInt(filledHex.substring(4, 6), 16);

        double opacity = 1.0;
        if (filledHex.length() == 8) {
            opacity = Integer.parseInt(filledHex.substring(6, 8), 16) / 255.0;
        }

        Color color = rgb(red, green, blue, opacity);
        color.hex = "#".concat(hex);

        return color;
    }

    /**
     * Creates a color from a string representation.<br>
     *
     * Supported formats are:
     * <ul>
     *     <li><code>rgb[a](red, green, blue[, opacity])</code></li>
     *     <li><code>cmyk[a](cyan, magenta, yellow, black[, opacity])</code></li>
     *     <li><code>hsl[a](hue, saturation, lightness[, opacity])</code></li>
     *     <li>{@link NamedColor named color}</li>
     *     <li>hexadecimal numbers</li>
     * </ul>
     *
     * Examples:
     * <pre>
     * Color.valueOf("rgb(255, 0, 153)");
     * Color.valueOf("rgb(100%, 0%, 60%)");
     * Color.valueOf("rgb(255 0 153)");
     * Color.valueOf("rgb(255, 0, 153, 1)");
     * Color.valueOf("rgb(255, 0, 153, 100%)");
     * Color.valueOf("rgb(255 0 153 / 1)");
     * Color.valueOf("rgb(255 0 153 / 100%)");
     * Color.valueOf("rgb(1e2, .5e1, .5e0, +.25e2%)");
     *
     * Color.valueOf("rgba(51, 170, 51, .5)");
     *
     * Color.valueOf("cmyk(1, 0, 0, 0)");
     *
     * Color.valueOf("hsl(270, 60%, 70%)");
     * Color.valueOf("hsl(270deg, 60%, 70%)");
     * Color.valueOf("hsl(4.71239rad, 60%, 70%)");
     * Color.valueOf("hsl(300grad, 60%, 70%)");
     * Color.valueOf("hsl(.75turn, 60%, 70%)");
     *
     * Color.valueOf("black");
     * Color.valueOf("rebeccapurple");
     *
     * Color.valueOf("#f09");
     * Color.valueOf("#ff0099");
     * Color.valueOf("#ff0099ff");
     * </pre>
     *
     * @param value the string to convert
     * @return a <code>Color</code> object that contains color information such as red, green, blue, cyan, magenta, yellow, black, hue, saturation, lightness, opacity, and hexadecimal numbers
     * @throws IllegalArgumentException if the given string value cannot be recognized as <code>rgb</code>, <code>cmyk</code>, <code>hsl</code>, {@link NamedColor named color} or hexadecimal numbers
     * @see #rgb(int, int, int)
     * @see #rgb(int, int, int, double)
     * @see #cmyk(double, double, double, double)
     * @see #cmyk(double, double, double, double, double)
     * @see #hsl(double, double, double)
     * @see #hsl(double, double, double, double)
     * @see org.silentsoft.csscolor4j.NamedColor
     * @see #hex(String)
     */
    public static Color valueOf(String value) throws IllegalArgumentException {
        value = value.trim().toLowerCase(Locale.ROOT);

        if (value.contains("rgb")) {
            String[] rgb = split(value);
            if (rgb[0].contains("%") || rgb[1].contains("%") || rgb[2].contains("%")) {
                boolean makeSense = rgb[0].contains("%") && rgb[1].contains("%") && rgb[2].contains("%");
                if (!makeSense) {
                    throw new IllegalArgumentException("Cannot mix numbers and percentages in RGB calculations.");
                }
            }
            int red = parseInt(rgb[0], 255);
            int green = parseInt(rgb[1], 255);
            int blue = parseInt(rgb[2], 255);
            double opacity = rgb.length >= 4 ? parseDouble(rgb[3], 1) : 1.0;

            return rgb(red, green, blue, opacity);
        } else if (value.contains("cmyk")) {
            String[] cmyk = split(value);
            double cyan = parseDouble(cmyk[0], 1);
            double magenta = parseDouble(cmyk[1], 1);
            double yellow = parseDouble(cmyk[2], 1);
            double black = parseDouble(cmyk[3], 1);
            double opacity = cmyk.length >= 5 ? parseDouble(cmyk[4], 1) : 1.0;

            return cmyk(cyan, magenta, yellow, black, opacity);
        } else if (value.contains("hsl")) {
            String[] hsl = split(value);
            double hue = toDegrees(hsl[0]);
            double saturation = parseDouble(hsl[1], 1);
            double lightness = parseDouble(hsl[2], 1);
            double opacity = hsl.length >= 4 ? parseDouble(hsl[3], 1) : 1.0;

            return hsl(hue, saturation, lightness, opacity);
        } else if (NamedColor.nameOf(value) != null) {
            return hex(NamedColor.nameOf(value).getHex());
        } else if ("transparent".equals(value)) {
            return rgb(0, 0, 0, 0);
        } else if (value.startsWith("#") || value.length() == 3 || value.length() == 4 ||  value.length() == 6 || value.length() == 8) {
            /* This else if statement must be at the end. */
            return hex(value);
        }

        throw new IllegalArgumentException(value + " cannot be recognized.");
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public double getCyan() {
        return cyan;
    }

    public double getMagenta() {
        return magenta;
    }

    public double getYellow() {
        return yellow;
    }

    public double getBlack() {
        return black;
    }

    public double getHue() {
        return hue;
    }

    public double getSaturation() {
        return saturation;
    }

    public double getLightness() {
        return lightness;
    }

    public double getOpacity() {
        return opacity;
    }

    public String getHex() {
        return hex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return red == color.red &&
                green == color.green &&
                blue == color.blue &&
                Double.compare(color.cyan, cyan) == 0 &&
                Double.compare(color.magenta, magenta) == 0 &&
                Double.compare(color.yellow, yellow) == 0 &&
                Double.compare(color.black, black) == 0 &&
                Double.compare(color.hue, hue) == 0 &&
                Double.compare(color.saturation, saturation) == 0 &&
                Double.compare(color.lightness, lightness) == 0 &&
                Double.compare(color.opacity, opacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue, cyan, magenta, yellow, black, hue, saturation, lightness, opacity);
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", cyan=" + cyan +
                ", magenta=" + magenta +
                ", yellow=" + yellow +
                ", black=" + black +
                ", hue=" + hue +
                ", saturation=" + saturation +
                ", lightness=" + lightness +
                ", opacity=" + opacity +
                ", hex='" + hex + '\'' +
                '}';
    }

    private static String fill(String hex) {
        if (hex.length() == 3 || hex.length() == 4) {
            String value = "";
            for (char letter : hex.toCharArray()) {
                value = value.concat(String.valueOf(new char[] { letter, letter }));
            }
            return value;
        }
        return hex;
    }

    private static double parseDouble(String value, double limit) {
        boolean hasPercent = value.contains("%");
        value = value.replace("%", "").trim();
        double number = Double.parseDouble(value);
        return hasPercent ? BigDecimal.valueOf(number).multiply(BigDecimal.valueOf((limit / 100.0))).doubleValue() : number;
    }

    private static int parseInt(String value, double limit) {
        double number = parseDouble(value, limit);
        int round = (int) Math.round(number);
        return round > limit ? (int) limit : round;
    }

    private static String[] split(String value) {
        value = value.replace("/", " ");
        value = value.replaceAll("(\\s)+", " ");
        value = value.substring(value.indexOf("(")+1, value.indexOf(")"));
        return value.contains(",") ? value.split(",") : value.split(" ");
    }

    private static double toDegrees(String value) {
        value = value.toLowerCase(Locale.ROOT).trim();
        if (value.contains("deg")) {
            return Double.parseDouble(value.replace("deg", "").trim());
        } else if (value.contains("grad")) {
            return (Double.parseDouble(value.replace("grad", "").trim()) / 400.0) * 360.0;
        } else if (value.contains("rad")) {
            return Double.parseDouble(value.replace("rad", "").trim()) * (180.0 / Math.PI);
        } else if (value.contains("turn")) {
            return Double.parseDouble(value.replace("turn", "").trim()) * 360.0;
        }
        return parseDouble(value, 360);
    }

    public static final Color BLACK = hex(NamedColor.BLACK.getHex());
    public static final Color SILVER = hex(NamedColor.SILVER.getHex());
    public static final Color GRAY = hex(NamedColor.GRAY.getHex());
    public static final Color WHITE = hex(NamedColor.WHITE.getHex());
    public static final Color MAROON = hex(NamedColor.MAROON.getHex());
    public static final Color RED = hex(NamedColor.RED.getHex());
    public static final Color PURPLE = hex(NamedColor.PURPLE.getHex());
    public static final Color FUCHSIA = hex(NamedColor.FUCHSIA.getHex());
    public static final Color GREEN = hex(NamedColor.GREEN.getHex());
    public static final Color LIME = hex(NamedColor.LIME.getHex());
    public static final Color OLIVE = hex(NamedColor.OLIVE.getHex());
    public static final Color YELLOW = hex(NamedColor.YELLOW.getHex());
    public static final Color NAVY = hex(NamedColor.NAVY.getHex());
    public static final Color BLUE = hex(NamedColor.BLUE.getHex());
    public static final Color TEAL = hex(NamedColor.TEAL.getHex());
    public static final Color AQUA = hex(NamedColor.AQUA.getHex());
    public static final Color ORANGE = hex(NamedColor.ORANGE.getHex());
    public static final Color ALICEBLUE = hex(NamedColor.ALICEBLUE.getHex());
    public static final Color ANTIQUEWHITE = hex(NamedColor.ANTIQUEWHITE.getHex());
    public static final Color AQUAMARINE = hex(NamedColor.AQUAMARINE.getHex());
    public static final Color AZURE = hex(NamedColor.AZURE.getHex());
    public static final Color BEIGE = hex(NamedColor.BEIGE.getHex());
    public static final Color BISQUE = hex(NamedColor.BISQUE.getHex());
    public static final Color BLANCHEDALMOND = hex(NamedColor.BLANCHEDALMOND.getHex());
    public static final Color BLUEVIOLET = hex(NamedColor.BLUEVIOLET.getHex());
    public static final Color BROWN = hex(NamedColor.BROWN.getHex());
    public static final Color BURLYWOOD = hex(NamedColor.BURLYWOOD.getHex());
    public static final Color CADETBLUE = hex(NamedColor.CADETBLUE.getHex());
    public static final Color CHARTREUSE = hex(NamedColor.CHARTREUSE.getHex());
    public static final Color CHOCOLATE = hex(NamedColor.CHOCOLATE.getHex());
    public static final Color CORAL = hex(NamedColor.CORAL.getHex());
    public static final Color CORNFLOWERBLUE = hex(NamedColor.CORNFLOWERBLUE.getHex());
    public static final Color CORNSILK = hex(NamedColor.CORNSILK.getHex());
    public static final Color CRIMSON = hex(NamedColor.CRIMSON.getHex());
    public static final Color CYAN = hex(NamedColor.CYAN.getHex());
    public static final Color DARKBLUE = hex(NamedColor.DARKBLUE.getHex());
    public static final Color DARKCYAN = hex(NamedColor.DARKCYAN.getHex());
    public static final Color DARKGOLDENROD = hex(NamedColor.DARKGOLDENROD.getHex());
    public static final Color DARKGRAY = hex(NamedColor.DARKGRAY.getHex());
    public static final Color DARKGREEN = hex(NamedColor.DARKGREEN.getHex());
    public static final Color DARKGREY = hex(NamedColor.DARKGREY.getHex());
    public static final Color DARKKHAKI = hex(NamedColor.DARKKHAKI.getHex());
    public static final Color DARKMAGENTA = hex(NamedColor.DARKMAGENTA.getHex());
    public static final Color DARKOLIVEGREEN = hex(NamedColor.DARKOLIVEGREEN.getHex());
    public static final Color DARKORANGE = hex(NamedColor.DARKORANGE.getHex());
    public static final Color DARKORCHID = hex(NamedColor.DARKORCHID.getHex());
    public static final Color DARKRED = hex(NamedColor.DARKRED.getHex());
    public static final Color DARKSALMON = hex(NamedColor.DARKSALMON.getHex());
    public static final Color DARKSEAGREEN = hex(NamedColor.DARKSEAGREEN.getHex());
    public static final Color DARKSLATEBLUE = hex(NamedColor.DARKSLATEBLUE.getHex());
    public static final Color DARKSLATEGRAY = hex(NamedColor.DARKSLATEGRAY.getHex());
    public static final Color DARKSLATEGREY = hex(NamedColor.DARKSLATEGREY.getHex());
    public static final Color DARKTURQUOISE = hex(NamedColor.DARKTURQUOISE.getHex());
    public static final Color DARKVIOLET = hex(NamedColor.DARKVIOLET.getHex());
    public static final Color DEEPPINK = hex(NamedColor.DEEPPINK.getHex());
    public static final Color DEEPSKYBLUE = hex(NamedColor.DEEPSKYBLUE.getHex());
    public static final Color DIMGRAY = hex(NamedColor.DIMGRAY.getHex());
    public static final Color DIMGREY = hex(NamedColor.DIMGREY.getHex());
    public static final Color DODGERBLUE = hex(NamedColor.DODGERBLUE.getHex());
    public static final Color FIREBRICK = hex(NamedColor.FIREBRICK.getHex());
    public static final Color FLORALWHITE = hex(NamedColor.FLORALWHITE.getHex());
    public static final Color FORESTGREEN = hex(NamedColor.FORESTGREEN.getHex());
    public static final Color GAINSBORO = hex(NamedColor.GAINSBORO.getHex());
    public static final Color GHOSTWHITE = hex(NamedColor.GHOSTWHITE.getHex());
    public static final Color GOLD = hex(NamedColor.GOLD.getHex());
    public static final Color GOLDENROD = hex(NamedColor.GOLDENROD.getHex());
    public static final Color GREENYELLOW = hex(NamedColor.GREENYELLOW.getHex());
    public static final Color GREY = hex(NamedColor.GREY.getHex());
    public static final Color HONEYDEW = hex(NamedColor.HONEYDEW.getHex());
    public static final Color HOTPINK = hex(NamedColor.HOTPINK.getHex());
    public static final Color INDIANRED = hex(NamedColor.INDIANRED.getHex());
    public static final Color INDIGO = hex(NamedColor.INDIGO.getHex());
    public static final Color IVORY = hex(NamedColor.IVORY.getHex());
    public static final Color KHAKI = hex(NamedColor.KHAKI.getHex());
    public static final Color LAVENDER = hex(NamedColor.LAVENDER.getHex());
    public static final Color LAVENDERBLUSH = hex(NamedColor.LAVENDERBLUSH.getHex());
    public static final Color LAWNGREEN = hex(NamedColor.LAWNGREEN.getHex());
    public static final Color LEMONCHIFFON = hex(NamedColor.LEMONCHIFFON.getHex());
    public static final Color LIGHTBLUE = hex(NamedColor.LIGHTBLUE.getHex());
    public static final Color LIGHTCORAL = hex(NamedColor.LIGHTCORAL.getHex());
    public static final Color LIGHTCYAN = hex(NamedColor.LIGHTCYAN.getHex());
    public static final Color LIGHTGOLDENRODYELLOW = hex(NamedColor.LIGHTGOLDENRODYELLOW.getHex());
    public static final Color LIGHTGRAY = hex(NamedColor.LIGHTGRAY.getHex());
    public static final Color LIGHTGREEN = hex(NamedColor.LIGHTGREEN.getHex());
    public static final Color LIGHTGREY = hex(NamedColor.LIGHTGREY.getHex());
    public static final Color LIGHTPINK = hex(NamedColor.LIGHTPINK.getHex());
    public static final Color LIGHTSALMON = hex(NamedColor.LIGHTSALMON.getHex());
    public static final Color LIGHTSEAGREEN = hex(NamedColor.LIGHTSEAGREEN.getHex());
    public static final Color LIGHTSKYBLUE = hex(NamedColor.LIGHTSKYBLUE.getHex());
    public static final Color LIGHTSLATEGRAY = hex(NamedColor.LIGHTSLATEGRAY.getHex());
    public static final Color LIGHTSLATEGREY = hex(NamedColor.LIGHTSLATEGREY.getHex());
    public static final Color LIGHTSTEELBLUE = hex(NamedColor.LIGHTSTEELBLUE.getHex());
    public static final Color LIGHTYELLOW = hex(NamedColor.LIGHTYELLOW.getHex());
    public static final Color LIMEGREEN = hex(NamedColor.LIMEGREEN.getHex());
    public static final Color LINEN = hex(NamedColor.LINEN.getHex());
    public static final Color MAGENTA = hex(NamedColor.MAGENTA.getHex());
    public static final Color MEDIUMAQUAMARINE = hex(NamedColor.MEDIUMAQUAMARINE.getHex());
    public static final Color MEDIUMBLUE = hex(NamedColor.MEDIUMBLUE.getHex());
    public static final Color MEDIUMORCHID = hex(NamedColor.MEDIUMORCHID.getHex());
    public static final Color MEDIUMPURPLE = hex(NamedColor.MEDIUMPURPLE.getHex());
    public static final Color MEDIUMSEAGREEN = hex(NamedColor.MEDIUMSEAGREEN.getHex());
    public static final Color MEDIUMSLATEBLUE = hex(NamedColor.MEDIUMSLATEBLUE.getHex());
    public static final Color MEDIUMSPRINGGREEN = hex(NamedColor.MEDIUMSPRINGGREEN.getHex());
    public static final Color MEDIUMTURQUOISE = hex(NamedColor.MEDIUMTURQUOISE.getHex());
    public static final Color MEDIUMVIOLETRED = hex(NamedColor.MEDIUMVIOLETRED.getHex());
    public static final Color MIDNIGHTBLUE = hex(NamedColor.MIDNIGHTBLUE.getHex());
    public static final Color MINTCREAM = hex(NamedColor.MINTCREAM.getHex());
    public static final Color MISTYROSE = hex(NamedColor.MISTYROSE.getHex());
    public static final Color MOCCASIN = hex(NamedColor.MOCCASIN.getHex());
    public static final Color NAVAJOWHITE = hex(NamedColor.NAVAJOWHITE.getHex());
    public static final Color OLDLACE = hex(NamedColor.OLDLACE.getHex());
    public static final Color OLIVEDRAB = hex(NamedColor.OLIVEDRAB.getHex());
    public static final Color ORANGERED = hex(NamedColor.ORANGERED.getHex());
    public static final Color ORCHID = hex(NamedColor.ORCHID.getHex());
    public static final Color PALEGOLDENROD = hex(NamedColor.PALEGOLDENROD.getHex());
    public static final Color PALEGREEN = hex(NamedColor.PALEGREEN.getHex());
    public static final Color PALETURQUOISE = hex(NamedColor.PALETURQUOISE.getHex());
    public static final Color PALEVIOLETRED = hex(NamedColor.PALEVIOLETRED.getHex());
    public static final Color PAPAYAWHIP = hex(NamedColor.PAPAYAWHIP.getHex());
    public static final Color PEACHPUFF = hex(NamedColor.PEACHPUFF.getHex());
    public static final Color PERU = hex(NamedColor.PERU.getHex());
    public static final Color PINK = hex(NamedColor.PINK.getHex());
    public static final Color PLUM = hex(NamedColor.PLUM.getHex());
    public static final Color POWDERBLUE = hex(NamedColor.POWDERBLUE.getHex());
    public static final Color ROSYBROWN = hex(NamedColor.ROSYBROWN.getHex());
    public static final Color ROYALBLUE = hex(NamedColor.ROYALBLUE.getHex());
    public static final Color SADDLEBROWN = hex(NamedColor.SADDLEBROWN.getHex());
    public static final Color SALMON = hex(NamedColor.SALMON.getHex());
    public static final Color SANDYBROWN = hex(NamedColor.SANDYBROWN.getHex());
    public static final Color SEAGREEN = hex(NamedColor.SEAGREEN.getHex());
    public static final Color SEASHELL = hex(NamedColor.SEASHELL.getHex());
    public static final Color SIENNA = hex(NamedColor.SIENNA.getHex());
    public static final Color SKYBLUE = hex(NamedColor.SKYBLUE.getHex());
    public static final Color SLATEBLUE = hex(NamedColor.SLATEBLUE.getHex());
    public static final Color SLATEGRAY = hex(NamedColor.SLATEGRAY.getHex());
    public static final Color SLATEGREY = hex(NamedColor.SLATEGREY.getHex());
    public static final Color SNOW = hex(NamedColor.SNOW.getHex());
    public static final Color SPRINGGREEN = hex(NamedColor.SPRINGGREEN.getHex());
    public static final Color STEELBLUE = hex(NamedColor.STEELBLUE.getHex());
    public static final Color TAN = hex(NamedColor.TAN.getHex());
    public static final Color THISTLE = hex(NamedColor.THISTLE.getHex());
    public static final Color TOMATO = hex(NamedColor.TOMATO.getHex());
    public static final Color TURQUOISE = hex(NamedColor.TURQUOISE.getHex());
    public static final Color VIOLET = hex(NamedColor.VIOLET.getHex());
    public static final Color WHEAT = hex(NamedColor.WHEAT.getHex());
    public static final Color WHITESMOKE = hex(NamedColor.WHITESMOKE.getHex());
    public static final Color YELLOWGREEN = hex(NamedColor.YELLOWGREEN.getHex());
    public static final Color REBECCAPURPLE = hex(NamedColor.REBECCAPURPLE.getHex());

}
