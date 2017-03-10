package eu.crushedpixel.sponge.masquerade.manipulators;

import net.minecraft.network.datasync.EntityDataManager.DataEntry;

import java.util.List;

public interface DataManipulator {

    List<DataEntry<?>> getAllEntries();

}
