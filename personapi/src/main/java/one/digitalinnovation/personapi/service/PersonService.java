package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.MessageResponseDTO;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public List<PersonDTO> listAll() {
        List<Person> allPeople = this.personRepository.findAll();
        return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = this.verifyIfExists(id);
        return personMapper.toDTO(person);
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = this.personMapper.toModel(personDTO);
        Person savedPerson = this.personRepository.save(personToSave);
        return createMessageResponse(savedPerson.getId(), "Created person with Id = ");
    }

    public MessageResponseDTO updatePersonById(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        this.verifyIfExists(id);

        Person personToUpdate = this.personMapper.toModel(personDTO);
        Person updatedPerson = this.personRepository.save(personToUpdate);
        return createMessageResponse(updatedPerson.getId(), "Updated person with Id = ");
    }

    public void deleteById(Long id) throws PersonNotFoundException {
        Person personToDelete = this.verifyIfExists(id);
        this.personRepository.deleteById(id);
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return this.personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO
                .builder()
                .message(message + id)
                .build();
    }
}
