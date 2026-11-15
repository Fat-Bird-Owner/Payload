package SillyPayload.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.type.*;
import SillyPayload.world.units.*;
import ent.anno.Annotations.*;
import mindustry.gen.*;
import mindustry.type.*;
import GierJava.gen.*;
import GierJava.world.abilities.*;
import GierJava.world.units.*;
import arc.*;
import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import arc.struct.Seq;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.ai.types.*;
import mindustry.gen.Sounds;
import mindustry.entities.abilities.*;
import mindustry.entities.pattern.*;
import mindustry.type.unit.*;

import static mindustry.Vars.*;

public class PayloadCarrierUnits{

    public static @EntityDef(value = {Unitc.class, Payloadc.class}) UnitType inquire;

    public static void load(){
      
        inquire = new PayloadCarrierUnitType("cargo-carrier"){{
            health = 500;
            speed = 2.2f;
            hitSize = 16f;
            itemCapacity = 100;

            drawCell = false;
            flying = true;
            payloadCapacity = (tilesize * tilesize) * 4f;

            payloadSlots.addAll(
                new PayloadSlot(Blocks.hail, 6f, 6f, true),
                new PayloadSlot(Blocks.hail, -6f, 6f, true),
                new PayloadSlot(Blocks.liquidRouter, 6f, -6f, true),
                new PayloadSlot(Blocks.liquidRouter, -6f, -6f, true)
            );
        }};

    }
}
