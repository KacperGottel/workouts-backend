package pl.kacperg.workoutsbackend.users.model;

import lombok.Getter;

@Getter
public enum Scope {
    ADMIN("ADMIN"), USER("USER");

    private final String scopes;
    Scope(String scope) {
        this.scopes = scope;
    }
}
