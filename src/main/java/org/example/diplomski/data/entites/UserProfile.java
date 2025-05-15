package org.example.diplomski.data.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String profilePictureUrl;

    @OneToOne
    @MapsId
    private User user;
}
