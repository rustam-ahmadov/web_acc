package web_acc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter()
@NoArgsConstructor
public class CustomUser implements UserDetails {

    public CustomUser(String username, String surname, String password,
                      String email, Set<Authority> authorities, boolean accountNonExpired,
                      boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled,
                      String uuid) {
        this.username = username;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.uuid = uuid;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;
    private String username;
    private String surname;
    private String password;
    @Column(unique = true)
    private String email;
    @OneToOne(mappedBy = "customUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Image image;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    public static final class CustomUserBuilder {
        private String username;
        private String email;

        private String surname;

        private String password;
        private Set<Authority> authorities;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled;

        private String uuid;

        private PasswordEncoder passwordEncoder;

        public CustomUserBuilder() {
        }


        public CustomUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public CustomUserBuilder password(String password) {
            this.password = password;
            return this;
        }


        public CustomUserBuilder authorities(Set<Authority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public CustomUserBuilder passwordEncoder(PasswordEncoder encoder) {
            this.passwordEncoder = encoder;
            return this;
        }

        public CustomUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CustomUserBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }


        public CustomUserBuilder accountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public CustomUserBuilder accountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public CustomUserBuilder credentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public CustomUserBuilder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public CustomUserBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public CustomUser build() {
            String encodedPassword = this.passwordEncoder.encode(this.password);
            return new CustomUser(
                    this.username,
                    this.surname,
                    encodedPassword,
                    this.email,
                    this.authorities,
                    this.accountNonExpired,
                    this.accountNonLocked,
                    this.credentialsNonExpired,
                    this.enabled,
                    this.uuid
            );
        }
    }
}
