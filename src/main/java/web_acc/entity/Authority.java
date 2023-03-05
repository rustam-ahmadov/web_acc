package web_acc.entity;

import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Entity
@Setter
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "authorities")
    private List<CustomUser> users;

    public Authority authority(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
