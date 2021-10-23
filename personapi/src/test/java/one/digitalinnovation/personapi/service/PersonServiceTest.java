package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.MessageResponseDTO;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.repository.PersonRepository;
import one.digitalinnovation.personapi.util.PersonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonService personService;

    @Test
    void testGivenPersonDTOThenReturnSuccessMessage(){
        PersonDTO personDTO= PersonUtil.createFakeDTO();
        Person expectedSavedPerson = PersonUtil.createFakeEntity();

        when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);

        MessageResponseDTO expectedMessage =
                createExpectedMessageResponse("Created person with Id = ", expectedSavedPerson.getId());
        MessageResponseDTO successMessage = personService.createPerson(personDTO);

        Assertions.assertEquals(expectedMessage, successMessage);
    }

    private MessageResponseDTO createExpectedMessageResponse(String message, Long id){
        return MessageResponseDTO.builder()
                .message(message + id).build();
    }
}
