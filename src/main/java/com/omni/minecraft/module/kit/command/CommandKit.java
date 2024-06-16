package com.omni.minecraft.module.kit.command;

import com.omni.minecraft.module.kit.KitModule;
import com.omni.minecraft.module.kit.data.Kit;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandKit {

  private final KitModule kitModule;

  @Command(
    name = "kit",
    permission = "omni.commands.kit"
  )
  public void onKit(BukkitContext context) {
    CommandSender sender = context.getSender();
  }

  @Command(
    name = "createkit",
    aliases = {"criarkit"},
    permission = "omni.commands.createkit"
  )
  public void onCreateKit(BukkitContext context, @Optional String name) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      kitModule.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    if (name == null) {
      kitModule.getExecutorHandler().messenger(sender, "commands.kit-create.usage");
      return;
    }

    if (kitModule.getKitManager().exists(name)) {
      kitModule.getExecutorHandler().messenger(sender, "commands.kit-create.already-exists");
      return;
    }

    Kit kit = Kit.builder()
      .name(name)
      .build();

    kitModule.getKitManager().register(kit);
    kitModule.getKitController().createKit(kit);

    kitModule.getExecutorHandler().messenger(sender, "commands.kit-create.success", name);
  }

  @Command(
    name = "deletekit",
    aliases = {"deletarkit"},
    permission = "omni.commands.deletekit"
  )
  public void onDeleteKit(BukkitContext context, @Optional String name) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      kitModule.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    if (name == null) {
      kitModule.getExecutorHandler().messenger(sender, "commands.kit-create.usage");
      return;
    }

    if (kitModule.getKitManager().exists(name)) {
      kitModule.getExecutorHandler().messenger(sender, "commands.kit-create.already-exists");
      return;
    }

    Kit kit = Kit.builder()
      .name(name)
      .build();

    kitModule.getKitManager().register(kit);
    kitModule.getKitController().createKit(kit);

    kitModule.getExecutorHandler().messenger(sender, "commands.kit-create.success", name);
  }
}
