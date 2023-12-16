package lol.koblizek.stellarvoyage.block.entity.bloomery;

import lol.koblizek.stellarvoyage.block.ModBlockEntities;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtils;

public class BloomeryBlockEntity extends BlockEntity implements GeoBlockEntity, ExtendedScreenHandlerFactory, ImplementedInventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

    private static final int FUEL_SLOT = 0;
    private static final int FLUX_SLOT = 1;
    private static final int ORE_SLOT = 2;
    private static final int SLAG_SLOT = 3;
    private static final int OUTPUT_SLOT = 4;

    protected final PropertyDelegate propertyDelegate;
    private int progress;
    private int maxProgress = 72;


    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public BloomeryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLOOMERY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BloomeryBlockEntity.this.progress;
                    case 1 -> BloomeryBlockEntity.this.maxProgress;
                    default -> BloomeryBlockEntity.this.maxProgress;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BloomeryBlockEntity.this.progress = value;
                    case 1 -> BloomeryBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.stellarvoyage.bloomery.name");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("bloomery.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("bloomery.proggress");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public void tick(World world1, BlockPos pos, BlockState state1) {
    }
}
