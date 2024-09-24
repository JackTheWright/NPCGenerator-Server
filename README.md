# NPCGenerator-Server
This is the backend for the Dungeons and Dragons NPC Generator app. It's made using Spring and implements a RESTful web service.

The main functionality of this Server is to call the Open AI API and use chatGPT to generate a custom NPC for a Fantasy Table Top Role Playing Game according to a custom JSON Schema.

The server implements GET requests that do the following
- all(...) -> returns a paginated response that includes the NPCDetails
- one(...) -> takes ID as a parameter and returns an NPC with the matching ID
- generate() -> Uses Open AI API to create an NPC and then builds an NPCDetails object which is returned

POST and PUT requests:
- newNpc(...) -> Create a new NPC, takes NPCDetails as an input parameter
- updateNPC(...) -> Edit an NPC, takes NPCDetails and ID as an input parameter

and DELETE requests:
- deleteNPCDetails(...) -> Deletes the NPC
