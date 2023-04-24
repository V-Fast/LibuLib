package com.lumaa.libu.items;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class LibuMaterial implements Tier {
    private int durability;
    private float attackDamage;
    private int miningLevel = 0;
    private int enchantability;
    private Ingredient repairIngredient = null;

    // any
    public LibuMaterial(int durability, float attackDamage, int miningLevel, int enchantability, Ingredient repairIngredient) {
        this.durability = durability;
        this.attackDamage = attackDamage;
        this.miningLevel = miningLevel;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    // sword type
    public LibuMaterial(int durability, float attackDamage) {
        this.durability = durability;
        this.attackDamage = attackDamage;
        this.enchantability = 15;
    }

    // tool type
    public LibuMaterial(int durability, int miningLevel, int enchantability) {
        this.durability = durability;
        this.miningLevel = miningLevel;
        this.enchantability = enchantability;
        this.attackDamage = 2.0F;
    }
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public int getUses() {
        return this.durability;
    }

    @Override
    public float getSpeed() {
        return 0;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }
}