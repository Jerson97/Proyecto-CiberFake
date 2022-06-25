package com.proyecto.pe.edu.ciberfake.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.proyecto.pe.edu.ciberfake.dto.ComunidadDto;
import com.proyecto.pe.edu.ciberfake.exceptions.CiberFakeException;
import com.proyecto.pe.edu.ciberfake.mapper.ComunidadMapper;
import com.proyecto.pe.edu.ciberfake.model.Comunidad;
import com.proyecto.pe.edu.ciberfake.repository.ComunidadRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Service
@AllArgsConstructor
@Slf4j
public class ComunidadService {

    private final ComunidadRepository comunidadRepository;
    private final ComunidadMapper comunidadMapper;
    private final AuthService authService;

    @Transactional
    public ComunidadDto guardar(ComunidadDto comunidadDto){
        comunidadDto.setComEstado(true);
        Comunidad comunidad = comunidadRepository.save(comunidadMapper.mapComunidadToDto(comunidadDto, authService.obtenerUsuarioActual()));
        comunidadDto.setComId(comunidad.getComId());
        comunidadDto.setUsuNombre(comunidad.getUsuario().getUsuNombre());
        comunidadDto.setComNumeroPosts(0);
        return comunidadDto;
    }

    @Transactional(readOnly = true)
    public List<ComunidadDto> obtenerComunidades() {
        return comunidadRepository.findAll()
                .stream()
                .map(comunidadMapper::mapComunidadToDto)
                .collect(toList());
    }

    public ComunidadDto obtenerComunidad(Long id){
        Comunidad comunidad = comunidadRepository.findById(id)
                .orElseThrow(() -> new CiberFakeException("No se encontro Comunidad con ese Id"));
        return comunidadMapper.mapComunidadToDto(comunidad);
    }

    public ComunidadDto actualizar(ComunidadDto comunidadDto) {
        comunidadRepository.save(comunidadMapper.mapDtoToComunidad(comunidadDto, authService.obtenerUsuarioActual()));
        return comunidadDto;
    }
}
