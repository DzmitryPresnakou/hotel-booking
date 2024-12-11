package com.presnakov.hotelbooking.service;

import com.presnakov.hotelbooking.database.entity.Room;
import com.presnakov.hotelbooking.database.repository.RoomRepository;
import com.presnakov.hotelbooking.dto.RoomCreateEditDto;
import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.dto.RoomReadDto;
import com.presnakov.hotelbooking.mapper.RoomCreateEditMapper;
import com.presnakov.hotelbooking.mapper.RoomReadMapper;
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
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomReadMapper roomReadMapper;
    private final RoomCreateEditMapper roomCreateEditMapper;
    private final ImageService imageService;

    public Page<RoomReadDto> findAll(RoomFilter filter, Pageable pageable) {
        return roomRepository.findAll(filter, pageable)
                .map(roomReadMapper::map);
    }

    public List<RoomReadDto> findAll() {
        return roomRepository.findAll().stream()
                .map(roomReadMapper::map)
                .toList();
    }

    public Optional<RoomReadDto> findById(Integer id) {
        return roomRepository.findById(id)
                .map(roomReadMapper::map);
    }

    public Optional<byte[]> findAvatar(Integer id) {
        return roomRepository.findById(id)
                .map(Room::getPhoto)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public RoomReadDto create(RoomCreateEditDto roomCreateEditDto) {
        return Optional.of(roomCreateEditDto)
                .map(dto -> {
                    uploadImage(dto.getPhoto());
                    return roomCreateEditMapper.map(dto);
                })
                .map(roomRepository::save)
                .map(roomReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<RoomReadDto> update(Integer id, RoomCreateEditDto roomCreateEditDto) {
        return roomRepository.findById(id)
                .map(entity -> {
                    uploadImage(roomCreateEditDto.getPhoto());
                    return roomCreateEditMapper.map(roomCreateEditDto, entity);
                })
                .map(roomRepository::saveAndFlush)
                .map(roomReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return roomRepository.findById(id)
                .map(entity -> {
                    roomRepository.delete(entity);
                    roomRepository.flush();
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
