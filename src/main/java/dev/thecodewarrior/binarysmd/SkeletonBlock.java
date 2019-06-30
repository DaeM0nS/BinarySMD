package dev.thecodewarrior.binarysmd;

import dev.thecodewarrior.binarysmd.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkeletonBlock extends SMDFileBlock {
    public @NotNull List<@NotNull Keyframe> keyframes = new ArrayList<>();

    public SkeletonBlock(Tokenizer file) {
        if(file.current().test("end")) {
            file.advance().next().expectLine();
        }
        file.current().expect("time");

        Keyframe keyframe = new Keyframe(-1); // this temporary keyframe is immediately overwritten
        while(!file.current().test("end")) {
            if(file.current().test("time")) {
                keyframe = new Keyframe(file.advance().next().toInt());
                keyframes.add(keyframe);
            } else {
                keyframe.states.add(
                        new BoneState(file.next().toInt(),
                                file.next().toFloat(), file.next().toFloat(), file.next().toFloat(),
                                file.next().toFloat(), file.next().toFloat(), file.next().toFloat()
                        )
                );
            }
            file.next().expectLine();
        }
        file.next().expect("end");
        file.next().expectLine();
    }

    public static class Keyframe {
        public int time;
        public @NotNull List<@NotNull BoneState> states = new ArrayList<>();

        public Keyframe(int time) {
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Keyframe)) return false;
            Keyframe keyframe = (Keyframe) o;
            return time == keyframe.time &&
                    states.equals(keyframe.states);
        }

        @Override
        public int hashCode() {
            return Objects.hash(time, states);
        }
    }

    public static class BoneState {
        public int bone;
        public float posX, posY, posZ;
        public float rotX, rotY, rotZ;

        public BoneState(int bone, float posX, float posY, float posZ, float rotX, float rotY, float rotZ) {
            this.bone = bone;
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.rotX = rotX;
            this.rotY = rotY;
            this.rotZ = rotZ;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BoneState)) return false;
            BoneState boneState = (BoneState) o;
            return bone == boneState.bone &&
                    Float.compare(boneState.posX, posX) == 0 &&
                    Float.compare(boneState.posY, posY) == 0 &&
                    Float.compare(boneState.posZ, posZ) == 0 &&
                    Float.compare(boneState.rotX, rotX) == 0 &&
                    Float.compare(boneState.rotY, rotY) == 0 &&
                    Float.compare(boneState.rotZ, rotZ) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(bone, posX, posY, posZ, rotX, rotY, rotZ);
        }
    }
}
