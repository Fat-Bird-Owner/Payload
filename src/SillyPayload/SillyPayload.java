package SillyPayload;

import mindustry.mod.*;
import sillyPayload.content.*;
import sillyPayload.gen.*;

public class SillyPayload extends Mod{
    @Override
    public void loadContent(){
        EntityRegistry.register();
        PayloadCarrierUnits.load();
    }
}
