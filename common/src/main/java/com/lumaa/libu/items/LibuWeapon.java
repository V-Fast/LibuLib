package com.lumaa.libu.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class LibuWeapon {
    public static class LibuSword extends SwordItem {
        public LibuSword(float attackDamage, float attackSpeed, int durability, Properties Properties) {
            super(new LibuMaterial(durability, attackDamage - 1.0f), 0, attackSpeed - 4.0f, Properties.stacksTo(1));
        }
    }

    public static class LibuAxe extends AxeItem {
        public LibuAxe(float attackDamage, float attackSpeed, int durability, Properties Properties) {
            super(new LibuMaterial(durability, attackDamage - 1.0f), 0, attackSpeed - 4.0f, Properties.stacksTo(1));
        }
    }

    /**
     * @deprecated Does not work properly
     */
    @Deprecated
    public static class LibuThrowable extends Item {
        private static LibuProjectile.ProjectileInfo projectileInfo;

        public LibuThrowable(int damage, boolean dropOnHit, Item.Properties settings) {
            super(settings);
            setProjectileInfo(new LibuProjectile.ProjectileInfo(damage, this.getDefaultInstance(), dropOnHit));
        }

        public static void setProjectileInfo(LibuProjectile.ProjectileInfo info) {
            projectileInfo = info;
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!level.isClientSide) {
                LibuProjectile libuProjectile = new LibuProjectile(level, player, this.projectileInfo);
                libuProjectile.setItem(itemStack);
                libuProjectile.setDeltaMovement(0.0F, 1.5F, 1.0F);
                level.addFreshEntity(libuProjectile);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            if (!Objects.requireNonNull(player.getServer().getPlayerList().getPlayer(player.getUUID())).gameMode.getGameModeForPlayer().isCreative()) {
                itemStack.setCount(itemStack.getCount() - 1);
            }

            return InteractionResultHolder.success(itemStack);
        }
    }
}
