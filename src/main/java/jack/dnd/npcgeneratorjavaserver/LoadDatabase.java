package jack.dnd.npcgeneratorjavaserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(NPCDetailsRepository repository) {
        return args -> log.info("Preloading " + repository.save(new NPCDetails("Gimly", 31, "Dwarf", "Male", "Adventurer", "long orange hair", "big axe", "big beard", "dwarven")));
    }
}
