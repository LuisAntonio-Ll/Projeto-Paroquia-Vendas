package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.GrupoRequest;
import br.com.paroquiagestao.exception.RecursoNaoEncontradoException;
import br.com.paroquiagestao.model.Grupo;
import br.com.paroquiagestao.repository.GrupoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GrupoServiceImplTest {

    @Mock
    private GrupoRepository grupoRepository;

    @InjectMocks
    private GrupoServiceImpl service;

    @Test
    void cadastrar_deveSalvarGrupoComDadosInformados() {
        when(grupoRepository.save(any(Grupo.class))).thenAnswer(i -> i.getArgument(0));

        Grupo salvo = service.cadastrar(new GrupoRequest("Catequese", "Maria Silva"));

        assertEquals("Catequese", salvo.getNome());
        assertEquals("Maria Silva", salvo.getResponsavel());
    }

    @Test
    void buscarPorId_inexistente_deveLancarExcecao() {
        when(grupoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> service.buscarPorId(99L));
    }
}