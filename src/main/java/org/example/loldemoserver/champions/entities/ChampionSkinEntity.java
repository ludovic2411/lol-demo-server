package org.example.loldemoserver.champions.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;


@Entity(name = "champions_skins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChampionSkinEntity implements Serializable {

    @EmbeddedId
    @NotNull
    @Valid
    private ChampionSkinId id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChampionSkinEntity other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
