package org.example.loldemoserver.champions.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class ChampionSkinId implements Serializable {

    @NotBlank
    @Size(max = 255)
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @NotBlank
    @Size(max = 255)
    @Column(name = "champion_id", nullable = false, length = 255)
    private String championId;

    @NotBlank
    @Size(max = 255)
    @Column(name = "skin_id", nullable = false, length = 255)
    private String skinId;
}