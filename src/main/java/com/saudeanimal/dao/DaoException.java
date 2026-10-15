package com.saudeanimal.dao;

public class DaoException extends RuntimeException {

    public DaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
