package com.vitu.omni.serializer;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ItemStackSerializer {

  public static String serializeOne(ItemStack itemStack) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

      dataOutput.writeObject(itemStack);

      dataOutput.close();
      return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    } catch (IOException e) {
      throw new IllegalStateException("Não foi possível serializar o ItemStack.", e);
    }
  }

  public static ItemStack deserializeOne(String base64) {
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
      BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

      ItemStack itemStack = (ItemStack) dataInput.readObject();
      dataInput.close();
      return itemStack;
    } catch (IOException | ClassNotFoundException e) {
      throw new IllegalStateException("Não foi possível desserializar o ItemStack.", e);
    }
  }

  public static String serializeArray(ItemStack[] items) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

      dataOutput.writeInt(items.length);
      for (ItemStack item : items) {
        dataOutput.writeObject(item);
      }

      dataOutput.close();
      return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    } catch (IOException e) {
      throw new IllegalStateException("Não foi possível serializar o array de ItemStack.", e);
    }
  }

  public static ItemStack[] deserializeArray(String base64) {
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
      BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

      int length = dataInput.readInt();
      ItemStack[] items = new ItemStack[length];
      for (int i = 0; i < length; i++) {
        items[i] = (ItemStack) dataInput.readObject();
      }

      dataInput.close();
      return items;
    } catch (IOException | ClassNotFoundException e) {
      throw new IllegalStateException("Não foi possível desserializar o array de ItemStack.", e);
    }
  }

  public static String serializeInventory(Inventory inventory) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

      dataOutput.writeInt(inventory.getSize());
      for (int i = 0; i < inventory.getSize(); i++) {
        dataOutput.writeObject(inventory.getItem(i));
      }

      dataOutput.close();
      return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    } catch (IOException e) {
      throw new IllegalStateException("Não foi possível serializar o Inventory.", e);
    }
  }

  public static Inventory deserializeInventory(String base64) {
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
      BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

      int size = dataInput.readInt();
      Inventory inventory = Bukkit.createInventory(null, size);
      for (int i = 0; i < size; i++) {
        inventory.setItem(i, (ItemStack) dataInput.readObject());
      }

      dataInput.close();
      return inventory;
    } catch (IOException | ClassNotFoundException e) {
      throw new IllegalStateException("Não foi possível desserializar o Inventory.", e);
    }
  }
}
