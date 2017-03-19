package eu.crushedpixel.sponge.masquerade.api.manipulators;

import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.data.FlagEntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.masquerades.Masquerade;
import net.minecraft.entity.EntityLiving;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;

import java.util.List;

public class EntityLivingDataManipulator<E extends EntityLiving> extends EntityMobDataManipulator<E> {

    private static final int FLAG_NO_AI = 0;
    private static final int FLAG_LEFT_HANDED = 1;

    protected final DataEntry<Byte> aiFlags = new DataEntry<>(EntityLiving.AI_FLAGS, (byte) 0);

    public final EntityMetadata<Byte, Boolean> noAi = new FlagEntityMetadata(this, aiFlags, FLAG_NO_AI, "noAI");
    public final EntityMetadata<Byte, Boolean> leftHanded = new FlagEntityMetadata(this, aiFlags, FLAG_LEFT_HANDED, "leftHanded");

    public EntityLivingDataManipulator(Masquerade<E, ? extends EntityDataManipulator<E>> masquerade) {
        super(masquerade);
    }

    @Override
    public List<EntityMetadata> getAllEntries() {
        List<EntityMetadata> entries = super.getAllEntries();

        entries.add(noAi);
        entries.add(leftHanded);

        return entries;
    }
}
