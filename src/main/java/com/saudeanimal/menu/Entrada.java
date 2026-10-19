package com.saudeanimal.menu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;
import java.util.function.Function;

public class Entrada {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private final Scanner scanner = new Scanner(System.in);

    public String lerLinha(String pergunta) {
        System.out.print(pergunta);
        return scanner.nextLine().trim();
    }

    public int lerInteiro(String pergunta) {
        while (true) {
            String entrada = lerLinha(pergunta);
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }

    public boolean confirmar(String pergunta) {
        while (true) {
            String resposta = lerLinha(pergunta + " (s/n): ").toLowerCase();
            if (resposta.equals("s") || resposta.equals("sim")) {
                return true;
            }
            if (resposta.equals("n") || resposta.equals("nao") || resposta.equals("não")) {
                return false;
            }
            System.out.println("Responda com s ou n.");
        }
    }

    public String lerTexto(String rotulo, int maximo, boolean obrigatorio) {
        return ler(perguntaNova(rotulo, obrigatorio), obrigatorio, false, null, texto(maximo));
    }

    public String lerTextoOuManter(String rotulo, String atual, int maximo, boolean obrigatorio) {
        return ler(perguntaAtual(rotulo, Formato.texto(atual)), obrigatorio, true, atual, texto(maximo));
    }

    public LocalDate lerData(String rotulo, boolean obrigatorio) {
        return ler(perguntaNova(rotulo + " (dd/mm/aaaa)", obrigatorio), obrigatorio, false, null, data());
    }

    public LocalDate lerDataOuManter(String rotulo, LocalDate atual, boolean obrigatorio) {
        return ler(perguntaAtual(rotulo + " (dd/mm/aaaa)", Formato.data(atual)), obrigatorio, true, atual, data());
    }

    public BigDecimal lerDecimal(String rotulo, BigDecimal maximo, boolean obrigatorio) {
        return ler(perguntaNova(rotulo, obrigatorio), obrigatorio, false, null, decimal(maximo));
    }

    public BigDecimal lerDecimalOuManter(String rotulo, BigDecimal atual, BigDecimal maximo, boolean obrigatorio) {
        return ler(perguntaAtual(rotulo, Formato.decimal(atual)), obrigatorio, true, atual, decimal(maximo));
    }

    private String perguntaNova(String rotulo, boolean obrigatorio) {
        return rotulo + (obrigatorio ? "" : " (opcional)") + ": ";
    }

    private String perguntaAtual(String rotulo, String atual) {
        return rotulo + " [" + atual + "]: ";
    }

    private <T> T ler(String pergunta, boolean obrigatorio, boolean temAtual, T atual, Function<String, T> conversor) {
        while (true) {
            String entrada = lerLinha(pergunta);
            if (entrada.isEmpty()) {
                if (temAtual) {
                    return atual;
                }
                if (!obrigatorio) {
                    return null;
                }
                System.out.println("Campo obrigatório.");
                continue;
            }
            if (temAtual && !obrigatorio && entrada.equals("-")) {
                return null;
            }
            try {
                return conversor.apply(entrada);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Function<String, String> texto(int maximo) {
        return valor -> {
            if (valor.length() > maximo) {
                throw new IllegalArgumentException("Máximo de " + maximo + " caracteres (você digitou " + valor.length() + ").");
            }
            return valor;
        };
    }

    private Function<String, LocalDate> data() {
        return valor -> {
            try {
                return LocalDate.parse(valor, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Data inválida. Use o formato dd/mm/aaaa, por exemplo 14/05/2020.");
            }
        };
    }

    private Function<String, BigDecimal> decimal(BigDecimal maximo) {
        return valor -> {
            BigDecimal numero;
            try {
                numero = new BigDecimal(valor.replace(',', '.'));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Número inválido. Exemplo: 12,5");
            }
            if (numero.signum() < 0) {
                throw new IllegalArgumentException("O valor não pode ser negativo.");
            }
            numero = numero.setScale(2, RoundingMode.HALF_UP);
            if (numero.compareTo(maximo) > 0) {
                throw new IllegalArgumentException("O valor máximo é " + Formato.decimal(maximo) + ".");
            }
            return numero;
        };
    }
}
