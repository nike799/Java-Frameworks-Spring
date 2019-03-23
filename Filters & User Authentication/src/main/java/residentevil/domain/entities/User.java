package residentevil.domain.entities;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    private String username;
    private String password;
    private String email;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;
    private Set<Role> authorities;

    public User() {
        this.authorities = new HashSet<>();
    }

    @Column(name = "username", unique = true, nullable = false)
    @Override
    public String getUsername() {
        return this.username;
    }

    @Column(name = "password", nullable = false)
    @Override
    public String getPassword() {
        return this.password;
    }

    @Column(name = "email", unique = true)
    public String getEmail() {
        return this.email;
    }

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @Override
    public Set<Role> getAuthorities() {
        return this.authorities;
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }
}
