package com.example.Trailers.profile.model;


import com.example.Trailers.user.model.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    @ElementCollection
    @CollectionTable(
         name = "user_favourite_genres",
         joinColumns = @JoinColumn(name = "user_profile_id")
    )
    @Column(name = "genre")
    private Set<String> favouriteGenres;

    @ElementCollection
    @CollectionTable(
            name = "user_favourite_franchises",
            joinColumns = @JoinColumn(name = "user_profile_id")
    )
    @Column(name = "franchise")
    private Set<String> favouriteFranchises;

    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "userProfile"
    )
    private UserAccount userAccount;
}
