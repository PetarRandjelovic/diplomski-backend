package org.example.diplomski.data.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.diplomski.data.enums.MediaType;

@Entity
@Table(name = "media")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private MediaType type;

    private String title;
}
