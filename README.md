# MasqueradeAPI
A disguise library for Sponge.

## Usage
### Masking a player
To mask a player, retrieve an instance of `Masquerade` and show it to other players.  
For example, if you want to mask the Player as a Zombie and show the masquerade to all online players, do:
```java
Masquerade masquerade = Masquerades.fromType(EntityTypes.ZOMBIE, player);

for (Player p : Sponge.getServer().getOnlinePlayers()) {
    // do not show the masquerade to the masked player itself
    if (p == player) continue;
    
    masquerade.maskTo(p);
}
```

The player now looks like a Zombie to all other online players on the server.

### Unmasking a player
If you want to unmask the player again, you can either call `Masquerade#unmaskTo(Player)` to unmask them to a specific player 
or `Masquerade#unmask()` to unmask them to everyone.

**Note:** This API does *not* handle Events like clients connecting and disconnecting - for an example implementation, please refer to the [Masquerade Plugin](https://github.com/CrushedPixel/Masquerade/).

### Masquerade Data
You can manipulate the `Masquerade`'s entity metadata using Sponge `Key`s, for example to display a custom name for the fake entity:

```java
masquerade.setData(Keys.DISPLAY_NAME, Text.of("Dinnerbone"));
masquerade.setData(Keys.CUSTOM_NAME_VISIBLE, true);
```

Please note that some Keys may not be applicable to a certain `Masquerade` type or may not be implemented yet, in which case `setData` will throw an `IllegalArgumentException`.

Some metadata fields are automatically modified by the server, 
for example will the `Keys.IS_ABLAZE` be modified if the masked player walks through fire.  
You can easily disable this behaviour for each key separately:

```java
manipulator.setAllowValueChange(Keys.IS_ABLAZE, false);
```

Now, the entity will only appear in flames when asked for by the plugin, and otherwise retain the current state.
