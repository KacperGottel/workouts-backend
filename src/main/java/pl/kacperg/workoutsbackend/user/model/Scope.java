package pl.kacperg.workoutsbackend.user.model;

import lombok.Getter;

@Getter
public enum Scope {
    ADMIN("ADMIN"), USER("USER");

    private final String scopes;
    Scope(String scope) {
        this.scopes = scope;
    }
}
