package org.example.diplomski.data.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.diplomski.data.enums.RelationshipStatus;

@Entity
@Table(
        name = "user_relationship",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user1_id", "user2_id"})
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRelationship {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    private RelationshipStatus status;


}
