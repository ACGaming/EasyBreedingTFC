package mod.acgaming.easybreedingtfc;

import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import net.dries007.tfc.objects.entity.animal.EntityAnimalTFC;

public class EntityAIEatDroppedFood extends EntityAIBase
{
    private final EntityAnimalTFC animal;
    private final World world;
    double searchDistance = 10;

    public EntityAIEatDroppedFood(EntityAnimalTFC animal)
    {
        this.animal = animal;
        this.world = animal.world;
    }

    public EntityItem whatFoodIsNear()
    {
        List<EntityItem> items = getItems();
        for (EntityItem item : items)
        {
            return item;
        }
        return null;
    }

    public boolean shouldExecute()
    {
        EntityItem closeFood = whatFoodIsNear();
        if ((closeFood != null) && (this.animal.isBreedingItem(closeFood.getItem())))
        {
            if (this.animal.isReadyToMate())
            {
                execute(this.animal, closeFood);
            }
            else if (this.animal.isHungry())
            {
                //TO DO
            }
        }
        return false;
    }

    public void execute(EntityAnimalTFC animal, EntityItem item)
    {
        if (animal.getNavigator().tryMoveToXYZ(item.posX, item.posY, item.posZ, 1.25F))
        {
            if (animal.getDistance(item) < 1.0F)
            {
                eatOne(item);
                animal.setInLove(null);
            }
        }
    }

    public void eatOne(EntityItem item)
    {
        ItemStack stack = item.getItem();
        stack.setCount(stack.getCount() - 1);
        if (stack.getCount() == 0) item.setDead();
    }

    List<EntityItem> getItems()
    {
        return world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(animal.posX - searchDistance, animal.posY - searchDistance, animal.posZ - searchDistance, animal.posX + searchDistance, animal.posY + searchDistance, animal.posZ + searchDistance));
    }
}