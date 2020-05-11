package io.sskrishna.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Objects;

@MappedSuperclass
@Data
public class JpaModel implements Persistable<String> {
    @Id
    @Column(length = 36)
    private String id;

    @Version
    @Column(name = "VERSION")
    private long version;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaModel jpaModel = (JpaModel) o;
        return Objects.equals(id, jpaModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
