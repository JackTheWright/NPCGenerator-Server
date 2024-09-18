package jack.dnd.npcgeneratorjavaserver;

class NpcNotFoundException extends RuntimeException {
    NpcNotFoundException(Long id) {
        super("Could not find npc: " + id);
    }
}