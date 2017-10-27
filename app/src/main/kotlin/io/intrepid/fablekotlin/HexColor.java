package io.intrepid.fablekotlin;

public enum HexColor {

    NAVY("#182641"),
    GREEN("#00796c"),
    LAVENDER("#9292af"),
    DARKTEAL("#0d97af"),
    MAROON("#6c0048"),
    MUSTARD("#ffb717"),
    ORANGE("#f66517"),
    PINK("#ff5b82");

    private String hexColor;

    HexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getHexColor() {
        return hexColor;
    }

}
