package com.example.PG.s.Dragons;

import com.example.PG.s.Dragons.entities.Spell;
import com.example.PG.s.Dragons.enums.Class;
import com.example.PG.s.Dragons.enums.School;
import com.example.PG.s.Dragons.services.SpellService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Runner implements CommandLineRunner {
    @Autowired
    private SpellService spellService;
    private final Logger logger= LoggerFactory.getLogger("Spells_DB");
    @Override
    @Order(1)
    public void run(String... args) throws Exception {
        if(spellService.optionalById(1).isPresent()) return;
        String spells = "CSV/dnd-spells.csv";
        String[] line;
        int index=0;
        try {
            CSVReader spellsReader = new CSVReader(new FileReader(spells));
            while((line=spellsReader.readNext())!=null){
                Spell spell=new Spell();
                spell.setName(line[0]);
                spell.setClassList(new HashSet<>(toClassSet(line[1])));
                spell.setLevel(Integer.parseInt(line[2]));
                spell.setSchool(toSchoolSet(line[3]));
                spell.setCastTime(line[4]);
                spell.setRange(line[5]);
                spell.setDuration(line[6]);
                boolean verbal=toBoolean(line[7]);
                boolean somatic=toBoolean(line[8]);
                boolean material=toBoolean(line[9]);
                spell.setComponents(List.of(verbal,somatic,material));
                spell.setMaterialCost(line[10]);
                spell.setDescription(line[11]);
                spellService.save(spell);
            }
            logger.info("All spells are uploaded");
        } catch (IOException | CsvValidationException e) {
            logger.error(e.getMessage());
        }
    }
    private Set<Class> toClassSet(String string){
        String[] classSplit=string.split(", ");
        Set<Class> classSet=new HashSet<>();
        Arrays.stream(classSplit).toList().forEach(el->{
            switch(el){
                case "Artificer":
                    classSet.add(Class.Artificer);
                    break;
                case "Bard":
                    classSet.add(Class.Bard);
                    break;
                case "Cleric":
                    classSet.add(Class.Cleric);
                    break;
                case "Druid":
                    classSet.add(Class.Druid);
                    break;
                case "Paladin":
                    classSet.add(Class.Paladin);
                    break;
                case "Ranger":
                    classSet.add(Class.Ranger);
                    break;
                case "Sorcerer":
                    classSet.add(Class.Sorcerer);
                    break;
                case "Warlock":
                    classSet.add(Class.Warlock);
                    break;
                case "Wizard":
                    classSet.add(Class.Wizard);
                    break;
            }
        });
        return classSet;
    }
    private School toSchoolSet(String string){
        return switch (string) {
            case "Abjuration" -> School.Abjuration;
            case "Conjuration" -> School.Conjuration;
            case "Enchantment" -> School.Enchantment;
            case "Evocation" -> School.Evocation;
            case "Illusion" -> School.Illusion;
            case "Transmutation" -> School.Transmutation;
            case "Necromancy"->School.Necromancy;
            case "Divination"->School.Divination;
            default -> null;
        };
    }
    private boolean toBoolean(String string){
        return string.equals("1");
    }
}
