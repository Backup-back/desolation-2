package raltsmc.desolation.mixin;


import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import raltsmc.desolation.registry.DesolationBlocks;

import java.util.Random;
import java.util.Set;

import static net.minecraft.world.gen.feature.Feature.isSoil;

@Mixin(TreeFeature.class)
public class TreeFeatureMixin {
    @Inject(method = "isDirtOrGrass", at = @At("HEAD"), cancellable = true)
    private static void isDirtOrGrass(TestableWorld world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        boolean isValid = world.testBlockState(pos, blockState -> {
            Block block = blockState.getBlock();
            return isSoil(block) || block == Blocks.FARMLAND || block == DesolationBlocks.CHARRED_SOIL;
        });
        info.setReturnValue(isValid);
    }
}
