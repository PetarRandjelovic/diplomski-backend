package org.example.diplomski.data.entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    private Long id;

    private String city;

    @ElementCollection
    private List<String> interests;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageData profilePictureUrl;


    @OneToOne
    @MapsId
    @ToString.Exclude
    @JsonBackReference
    private User user;



}
