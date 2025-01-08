package com.presnakov.hotelbooking.service;

import com.presnakov.hotelbooking.database.entity.Hotel;
import com.presnakov.hotelbooking.database.repository.HotelRepository;
import com.presnakov.hotelbooking.dto.HotelCreateEditDto;
import com.presnakov.hotelbooking.dto.HotelReadDto;
import com.presnakov.hotelbooking.mapper.HotelCreateEditMapper;
import com.presnakov.hotelbooking.mapper.HotelReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelReadMapper hotelReadMapper;
    private final HotelCreateEditMapper hotelCreateEditMapper;
    private final ImageService imageService;

    public List<HotelReadDto> findAll() {
        return hotelRepository.findAll().stream()
                .map(hotelReadMapper::map)
                .toList();
    }

    public Page<HotelReadDto> findAll(Pageable pageable) {
        return hotelRepository.findAll(pageable)
                .map(hotelReadMapper::map);
    }

    public Optional<HotelReadDto> findById(Integer id) {
        return hotelRepository.findById(id)
                .map(hotelReadMapper::map);

    }

    public Optional<HotelReadDto> findByName(String name) {
        return hotelRepository.findByName(name)
                .map(hotelReadMapper::map);
    }

    public Optional<byte[]> findAvatar(Integer id) {
        return hotelRepository.findById(id)
                .map(Hotel::getPhoto)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public HotelReadDto create(HotelCreateEditDto hotelDto) {
        return Optional.of(hotelDto)
                .map(dto -> {
                    uploadImage(dto.getPhoto());
                    return hotelCreateEditMapper.map(dto);
                })
                .map(hotelRepository::save)
                .map(hotelReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<HotelReadDto> update(Integer id, HotelCreateEditDto hotelDto) {
        return hotelRepository.findById(id)
                .map(entity -> {
                    uploadImage(hotelDto.getPhoto());
                    return hotelCreateEditMapper.map(hotelDto, entity);
                })
                .map(hotelRepository::saveAndFlush)
                .map(hotelReadMapper::map);
    }


    @Transactional
    public boolean delete(Integer id) {
        return hotelRepository.findById(id)
                .map(entity -> {
                    hotelRepository.delete(entity);
                    hotelRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            imageService.upload(photo.getOriginalFilename(), photo.getInputStream());
        }
    }
}
