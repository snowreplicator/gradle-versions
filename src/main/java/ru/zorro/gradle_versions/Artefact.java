package ru.zorro.gradle_versions;

import java.util.Objects;

public class Artefact {

    private final String group;
    private final String artefact;
    private final String version;
    private final String fullPath;

    public Artefact(String group, String artefact, String version, String fullPath) {
        this.group = group;
        this.artefact = artefact;
        this.version = version;
        this.fullPath = fullPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artefact a = (Artefact) o;
        return group.equals(a.group) && artefact.equals(a.artefact) && version.equals(a.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, artefact, version);
    }

    @Override
    public String toString() {
        return "Artefact{" + "group='" + group + '\'' + ", artefact='" + artefact + '\'' + ", version='" + version + '\'' + ", fullPath='" + fullPath + '\'' + '}';
    }

    public String getGroup() {
        return group;
    }

    public String getArtefact() {
        return artefact;
    }

    public String getVersion() {
        return version;
    }
}
