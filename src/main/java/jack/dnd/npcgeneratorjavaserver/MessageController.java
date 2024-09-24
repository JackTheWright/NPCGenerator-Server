package jack.dnd.npcgeneratorjavaserver;

import java.util.List;
import org.json.JSONObject;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.api.OpenAiApi.ChatCompletionRequest.ResponseFormat;
import org.springframework.ai.openai.api.OpenAiApi.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.openai.*;

@EnableSpringDataWebSupport(
        pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)

@RestController
public class MessageController {
    private final NPCDetailsRepository repository;
    String jsonSchema = """
            {
               "$schema": "http://json-schema.org/draft-07/schema#",
               "type": "object",
               "properties": {
                 "name": {
                   "type": "string",
                   "description": "The name of the character"
                 },
                 "age": {
                   "type": "integer",
                   "description": "The age of the character"
                 },
                 "race": {
                   "type": "string",
                   "description": "The character's race, such as Elf, Dwarf, etc."
                 },
                 "gender": {
                   "type": "string",
                   "description": "The character's gender"
                 },
                 "profession": {
                   "type": "string",
                   "description": "The character's profession, such as Wizard, Librarian, blacksmith, etc."
                 },
                 "hairstyle": {
                   "type": "string",
                   "description": "The character's hairstyle"
                 },
                 "standoutFeature": {
                   "type": "string",
                   "description": "A unique physical or behavioral feature that stands out about the character"
                 },
                 "personalityTrait": {
                   "type": "string",
                   "description": "A key personality trait that defines the character"
                 },
                 "accent": {
                   "type": "string",
                   "description": "The type of accent the character has"
                 }
               },
               "required": ["name", "age", "race", "gender", "profession", "hairstyle", "standoutFeature", "personalityTrait", "accent"],
               "additionalProperties": false
            }
            """;

    MessageController(NPCDetailsRepository repository, OpenAiChatModel chatModel) {
        this.repository = repository;
        this.chatModel = chatModel;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/npcDetails")
    Page<NPCDetails> all(@RequestParam(value = "offset", required = false) Integer offset,
                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                         @RequestParam(value = "sortBy", required = false) String sortBy) {
        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = 10;
        if(null == sortBy) sortBy = "id";
        return repository.findAll(PageRequest.of(offset, pageSize, Sort.by(sortBy)));
    }
    // end::get-aggregate-root[]

    @PostMapping("/npcDetails")
    NPCDetails newNpc(@RequestBody NPCDetails newNPCDetails) {
        return repository.save(newNPCDetails);
    }

    // Single item

    @GetMapping("/npcDetails/{id}")
    NPCDetails one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new NpcNotFoundException(id));
    }

    @PutMapping("/npcDetails/{id}")
    NPCDetails updateNPC(@RequestBody NPCDetails newNPC, @PathVariable Long id) {

        return repository.findById(id)
                .map(npc -> {
                    npc.setName(newNPC.getName());
                    npc.setAge(newNPC.getAge());
                    npc.setRace(newNPC.getRace());
                    npc.setGender(newNPC.getGender());
                    npc.setProfession(newNPC.getProfession());
                    npc.setHairstyle(newNPC.getHairstyle());
                    npc.setStandoutFeature(newNPC.getStandoutFeature());
                    npc.setPersonalityTrait(newNPC.getPersonalityTrait());
                    npc.setAccent(newNPC.getAccent());
                    return repository.save(newNPC);
                })
                .orElseGet(() -> repository.save(newNPC));
    }

    @DeleteMapping("/npcDetails/{id}")
    void deleteNPCDetails(@PathVariable Long id) {
        repository.deleteById(id);
    }

    private final OpenAiChatModel chatModel;

    @GetMapping("/ai/generate")
    public NPCDetails generate() {
        Prompt prompt = new Prompt("Generate me a random, unique, Dungeons and Dragons NPC.",
                OpenAiChatOptions.builder()
                        .withModel(ChatModel.GPT_4_O_MINI)
                        .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                        .withStreamUsage(false)
                        .withMaxTokens(250)
                        .withTemperature(1.55F)
                        .build());
        ChatResponse response = this.chatModel.call(prompt);
        var JSONResponse = response.getResult().getOutput().getContent();
        JSONObject jsonObj = new JSONObject(JSONResponse);
        NPCDetails npcDetails = new NPCDetails();
        npcDetails.setName(jsonObj.getString("name"));
        npcDetails.setAge(jsonObj.getInt("age"));
        npcDetails.setRace(jsonObj.getString("race"));
        npcDetails.setGender(jsonObj.getString("gender"));
        npcDetails.setProfession(jsonObj.getString("profession"));
        npcDetails.setHairstyle(jsonObj.getString("hairstyle"));
        npcDetails.setStandoutFeature(jsonObj.getString("standoutFeature"));
        npcDetails.setPersonalityTrait(jsonObj.getString("personalityTrait"));
        npcDetails.setAccent(jsonObj.getString("accent"));
        repository.save(npcDetails);
        return npcDetails;
    }
}