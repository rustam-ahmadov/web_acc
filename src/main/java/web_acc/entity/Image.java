package web_acc.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] bytes;
    private String extension;
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private CustomUser customUser;
}
