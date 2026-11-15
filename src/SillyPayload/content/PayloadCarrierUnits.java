package SillyPayload.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.type.*;
import SillyPayload.world.units.*;

import static mindustry.Vars.*;

public class PayloadCarrierUnits{

    public static UnitType cargoCarrier;

    public static void load(){
      
        cargoCarrier = new PayloadCarrierUnitType("cargo-carrier"){{
            health = 500;
            speed = 1.5f;
            hitSize = 16f;
            itemCapacity = 100;

            payloadCapacity = (tilesize * tilesize) * 4f;

            payloadSlots.addAll(
                new PayloadSlot(Blocks.duo, 6f, 6f),
                new PayloadSlot(Blocks.duo, -6f, 6f),
                new PayloadSlot(Blocks.liquidRouter, 6f, -6f),
                new PayloadSlot(Blocks.liquidRouter, -6f, -6f)
            );
        }};

    }
}
