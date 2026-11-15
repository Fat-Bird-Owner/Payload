package SillyPayload;

import mindustry.mod.*;
import SillyPayload.content.*;
import SillyPayload.gen.*;

public class SillyPayload extends Mod{
    @Override
    public void loadContent(){
        EntityRegistry.register();
        PayloadCarrierUnits.load();
    }
}
