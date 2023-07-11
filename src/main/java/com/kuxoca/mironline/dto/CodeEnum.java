package com.kuxoca.mironline.dto;

public enum CodeEnum {
    USD("Доллар США"),
    EUR("Евро"),
    UZS("Узбекский сум"),
    KRW("Южнокорейская вона"),
    BYN("Белорусский рубль"),
    TRY("Турецкая лира"),
    KGS("Кыргызский сом"),
    TJS("Таджикский сомони"),
    AMD("Армянский драм"),
    KZT("Казахстанский тенге"),
    VND("Вьетнамский донг"),
    VEF("Венесуэльский боливар"),
    CUP("Кубинский песо");

    private final String value;

    CodeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
