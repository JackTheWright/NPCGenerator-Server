package jack.dnd.npcgeneratorjavaserver;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class NPCDetails {
    private @Id
    @GeneratedValue Long id;
    private String name;
    private int age;
    private String race;
    private String gender;
    private String profession;
    private String hairstyle;
    private String standoutFeature;
    private String personalityTrait;
    private String accent;

    public NPCDetails(
            String name,
            int age,
            String race,
            String gender,
            String profession,
            String hairstyle,
            String standoutFeature,
            String personalityTrait,
            String accent
    ) {
        this.name = name;
        this.age = age;
        this.race = race;
        this.gender = gender;
        this.profession = profession;
        this.hairstyle = hairstyle;
        this.standoutFeature = standoutFeature;
        this.personalityTrait = personalityTrait;
        this.accent = accent;
    }

    public NPCDetails() {
    }

    public long getID() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }
    public String getRace() {
        return this.race;
    }
    public String getGender() {
        return this.gender;
    }
    public String getProfession() {
        return this.profession;
    }
    public String getHairstyle() {
        return this.hairstyle;
    }
    public String getStandoutFeature() {
        return this.standoutFeature;
    }
    public String getPersonalityTrait() {
        return this.personalityTrait;
    }
    public String getAccent() {
        return this.accent;
    }

    public void setID(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setRace(String race) {
        this.race = race;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public void setHairstyle(String hairstyle) {
        this.hairstyle = hairstyle;
    }
    public void setStandoutFeature(String standoutFeature) {
        this.standoutFeature = standoutFeature;
    }
    public void setPersonalityTrait(String personalityTrait) {
        this.personalityTrait = personalityTrait;
    }
    public void setAccent(String accent) {
        this.accent = accent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NPCDetails))
            return false;
        NPCDetails npc = (NPCDetails) o;
        return Objects.equals(this.id, npc.id) &&
                Objects.equals(this.name, npc.name) &&
                Objects.equals(this.age, npc.age) &&
                Objects.equals(this.personalityTrait, npc.personalityTrait);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.age, this.personalityTrait);
    }

    @Override
    public String toString() {
        String message = "NPC{\n" + "name: " + this.name + ",\nage: " + this.age + ",\nrace: " + this.race;
        String messagePartTwo = ",\ngender: " + this.gender + ",\nprofession: " + this.profession;
        String messagePartThree = ",\nhairstyle: " + this.hairstyle + ",\nstandoutFeature: " + this.standoutFeature;
        String messagePartFour = ",\npersonalityTrait: " + this.personalityTrait + ",\naccent: " + this.accent;
        return message + messagePartTwo + messagePartThree + messagePartFour;
    }
}
