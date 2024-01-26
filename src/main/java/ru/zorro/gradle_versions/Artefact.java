package ru.zorro.gradle_versions;

import java.util.Objects;
import java.util.UUID;

public class Artefact {
    private final UUID id;
    private final String group;
    private final String artefact;
    private final String version;

    private final String fullPath;

    public Artefact(UUID id, String group, String artefact, String version, String fullPath) {
        this.id = id;
        this.group = group;
        this.artefact = artefact;
        this.version = version;
        this.fullPath = fullPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artefact)) return false;
        Artefact artefact = (Artefact) o;
        return Objects.equals(id, artefact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Artefact{" +
                "id=" + id +
                ", group='" + group + '\'' +
                ", artefact='" + artefact + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
