package org.example.diplomski.data.entites;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "ImageData")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "imagedata")
    private byte[] imageData;
}