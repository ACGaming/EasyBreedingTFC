package mod.acgaming.easybreedingtfc;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.objects.entity.animal.EntityAnimalTFC;

public class DroppedFoodEventHandler
{
    @SubscribeEvent
    public void addAI(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof EntityAnimalTFC && event.getEntityLiving().ticksExisted < 5 && !event.getEntityLiving().isChild())
        {
            EntityAnimalTFC animal = (EntityAnimalTFC) event.getEntityLiving();
            animal.tasks.addTask(2, new EntityAIEatDroppedFood(animal));
        }
    }
}