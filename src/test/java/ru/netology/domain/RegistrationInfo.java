package ru.netology.domain;


import lombok.Value;

@Value
public class RegistrationInfo {
    private final String login;
    private final String password;
    private final String status;
}