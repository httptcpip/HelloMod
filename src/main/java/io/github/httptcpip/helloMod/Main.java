package io.github.httptcpip.helloMod;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod("hello_mod")
@Mod.EventBusSubscriber
public class Main {
    private static Player player;
    private static int Lagging = 0x00;
    private static boolean VerySecretSets = false; // Don't set it to true
    private static boolean DoOutPuts = false;

    @SubscribeEvent
    public static void PlayerJoinWorld(PlayerEvent.@NotNull PlayerLoggedInEvent event) {
        player = event.getEntity();
        player.sendSystemMessage(Component.nullToEmpty(
                "Hello " +
                        player.getDisplayName().getString() +
                        "."
        ));
    }

    @SubscribeEvent
    public static void LagOnFire(TickEvent event) {
        if (player != null) {
            if (player.wasOnFire) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (Lagging != 0) {
            try {
                Thread.sleep(Lagging);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SubscribeEvent
    public static void LeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (DoOutPuts) {
            Player player1 = event.getEntity();
            if (player1.level.isClientSide) {
                Player[] PlayerList = player1.level.players().toArray(new Player[0]);
                for (Player value : PlayerList) {
                    value.sendSystemMessage(Component.nullToEmpty("LeftClickBlock,on block:" + event.getPos() +
                            "with item" + event.getItemStack()));
                }
                if (event.getItemStack().toString().equals("flint_and_steel") && VerySecretSets) {
                    Lagging++;
                    player1.sendSystemMessage(Component.nullToEmpty("Lagging:" + Lagging));
                } else if (event.getItemStack().toString().equals("iron_block")) {
                    Lagging--;
                    player1.sendSystemMessage(Component.nullToEmpty("Lagging:" + Lagging));
                }
            }
        }
    }

    @SubscribeEvent
    public static void ConfigVSSAndOutput(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.toString().equals("iron_block")) DoOutPuts = !DoOutPuts;
        else if (itemStack.toString().equals("hopper")) VerySecretSets = true;
    }

    @SubscribeEvent
    public static void TeleportUseDiamSword(PlayerInteractEvent.RightClickBlock event){
        event.getEntity().sendSystemMessage(Component.nullToEmpty("Oh!!!!!!!!"));
    }
}
