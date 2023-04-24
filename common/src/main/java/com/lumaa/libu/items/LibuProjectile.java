package com.lumaa.libu.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class LibuProjectile extends ThrownEgg {
    private static ProjectileInfo projectileInfo;

    public LibuProjectile(Level world, LivingEntity owner, ProjectileInfo info) {
        super(world, owner);
        setItem(info.item);
    }

    public static void setProjectileInfo(ProjectileInfo projectileInfo) {
        LibuProjectile.projectileInfo = projectileInfo;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.handleDamageEvent(level.damageSources().thrown(this, this.getOwner()));
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            if (projectileInfo.dropOnHit) {
                this.getLevel().addFreshEntity(new ItemEntity(this.getLevel(), this.getX(), this.getY(), this.getZ(), projectileInfo.item));
            }
//            this.getLevel().sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    public static class ProjectileInfo {
        public int damage;
        public ItemStack item;
        public boolean dropOnHit;

        public ProjectileInfo(int damage, ItemStack itemStack, boolean dropOnHit) {
            this.damage = damage;
            this.item = itemStack;
            this.dropOnHit = dropOnHit;
        }
    }
}

