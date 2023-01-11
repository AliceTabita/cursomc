package com.alice.cursomc.services.validation;

import com.alice.cursomc.domain.Cliente;
import com.alice.cursomc.domain.enums.TipoCliente;
import com.alice.cursomc.dto.ClienteCadDTO;
import com.alice.cursomc.repositories.ClienteRepository;
import com.alice.cursomc.resources.exceptions.FieldMessage;
import com.alice.cursomc.services.validation.utils.BR;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteCadDTO> {
    @Autowired
    private ClienteRepository clienteRepository;
    @Override
    public void initialize(ClienteInsert ann) {
    }
    @Override
    public boolean isValid(ClienteCadDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        if(objDto.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","Cpf inválido"));
        }
        if(objDto.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj","Cnpj inválido"));
        }
        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
        if(aux != null){
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
