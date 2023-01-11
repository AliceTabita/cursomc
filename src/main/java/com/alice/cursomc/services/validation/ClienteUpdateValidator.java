package com.alice.cursomc.services.validation;

import com.alice.cursomc.domain.Cliente;
import com.alice.cursomc.domain.enums.TipoCliente;
import com.alice.cursomc.dto.ClienteCadDTO;
import com.alice.cursomc.dto.ClienteDTO;
import com.alice.cursomc.repositories.ClienteRepository;
import com.alice.cursomc.resources.exceptions.FieldMessage;
import com.alice.cursomc.services.validation.utils.BR;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private HttpServletRequest request;
    @Override
    public void initialize(ClienteUpdate ann) {
    }
    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
        @SuppressWarnings("unchecked")
        Map<String,String> map=(Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId =Integer.parseInt( map.get("id"));

        List<FieldMessage> list = new ArrayList<>();
        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
        if(aux != null && !aux.getId().equals(uriId)){
            list.add(new FieldMessage("email", "este email ja existe"));

        }

        // inclua os testes aqui, inserindo erros na lista

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
