package pl.kacperg.workoutsbackend.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kacperg.workoutsbackend.user.model.User;
import pl.kacperg.workoutsbackend.user.model.UserStatus;

import java.util.Collection;

import static java.util.Arrays.stream;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(user.getScope().name().split(",")).toList().stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getUserStatus().name().equals(UserStatus.DISABLED.name());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getUserStatus().name().equals(UserStatus.BLOCKED.name());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getUserStatus().name().equals(UserStatus.ENABLED.name());
    }
}
