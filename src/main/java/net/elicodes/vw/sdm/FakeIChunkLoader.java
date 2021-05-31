package net.elicodes.vw.sdm;

import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.ExceptionWorldConflict;
import net.minecraft.server.v1_8_R3.IChunkLoader;
import net.minecraft.server.v1_8_R3.World;

import java.io.IOException;

public class FakeIChunkLoader implements IChunkLoader {

    @Override
    public Chunk a(World world, int i, int i1) throws IOException {
        return null;
    }

    @Override
    public void a(World world, Chunk chunk) throws IOException, ExceptionWorldConflict {

    }

    @Override
    public void b(World world, Chunk chunk) throws IOException {

    }

    @Override
    public void a() {

    }

    @Override
    public void b() {

    }
}
