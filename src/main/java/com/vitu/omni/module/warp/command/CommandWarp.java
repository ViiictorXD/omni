package com.vitu.omni.module.warp.command;

import com.vitu.omni.module.warp.WarpModule;
import com.vitu.omni.module.warp.data.Warp;
import com.vitu.omni.module.warp.event.WarpCreateEvent;
import com.vitu.omni.module.warp.event.WarpDeleteEvent;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandWarp {

  private final WarpModule warpModule;

  @Command(
    name = "warp",
    permission = "omni.commands.warp"
  )
  public void onWarp(BukkitContext context, @Optional String warpName) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      warpModule.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    if (warpName == null) {
      warpModule.getExecutorHandler().messenger(sender, "commands.warp.usage");
      return;
    }

    Warp warp = warpModule.getWarpManager().getWarp(warpName);
    if (warp == null) {
      warpModule.getExecutorHandler().messenger(sender, "commands.warp.not-found");
      return;
    }

    warpModule.getWarpController().teleport(((Player) sender), warp);
  }

  @Command(
    name = "warps",
    permission = "omni.commands.warps"
  )
  public void onWarps(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      warpModule.getExecutorHandler().messenger(sender, "commands.only-player");
    }

    // TODO: Abrir menu com os Warps
  }

  @Command(
    name = "setwarp",
    aliases = {"setarwarp"},
    permission = "omni.commands.setwarp"
  )
  public void onSetWarp(BukkitContext context, @Optional String warpName) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      warpModule.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    if (warpName == null) {
      warpModule.getExecutorHandler().messenger(sender, "commands.set-warp.name-cannot-be-null");
      return;
    }

    if (warpModule.getWarpManager().exists(warpName)) {
      warpModule.getExecutorHandler().messenger(sender, "commands.set-warp.already-exists");
      return;
    }

    Warp warp = Warp.builder()
      .name(warpName)
      .location(((Player) sender).getLocation())
      .build();

    warpModule.getWarpController().createWarp(warp);
    warpModule.getWarpManager().register(warp);

    Bukkit.getPluginManager().callEvent(new WarpCreateEvent(warp));

    warpModule.getExecutorHandler().messenger(sender, "commands.set-warp.success", warpName);
  }

  @Command(
    name = "delwarp",
    aliases = {"deletarwarp"},
    permission = "omni.commands.delwarp"
  )
  public void onDelWarp(BukkitContext context, @Optional String warpName) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      warpModule.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    if (warpName == null) {
      warpModule.getExecutorHandler().messenger(sender, "commands.set-warp.name-cannot-be-null");
      return;
    }

    Warp warp = warpModule.getWarpManager().getWarp(warpName);
    if (warp == null) {
      warpModule.getExecutorHandler().messenger(sender, "commands.warp.not-found");
      return;
    }

    warpModule.getWarpController().deleteWarp(warp);
    Bukkit.getPluginManager().callEvent(new WarpDeleteEvent(warp));

    warpModule.getExecutorHandler().messenger(sender, "commands.del-warp.success", warpName);
  }

  @Command(
    name = "editwarp",
    aliases = {"editarwarp"},
    permission = "omni.commands.editwarp"
  )
  public void onEditWarp(BukkitContext context, @Optional String warpName) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      warpModule.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    if (warpName == null) {
      warpModule.getExecutorHandler().messenger(sender, "commands.set-warp.name-cannot-be-null");
      return;
    }

    Warp warp = warpModule.getWarpManager().getWarp(warpName);
    if (warp == null) {
      warpModule.getExecutorHandler().messenger(sender, "commands.del-warp.not-found");
      return;
    }

    sender.sendMessage("hello world");
  }
}
