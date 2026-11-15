package SillyPayload.world.units;

import arc.*;
import arc.audio.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.g2d.TextureAtlas.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.ai.*;
import mindustry.ai.Pathfinder.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.part.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.graphics.MultiPacker.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.storage.*;
import static arc.graphics.g2d.Draw.*;
import static mindustry.Vars.*;

public class PayloadCarrierUnitType extends UnitType{

  public Seq<PayloadSlot> payloadSlots = new Seq<>();

    public static class PayloadSlot{
      
    public UnlockableContent type;
    public float x, y;

    public PayloadSlot(UnlockableContent type, float x, float y, boolean under){
        this.type = type;
        this.x = x;
        this.y = y;
        this.under = under;
    }

    public boolean matches(Payload payload){
        if(type instanceof Block block && payload instanceof BuildPayload build){
            return build.build.block == block;
        }

        if(type instanceof UnitType unitType && payload instanceof UnitPayload unit){
            return unit.unit.type == unitType;
        }

        return false;
    }
}
  
  public PayloadCarrierUnitType(String name){
        super(name);
  }
  
private void updateHeldPayloads(Unit unit){
    if(!(unit instanceof Payloadc payloadc)) return;

    Seq<Payload> payloads = payloadc.payloads();

    int count = Math.min(payloads.size, payloadSlots.size);

    for(int i = 0; i < count; i++){
        Payload payload = payloads.get(i);

        if(!payloadSlots.get(i).matches(payload)) continue;

        if(payload instanceof UnitPayload unitPayload){
            unitPayload.unit.update();

        } else if(payload instanceof BuildPayload buildPayload){

            Building build = buildPayload.build;
            build.update();

            if(unit.stack.item != null && unit.stack.amount > 0 && build.acceptItem(build, unit.stack.item)){
                build.handleItem(build, unit.stack.item);
                unit.stack.amount--;
            }

            if(build instanceof LiquidRouter.LiquidRouterBuild liqBuild){

                for(int j = 0; j < count; j++){
                    Payload otherPayload = payloads.get(j);

                    if(!payloadSlots.get(j).matches(otherPayload)) continue;
                    if(!(otherPayload instanceof BuildPayload otherBuild)) continue;

                    Building other = otherBuild.build;

                    if(other == liqBuild || other.liquids == null) continue;

                    Liquid liquid = liqBuild.liquids.current();

                    if(liquid != null && liqBuild.liquids.get(liquid) > 0f
                        && other.acceptLiquid(liqBuild, liquid)){

                        liqBuild.transferLiquid(other, 2f, liquid);
                    }
                }
            }

            if(build instanceof StorageBlock.StorageBuild storBuild){

                for(int j = 0; j < count; j++){
                    Payload otherPayload = payloads.get(j);

                    if(!payloadSlots.get(j).matches(otherPayload)) continue;
                    if(!(otherPayload instanceof BuildPayload otherBuild)) continue;

                    Building other = otherBuild.build;

                    if(other == storBuild || other.items == null) continue;

                    for(Item item : content.items()){
                        if(storBuild.items.get(item) <= 0) continue;

                        if(other.acceptItem(storBuild, item)){
                            other.handleItem(storBuild, item);
                            storBuild.items.remove(item, 1);
                        }
                    }
                }
            }
        }
    }
}

@Override
public void update(Unit unit){
    updateHeldPayloads(unit);
}

@Override
public void updatePayload(Unit unit, @Nullable Unit unitHolder, @Nullable Building buildingHolder){
    updateHeldPayloads(unit);
}

  public int getPayloadSlot(Payload payload, Seq<Payload> payloads){
    for(int i = 0; i < payloadSlots.size; i++){
        PayloadSlot slot = payloadSlots.get(i);

        if(!slot.matches(payload)) continue;

        boolean occupied = false;

        for(Payload other : payloads){
            if(other == payload) continue;

            boolean otherSlot = getPayloadSlotMatch(other, i);
            if(otherSlot){
                occupied = true;
                break;
            }
        }

        if(!occupied) return i;
    }

    return -1;
}

private boolean getPayloadSlotMatch(Payload payload, int slot){
    return payloadSlots.get(slot).matches(payload);
}
  
  public int getPayloadSlot(Payload payload, boolean[] occupied){
    for(int i = 0; i < payloadSlots.size; i++){
        if(occupied[i]) continue;
        if(!payloadSlots.get(i).matches(payload)) continue;

        return i;
    }

    return -1;
}
  
  @Override
public <T extends Unit & Payloadc> void drawPayload(T unit){
    if(!unit.hasPayload()) return;

    float layer = flying ? flyingLayer : groundLayer;
    float prev = Draw.z();

    Draw.z(layer + 0.02f);

    Seq<Payload> payloads = unit.payloads();
  
    boolean[] occupied = new boolean[payloadSlots.size];
  
for(Payload payload : unit.payloads()){
    int slotIndex = getPayloadSlot(payload, occupied);
    if(slotIndex == -1) continue;

    occupied[slotIndex] = true;

    if (payloadSlots.get(slotIndex).under) Draw.z(layer - 0.02f);
    PayloadSlot slot = payloadSlots.get(slotIndex);

    float x = unit.x + Angles.trnsx(unit.rotation - 90f, slot.x, slot.y);
    float y = unit.y + Angles.trnsy(unit.rotation - 90f, slot.x, slot.y);

    payload.set(x, y, unit.rotation);
    payload.draw();
}

    Draw.z(prev);
}
  
}
