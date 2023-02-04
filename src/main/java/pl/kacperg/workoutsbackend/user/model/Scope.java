package pl.kacperg.workoutsbackend.user.model;

import lombok.Getter;

@Getter
public enum Scope {
    ADMIN("ADMIN"), USER("USER");

    private final String scope;
    Scope(String scope) {
        this.scope = scope;
    }
}
