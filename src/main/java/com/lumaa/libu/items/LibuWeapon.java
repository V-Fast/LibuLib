package com.lumaa.libu.items;

import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;

public class LibuWeapon {
    public static class LibuSword extends SwordItem {
        public LibuSword(float attackDamage, float attackSpeed, int durability, Settings settings) {
            super(new LibuMaterial(durability, attackDamage - 1.0f), 0, attackSpeed - 4.0f, settings.maxCount(1));
        }
    }

    public static class LibuAxe extends AxeItem {
        public LibuAxe(float attackDamage, float attackSpeed, int durability, Settings settings) {
            super(new LibuMaterial(durability, attackDamage - 1.0f), 0, attackSpeed - 4.0f, settings.maxCount(1));
        }
    }

    public static class LibuThrowable extends Item {
        private static LibuProjectile.ProjectileInfo projectileInfo;

        public LibuThrowable(int damage, boolean dropOnHit, Settings settings) {
            super(settings);
            setProjectileInfo(new LibuProjectile.ProjectileInfo(damage, this.getDefaultStack(), dropOnHit));
        }

        public static void setProjectileInfo(LibuProjectile.ProjectileInfo info) {
            projectileInfo = info;
        }

        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
            ItemStack itemStack = user.getStackInHand(hand);
            world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!world.isClient) {
                LibuProjectile libuProjectile = new LibuProjectile(world, user, this.projectileInfo);
                libuProjectile.setItem(itemStack);
                libuProjectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
                world.spawnEntity(libuProjectile);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            return TypedActionResult.success(itemStack, world.isClient());
        }
    }
}
