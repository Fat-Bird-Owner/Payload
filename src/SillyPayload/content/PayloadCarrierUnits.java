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
                new PayloadSlot(Blocks.duo, -12f, 0f),
                new PayloadSlot(Blocks.duo, 12f, 0f),
                new PayloadSlot(Blocks.mender, 0f, -12f),
                new PayloadSlot(Blocks.battery, 0f, 12f)
            );
        }};

    }
}
