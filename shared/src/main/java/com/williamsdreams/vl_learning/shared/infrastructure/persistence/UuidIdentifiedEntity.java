package com.williamsdreams.vl_learning.shared.infrastructure.persistence;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class UuidIdentifiedEntity implements Serializable {

    @Id
    protected UUID id;

    public void setId(UUID id) {

        if (this.id != null) {
            throw new UnsupportedOperationException("ID is already defined");
        }

        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}
