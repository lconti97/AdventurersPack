package com.kirodinstudios.adventurerspack_ddinventorymanagementtool.model;

public class WeaponTemplate extends EquipmentTemplate {

    private String damage;
    private String properties;
    private Boolean isSimpleWeapon;
    private Boolean isMeleeWeapon;

    public WeaponTemplate() { }

    public WeaponTemplate(String name, String description, Double costInGp, Double weightInPounds, String damage, String properties, Boolean isSimpleWeapon, Boolean isMeleeWeapon) {
        super(name, description, costInGp, weightInPounds);
        this.damage = damage;
        this.properties = properties;
        this.isSimpleWeapon = isSimpleWeapon;
        this.isMeleeWeapon = isMeleeWeapon;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public Boolean getIsSimpleWeapon() {
        return isSimpleWeapon;
    }

    public void setIsSimpleWeapon(Boolean simpleWeapon) {
        isSimpleWeapon = simpleWeapon;
    }

    public Boolean getIsMeleeWeapon() {
        return isMeleeWeapon;
    }

    public void setIsMeleeWeapon(Boolean meleeWeapon) {
        isMeleeWeapon = meleeWeapon;
    }

    public String getWeaponType() {
        if (isSimpleWeapon == null || isMeleeWeapon == null)
            return "";
        String simpleOrMartial = isSimpleWeapon ? "Simple" : "Martial";
        String meleeOrRanged = isMeleeWeapon ? "melee" : "ranged";
        return String.format("%s %s weapon", simpleOrMartial, meleeOrRanged);
    }
}
