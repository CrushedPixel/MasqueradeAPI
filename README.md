# MasqueradeAPI
A disguise library for Sponge.

## Usage
### Masking a player
To mask a player, create a new instance of `Masquerade` and show it to other players.  
For example, if you want to mask the Player as a Zombie and show the masquerade to all online players, do:
```java
Masquerade masquerade = new ZombieMasquerade(player);

for (Player p : Sponge.getServer().getOnlinePlayers()) {
    // do not show the masquerade to the masked player itself
    if (p == player) continue;
    
    masquerade.maskTo(p);
}
```

The player now looks like a Zombie to all other online players on the server.

**Note:** Not every entity type has been implemented by a `Masquerade` yet - 
all currently available `Masquerade`s can be found under [`eu.crushedpixel.sponge.masquerade.api.masquerade.impl`](https://github.com/CrushedPixel/MasqueradeAPI/tree/master/src/main/java/eu/crushedpixel/sponge/masquerade/api/masquerades/impl).
### Unmasking a player
If you want to unmask the player again, you can either call `Masquerade#unmaskTo(Player)` to unmask them to a specific player 
or `Masquerade#unmask()` to unmask them to everyone.

**Note:** This API does *not* handle Events like clients connecting and disconnecting - for an example implementation, please refer to the [Masquerade Plugin](https://github.com/CrushedPixel/Masquerade/).

### Data Manipulators
Every `Masquerade` has a `DataManipulator` which exposes `EntityMetadata` fields that can be used to modify the fake entity's metadata.
This, for example, can be used to remove the pumpkin on a `SnowmanMasquerade`'s head:

```java
SnowmanDataManipulator manipulator = snowmanMasquerade.getDataManipulator();
manipulator.pumpkinEquipped.setValue(false);
```

Some `EntityMetadata` fields are automatically modified by the server, 
for example will the `onFire` field be set to `true` if the masked player walks through fire.  
You can easily disable this behaviour for each `EntityMetadata`:

```java
manipulator.onFire.setOverridesPlayerData(true);
```

Now, the entity will only appear in flames when asked for by the plugin, and otherwise retain the current state.
