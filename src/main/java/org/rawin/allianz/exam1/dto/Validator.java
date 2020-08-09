package org.rawin.allianz.exam1.dto;

public interface Validator<T> {
    boolean validate(T t);
}
