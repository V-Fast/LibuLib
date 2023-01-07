package com.lumaa.libu.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class LibuProjectile extends ThrownItemEntity {
    private static ProjectileInfo projectileInfo;

    public LibuProjectile(World world, LivingEntity owner, ProjectileInfo info) {
        super(EntityType.SNOWBALL, owner, world);
        setItem(info.item);
    }

    public static void setProjectileInfo(ProjectileInfo projectileInfo) {
        LibuProjectile.projectileInfo = projectileInfo;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), projectileInfo.damage);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            if (projectileInfo.dropOnHit) {
                this.getWorld().spawnEntity(new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), projectileInfo.item));
            }
            this.world.sendEntityStatus(this, (byte)3);
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

