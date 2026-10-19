package com.saudeanimal.menu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Formato {

    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Formato() {
    }

    public static String data(LocalDate data) {
        return data == null ? "-" : data.format(DATA);
    }

    public static String decimal(BigDecimal valor) {
        return valor == null ? "-" : valor.toPlainString().replace('.', ',');
    }

    public static String decimal(BigDecimal valor, String unidade) {
        return valor == null ? "-" : decimal(valor) + " " + unidade;
    }

    public static String periodo(LocalDate inicio, LocalDate fim) {
        return fim == null ? "desde " + data(inicio) + " (em andamento)" : data(inicio) + " a " + data(fim);
    }

    public static String texto(String texto) {
        return texto == null || texto.isBlank() ? "-" : texto;
    }
}
