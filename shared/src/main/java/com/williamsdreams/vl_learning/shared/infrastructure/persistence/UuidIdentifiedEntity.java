package com.williamsdreams.vl_learning.shared.infrastructure.persistence;

import jakarta.persistence.Id;

import java.util.UUID;

public abstract class UuidIdentifiedEntity {

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
