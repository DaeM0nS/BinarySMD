package dev.thecodewarrior.binarysmd.studiomdl;

import dev.thecodewarrior.binarysmd.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NodesBlock extends SMDFileBlock {
    public @NotNull List<@NotNull Bone> bones = new ArrayList<>();

    public static class Bone {
        public int id;
        public @NotNull String name;
        public int parent;

        public Bone(int id, @NotNull String name, int parent) {
            this.id = id;
            this.name = name;
            this.parent = parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Bone)) return false;
            Bone bone = (Bone) o;
            return id == bone.id &&
                    parent == bone.parent &&
                    name.equals(bone.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, parent);
        }
    }

}