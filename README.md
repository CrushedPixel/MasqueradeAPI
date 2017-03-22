# MasqueradeAPI
A disguise API for Sponge.

## Installation
### Plugin development
To use the **MasqueradeAPI** in your project, you need to add it to your Maven/Gradle dependencies.  
We suggest using [jitpack.io](https://jitpack.io) to depend on the **MasqueradeAPI** like so:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.CrushedPixel</groupId>
        <artifactId>MasqueradeAPI</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
</dependencies>
```
### Sponge
To run a plugin that depends on the **MasqueradeAPI**, you need to install the [MasqueradePlugin] on your Sponge server.

## Usage
### The `Masquerades` service
The [MasqueradePlugin] registers a Sponge service that exposes an instance of `Masquerades`, which can be used to retrieve instances of `Masquerade`:
```java
Optional<Masquerades> optional = Sponge.getServiceManager().provide(Masquerades.class);
if (optional.isPresent()) {
    Masquerades masquerades = optional.get();
    // use Masquerades
} else {
    // the MasqueradesPlugin is not installed on the server
}
```
### Masking a player
To mask a player, retrieve an instance of `Masquerade` from `Masquerades` and show it to other players.  
For example, if you want to mask the Player as a Zombie and show the masquerade to all online players, do:
```java
Masquerade masquerade = masquerades.fromType(EntityTypes.ZOMBIE, player);

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

**Note:** The [MasqueradePlugin] itself does *not* handle Events like clients connecting and disconnecting - for an example implementation, please refer to [MasqueradeCommand].

### Masquerade Data
You can manipulate the `Masquerade`'s entity metadata using Sponge `Key`s, for example to display a custom name for the fake entity:

```java
masquerade.setData(Keys.DISPLAY_NAME, Text.of("Dinnerbone"));
masquerade.setData(Keys.CUSTOM_NAME_VISIBLE, true);
```

Please note that some Keys may not be applicable to a certain `Masquerade` type or may not be implemented yet, in which case `setData` will throw an `IllegalArgumentException`.

Some metadata fields are automatically modified by the server, 
for example `Keys.IS_ABLAZE` will be modified if the masked player walks through fire.  
You can easily disable this behaviour for each key separately:

```java
masquerade.setValueChangeAllowed(Keys.IS_ABLAZE, false);
```

Now, the entity will only appear in flames when asked for by the plugin, and otherwise retain the current state.

[MasqueradePlugin]: https://github.com/CrushedPixel/MasqueradePlugin/
[MasqueradeCommand]: https://github.com/CrushedPixel/MasqueradeCommand/
